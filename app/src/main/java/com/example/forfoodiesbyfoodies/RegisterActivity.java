package com.example.forfoodiesbyfoodies;



import android.content.ContentResolver;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forfoodiesbyfoodies.Helpers.User;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
//    Impostors
    private EditText reg_input_email,reg_input_fn,reg_input_sn,reg_input_pw,reg_confirm_pw;
    private TextView reg_already;
    private Button btn_reg;
    private ProgressBar progressBar;


    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    private FirebaseUser fuser;
    private FirebaseAuth mAuth;
    Uri url;
    private static final String TAG = "RegisterActivity";
    private User user;

    private ImageView profpic,useronoff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Bind / Init variables
        reg_already = findViewById(R.id.reg_already);
        reg_input_email = findViewById(R.id.reg_input_email);
        reg_input_fn = findViewById(R.id.reg_input_fn);
        reg_input_sn = findViewById(R.id.reg_input_sn);
        reg_input_pw = findViewById(R.id.reg_input_pw);
        reg_confirm_pw = findViewById(R.id.reg_confirm_pw);
        btn_reg = findViewById(R.id.btn_reg);
        progressBar = findViewById(R.id.reg_progressBar);
        profpic = findViewById(R.id.reg_userimg);
        useronoff = findViewById(R.id.reg_onoff);



        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        storageReference = FirebaseStorage.getInstance().getReference("PROFILE_PIC_USERS");
        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();





        profpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addimage = new Intent(Intent.ACTION_GET_CONTENT);
                addimage.setType("image/*");
                startActivityForResult(addimage, 100);
            }
        });



//        Picasso.get().load(url).fit().into(userImageView);
//      OnClick Event for createUser check if the user enter email and password in EDIT TEXT
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = reg_input_email.getText().toString();
                String password = reg_input_pw.getText().toString();
                String confirmpw = reg_confirm_pw.getText().toString();


                Picasso.get().load(url).fit().into(profpic);





                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) && url == null) {
                    Toast.makeText(getApplicationContext(), "Please complete the register account", Toast.LENGTH_LONG).show();

                    return;
                }

                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmpw)&& url != null) {

                    if (password.equals(confirmpw)) {
                        //are equal

                        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if(authResult != null){
                                    progressBar.setVisibility(View.VISIBLE);
                                    String name = mAuth.getCurrentUser().getUid();
                                    StorageReference reference = storageReference.child(name+"."+getExt(url));
                                    reference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    try{
                                                        Thread.sleep(1000);
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(RegisterActivity.this,"User registered successfully.",Toast.LENGTH_SHORT).show();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    String downloadUrl = uri.toString();

                                                    DatabaseReference currentUserDb = mDatabase.child(name);
                                                    currentUserDb.child("email").setValue(reg_input_email.getText().toString());
                                                    currentUserDb.child("firstName").setValue(reg_input_fn.getText().toString());
                                                    currentUserDb.child("lastName").setValue(reg_input_sn.getText().toString());
                                                    currentUserDb.child("imageUrl").setValue(downloadUrl);
                                                    currentUserDb.child("userType").setValue("user");

                                                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                                    startActivity(i);


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    reference.delete();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                try{
                                    Thread.sleep(1000);
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(RegisterActivity.this, " Your email is already registered.  ",Toast.LENGTH_SHORT).show();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                            }
                        });

                    } else {
                        //are different
                        Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_LONG).show();

                        return;
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Please upload your profile picture", Toast.LENGTH_SHORT).show();

                    return;
                }

            }
        });

//      Change class when he already have an account
        reg_already.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data.getData() !=null){
            useronoff.setVisibility(View.GONE);
            {
                url = data.getData();
                Picasso.get().load(url).fit().into(profpic);

            }

        }
    }



    private String getExt(Uri uri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }


}
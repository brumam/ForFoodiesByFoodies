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





                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter email and password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmpw)) {
                    if (password.equals(confirmpw)) {
                        //are equal

                        String name = mDatabase.push().getKey();
                        StorageReference reference = storageReference.child(name+"."+getExt(url));
                        reference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadUrl = uri.toString();
                                        DatabaseReference newPost = mDatabase.push();

                                        Map<String, String> dataToSave = new HashMap<>();
                                        dataToSave.put("email", reg_input_email.getText().toString());
                                        dataToSave.put("firstName", reg_input_fn.getText().toString());
                                        dataToSave.put("lastName", reg_input_sn.getText().toString());
                                        dataToSave.put("imageUrl", downloadUrl);
                                        dataToSave.put("userType","user");
                                        newPost.setValue(dataToSave);


                                        registerUser(email, password);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        reference.delete();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });



                    } else {
                        //are different
                        Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_LONG).show();
                        return;
                    }
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

    //    Register function to create user in Firebase - Error messages and Toast for successfully or not
    private void registerUser(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            try{
                                Thread.sleep(1000);

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this,"User registered successfully.",Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else{
                            try {
                                Thread.sleep(3000);

                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(RegisterActivity.this, " Your email is already registered.  ",Toast.LENGTH_SHORT).show();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void  updateUI(FirebaseUser currentUser){
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }


}
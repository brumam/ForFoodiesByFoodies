package com.example.forfoodiesbyfoodies.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.forfoodiesbyfoodies.App.AppClass;
import com.example.forfoodiesbyfoodies.LoginActivity;
import com.example.forfoodiesbyfoodies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;



public class EditProfile extends AppCompatActivity {
//     Declare Impostors
    public static final String TAG = "TAG";
    EditText edit_password, confirm_pw;
    ImageView edit_user_imageview, edit_on_off;
    Button edit_save;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    StorageReference storageReference;
    DatabaseReference mDatabase;
    Uri url;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

//        Bind Impostors
        edit_password = findViewById(R.id.edit_prof_pw);
        confirm_pw = findViewById(R.id.edit_prof_confirm_pw);
        edit_user_imageview = findViewById(R.id.edit_user_imageview);
        edit_save = findViewById(R.id.edit_save);
        progressBar = findViewById(R.id.edit_progress);
        edit_on_off = findViewById(R.id.edit_onoff);

        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        storageReference = FirebaseStorage.getInstance().getReference("PROFILE_PIC_USERS");


//        Remove visibility for User image Logo if the getImageURL != null
        if(AppClass.Session.user.getImageUrl() != null){
            edit_on_off.setVisibility(View.INVISIBLE);
            Picasso.get().load(AppClass.Session.user.getImageUrl()).fit().into(edit_user_imageview);
        }else {
            edit_on_off.setVisibility(View.VISIBLE);
        }

//     Get content
        edit_user_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });


//        Change Password method and update profile picture also check TEXT UTILS
        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edit_password.getText().toString();
                String confirmPassword = confirm_pw.getText().toString();
                FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();

                if(!TextUtils.isEmpty(newPassword)&&!TextUtils.isEmpty(confirmPassword)){
                    if(newPassword.equals(confirmPassword)){
                        progressBar.setVisibility(View.VISIBLE);

                        fuser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    String name = mAuth.getCurrentUser().getUid();
                                    StorageReference reference = storageReference.child(name+"."+getExt(url));
                                    reference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    try{
                                                        String downloadUrl = uri.toString();

                                                        DatabaseReference currentUserDB = mDatabase.child(name);
                                                        currentUserDB.child("imageUrl").setValue(downloadUrl);
                                                        Thread.sleep(1000);
                                                        mAuth.signOut();
                                                        Log.d(TAG,"User password updated");
                                                        Toast.makeText(EditProfile.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(EditProfile.this, LoginActivity.class);
                                                        startActivity(i);

                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
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
                        });
                    }else {
                        Toast.makeText(EditProfile.this, "Your password does not match.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditProfile.this, "Please enter your new password.", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

//    Check request code - and add data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData() !=null){
            edit_on_off.setVisibility(View.GONE);
            {
                url = data.getData();
                Picasso.get().load(url).fit().into(edit_user_imageview);

            }

        }

    }

//    Content extension resolver
    private String getExt(Uri uri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }
}
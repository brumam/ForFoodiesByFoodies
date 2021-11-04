package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
     TextView  nameTxtView,lastTxtView;
     TextView emailTxtView, passwordTxtView;
     ImageView userImageView;
    private final String TAG = this.getClass().getName().toUpperCase();
     Map<String, String> userMap;
     String email;
    private static final String USERS = "user";

     FirebaseAuth mAuth;
     FirebaseFirestore mStore;
     FirebaseDatabase database;
     String userId;
     FirebaseUser fuser;
     Uri url;
     String myUri = "";
     StorageReference storageReference;
     DatabaseReference mDatabase;
     User user;
     ImageView onfoff;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //receive data from login screen
        Intent intent = getIntent();
        email = intent.getStringExtra("email");


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());

//        Bind impostors

        nameTxtView = findViewById(R.id.name_textview);
        lastTxtView = findViewById(R.id.last_name_textview);
        emailTxtView = findViewById(R.id.email_imageview);
        passwordTxtView = findViewById(R.id.password_textview);
        userImageView = findViewById(R.id.user_imageview);
        onfoff = findViewById(R.id.onoff);


        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        storageReference = FirebaseStorage.getInstance().getReference();


        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                onfoff.setVisibility(View.GONE);

                String downloadURL = uri.toString();


                DatabaseReference updateref = mDatabase.push();

                Map<String, String> dataToSave = new HashMap<>();
                dataToSave.put("email", email);
                dataToSave.put("imageUrl", downloadURL);



                updateref.setValue(dataToSave);

//                User user = new User(uri.toString());

//                mDatabase.child(fuser.getUid()).child("imageUrl").setValue(uri.toString());
//                user = new User(uri.toString());
//
//                FirebaseUser imguser = mAuth.getCurrentUser();
//                updateUI(imguser);


//                Picasso.get().load(uri).into(userImageView);
            }
        });





        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname,lname, mail, password;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("firstName").getValue(String.class);
                        lname = keyId.child("lastName").getValue(String.class);
                        password = keyId.child("password").getValue(String.class);
                        break;
                    }
                }
                nameTxtView.setText(fname);
                lastTxtView.setText(lname);
                emailTxtView.setText(email);
                passwordTxtView.setText(password);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        userImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Add image
                Intent addImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                addImageIntent.setType("image/*");
                startActivityForResult(addImageIntent,1000);



//


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK && data.getData() !=null){
            onfoff.setVisibility(View.GONE);
            {
                url = data.getData();
//                Picasso.get().load(url).into(userImageView);


                userImageView.setImageURI(url);
                uploadImageToFirebaseReal(url);

//                uploadImageToFirebaseReal(url);

            }
        }
    }
    private void uploadImageToFirebaseReal(Uri uri){
        final StorageReference reference = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+".jpg");
         reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String downloadURL = uri.toString();


                        DatabaseReference updateref = mDatabase.push();

                        Map<String, String> dataToSave = new HashMap<>();
                        dataToSave.put("email", email);
                        dataToSave.put("imageUrl", downloadURL);



                        updateref.setValue(dataToSave);



                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private String getExt(Uri uri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

//    private void uploadImageToFirebase(Uri url) {
////        Upload Image to Firebase Storage
//
//        final StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+".jpg");
//        fileRef.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                onfoff.setVisibility(View.GONE);
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//
//                        user = new User(uri.toString());
//                        mDatabase.child(fuser.getUid()).child("imageUrl").setValue(uri.toString());
////                        String keyId = mDatabase.push().getKey();
////                        mDatabase.child(keyId).setValue(user);
//
//
//
//                        Picasso.get().load(url).into(userImageView);
//
//
//                    }
//                });
//            }
//        })
//    }
    public void  updateUI(FirebaseUser currentUser){






    }
}
package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TextView  nameTxtView,lastTxtView;
    private TextView emailTxtView, passwordTxtView;
    private ImageView userImageView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Map<String, String> userMap;
    private String email;
    private static final String USERS = "user";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private String userId;
    private FirebaseUser user;
    private Uri imageUri;
//    private String myUri = "";
    private StorageReference storageReference;
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

//        Init variables

        nameTxtView = findViewById(R.id.name_textview);
        lastTxtView = findViewById(R.id.last_name_textview);
        emailTxtView = findViewById(R.id.email_imageview);
        passwordTxtView = findViewById(R.id.password_textview);
        userImageView = findViewById(R.id.user_imageview);
        onfoff = findViewById(R.id.onoff);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                onfoff.setVisibility(View.GONE);
                Picasso.get().load(uri).into(userImageView);
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
                Intent addImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(addImageIntent,1000);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            onfoff.setVisibility(View.GONE);
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();


                // userImageView.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);

            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
//        Upload Image to Firebase Storage
        final StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onfoff.setVisibility(View.GONE);
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(userImageView);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                onfoff.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
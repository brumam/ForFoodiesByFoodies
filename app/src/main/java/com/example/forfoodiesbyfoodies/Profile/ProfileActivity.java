package com.example.forfoodiesbyfoodies.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forfoodiesbyfoodies.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.StorageReference;

import com.squareup.picasso.Picasso;



public class ProfileActivity extends AppCompatActivity {
//    Declare Impostors
     TextView  nameTxtView,lastTxtView;
     TextView emailTxtView, passwordTxtView;
     ImageView userImageView;
    private final String TAG = this.getClass().getName().toUpperCase();

     String email;
    private static final String USERS = "user";

     FirebaseAuth mAuth;
     FirebaseFirestore mStore;
     FirebaseUser fuser;
     Uri url;
     StorageReference storageReference;
     DatabaseReference mDatabase;
     ImageView onfoff;
     Button ed_pass;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //receive data from login screen
        Intent intent = getIntent();
        email = intent.getStringExtra("email");




//        Bind impostors

        nameTxtView = findViewById(R.id.name_textview);
        lastTxtView = findViewById(R.id.last_name_textview);
        emailTxtView = findViewById(R.id.email_imageview);
        passwordTxtView = findViewById(R.id.password_textview);
        userImageView = findViewById(R.id.user_imageview);
        onfoff = findViewById(R.id.onoff);
        ed_pass = findViewById(R.id.ed_password);


        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("user");


        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            String fname, lname, password, imageUrl;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("firstName").getValue(String.class);
                        lname = keyId.child("lastName").getValue(String.class);
                        password = keyId.child("password").getValue(String.class);
                        imageUrl = keyId.child("imageUrl").getValue(String.class);


                        break;
                    }
                }
                nameTxtView.setText(fname);
                lastTxtView.setText(lname);
                emailTxtView.setText(email);
                passwordTxtView.setText(password);
                Picasso.get().load(imageUrl).fit().into(userImageView);
                onfoff.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//        Change class to EditProfile set on click
        ed_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });
    }

}
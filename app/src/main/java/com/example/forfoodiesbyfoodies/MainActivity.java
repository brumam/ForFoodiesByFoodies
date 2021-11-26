package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.App.AppClass;
import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.Helpers.User;
import com.example.forfoodiesbyfoodies.Profile.ProfileActivity;
import com.example.forfoodiesbyfoodies.Restaurant.RecyclerActivity;
import com.example.forfoodiesbyfoodies.StreetFood.StreetRecyclerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    Declare Impostors
        private Button main_log_out,main_rest, main_street;
        private ImageView profile,imglog;

        private DatabaseReference mDatabase;
        private StorageReference mStorage;
        private FirebaseDatabase firebaseDatabase;

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;
        private FirebaseUser mUser;
        private String email;

        private static final String USERS = "user";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Bind them
        main_log_out = findViewById(R.id.main_log_out);
        main_rest = findViewById(R.id.main_restaurant);
        main_street = findViewById(R.id.main_street_food);
        profile = findViewById(R.id.main_user_imageview2);
        imglog = findViewById(R.id.main_onoff2);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("user");

//        Update UI
        if(mAuth.getCurrentUser() != null){
            updateUI(mAuth.getCurrentUser());
        }

//        Get intent from Log in activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");


//        set profile picture - top
        if(AppClass.Session.user.getImageUrl()!= null){
            Picasso.get().load(AppClass.Session.user.getImageUrl()).fit().into(profile);
            imglog.setVisibility(View.INVISIBLE);
        }else {
            imglog.setVisibility(View.VISIBLE);
        }




//  Log out button SignOut from Firebase and change to LoginActivity
        main_log_out.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                mUser = firebaseAuth.getCurrentUser();

                if(mUser != null){

                }else{

                }
            }
        };


//   Profile Activity Image View  start activity - ProfileActivity.class
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                profileIntent.putExtra("email", currentUser.getEmail());
                Log.v("DATA", currentUser.getUid());
                startActivity(profileIntent);
            }

        });

//        Restaurant Intent
        main_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecyclerActivity.class);
                startActivity(intent);

            }
        });

//         Street Food intent
        main_street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StreetRecyclerActivity.class);
                startActivity(intent);
            }
        });

    }



// Update UI

    public void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener == null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }






}
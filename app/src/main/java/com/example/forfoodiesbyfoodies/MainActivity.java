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
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.Helpers.User;
import com.example.forfoodiesbyfoodies.Profile.ProfileActivity;
import com.example.forfoodiesbyfoodies.Restaurant.RecyclerActivity;
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
        private Button main_log_out,main_rest;
        private ImageView profile,imglog;
        private DatabaseReference mDatabase;
        private StorageReference mStorage;
        private FirebaseDatabase firebaseDatabase;

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;
        private FirebaseUser mUser;
        private String email;

    private static final String USERS = "user";
    List<User> userList =  new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Bind them
        main_log_out = findViewById(R.id.main_log_out);
        main_rest = findViewById(R.id.main_restaurant);
        profile = findViewById(R.id.main_user_imageview2);
        imglog = findViewById(R.id.main_onoff2);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            updateUI(mAuth.getCurrentUser());
        }

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());

        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss: snapshot.getChildren()){
                    User user = dss.getValue(User.class);
                    userList.add(user);
                    Picasso.get().load(user.getImageUrl()).fit().into(profile);
                    imglog.setVisibility(View.INVISIBLE);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




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
                    Toast.makeText(MainActivity.this,"Signed In", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Not Signed In", Toast.LENGTH_SHORT).show();
                }
            }
        };

//   Profile Activity Image View
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
                startActivity(new Intent(MainActivity.this, RecyclerActivity.class));
            }
        });


    }



    //  If statement on start to check if the user is already logged into app or if he is not redirect to LoginActivity-
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
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
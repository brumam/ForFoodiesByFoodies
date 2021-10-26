package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
//    Declare Impostors
        private Button main_log_out;
        private ImageView profile;
        private FirebaseAuth mAuth;
        private String email;
    private static final String USERS = "user";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Bind them
        main_log_out = findViewById(R.id.main_log_out);
        profile = findViewById(R.id.prof_activity);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            updateUI(mAuth.getCurrentUser());
        }

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());



//  Log out button SignOut from Firebase and change to LoginActivity
        main_log_out.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

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



    }


    //  If statement on start to check if the user is already logged into app or if he is not redirect to LoginActivity-
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
    public void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());

    }





}
package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
//    Declare Impostors
        private Button main_log_out;
        private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Bind them
        main_log_out = findViewById(R.id.main_log_out);
        mAuth = FirebaseAuth.getInstance();

//  Log out button SignOut from Firebase and change to LoginActivity
        main_log_out.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

    }
//  If statement on start to check if the user is already logged into app or if he is not redirect to LoginActivity-
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
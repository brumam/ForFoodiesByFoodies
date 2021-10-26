package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // Declare Impostors on all activities
    private EditText log_input_email, log_input_pw;
    private TextView log_sign_up, forgot_pw;
    private Button btn_log;
    private ProgressBar progressBar;


    private String email, password;
    private static final String TAG = "LoginActivity";


    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind impostors

        log_sign_up=(TextView) findViewById(R.id.txtview_email);
        forgot_pw=(TextView) findViewById(R.id.forgot_pw);
        log_input_pw=(EditText) findViewById(R.id.log_input_pw);
        log_input_email=(EditText) findViewById(R.id.log_input_email);
        btn_log =(Button) findViewById(R.id.btn_log);
        progressBar = findViewById(R.id.log_progressBar);

        mAuth =FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() != null){
            updateUI(mAuth.getCurrentUser());
        }






// Event listener for Log in button
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = log_input_email.getText().toString();
                password = log_input_pw.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                            Sign in success, update UI with the signed-in user's information
                            Log.d(TAG,"signInWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            updateUI(user);
                        } else{
//                        If sign in fails, display a message to the user.
                            Log.w(TAG,"signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        //...
                    }
                });

            }


        });
//     Event listener onclick for  create new account
        log_sign_up.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        });
//      On click event for Forgot Password
        forgot_pw.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this,ForgotActivity.class));
        });

    }

    //        Custom auth Email / Pw


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    public void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());

    }


}



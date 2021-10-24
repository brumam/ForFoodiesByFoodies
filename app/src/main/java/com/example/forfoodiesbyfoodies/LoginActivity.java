package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    // Declare Impostors on all activities
    private EditText log_input_email, log_input_pw;
    private TextView log_sign_up, forgot_pw;
    private Button btn_log;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind impostors

        log_sign_up=(TextView) findViewById(R.id.log_sign_up);
        forgot_pw=(TextView) findViewById(R.id.forgot_pw);
        log_input_pw=(EditText) findViewById(R.id.log_input_pw);
        log_input_email=(EditText) findViewById(R.id.log_input_email);
        btn_log =(Button) findViewById(R.id.btn_log);
        mAuth =FirebaseAuth.getInstance();






// Event listener for Log in button
        btn_log.setOnClickListener(view -> {
            loginUser();
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
    private void loginUser(){
        String email = log_input_email.getText().toString();
        String password = log_input_pw.getText().toString();

        if (TextUtils.isEmpty(email)){
            log_input_email.setError("Email cannot be empty");
            log_input_email.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            log_input_pw.setError("Password cannot be empty");
            log_input_pw.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
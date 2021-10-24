package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
//    Impostors
    private EditText reg_input_email,reg_input_fn,reg_input_sn,reg_input_pw;
    private TextView reg_already;
    private Button btn_reg;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Bind impostors
        reg_already=findViewById(R.id.reg_already);
        mAuth=FirebaseAuth.getInstance();
        reg_input_email=findViewById(R.id.reg_input_email);
        reg_input_fn=findViewById(R.id.reg_input_fn);
        reg_input_sn=findViewById(R.id.reg_input_sn);
        reg_input_pw=findViewById(R.id.reg_input_pw);
        btn_reg = findViewById(R.id.btn_reg);

//      OnClick Event for createUser
        btn_reg.setOnClickListener(view ->{
            createUser();
        });

//      Change class when he already have an account
        reg_already.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

//    Register function to create user in Firebase - Error messages and Toast for successfully or not
    private void createUser(){
        String email = reg_input_email.getText().toString();
        String password = reg_input_pw.getText().toString();

        if (TextUtils.isEmpty(email)){
            reg_input_email.setError("Email cannot be empty");
            reg_input_email.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            reg_input_pw.setError("Password cannot be empty");
            reg_input_pw.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
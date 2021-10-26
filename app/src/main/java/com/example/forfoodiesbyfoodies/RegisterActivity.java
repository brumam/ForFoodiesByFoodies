package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
//    Impostors
    private EditText reg_input_email,reg_input_fn,reg_input_sn,reg_input_pw;
    private TextView reg_already;
    private Button btn_reg;
    private ProgressBar progressBar;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USER = "user";
    private static final String TAG = "RegisterActivity";
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Bind / Init variables
        reg_already=findViewById(R.id.reg_already);
        reg_input_email=findViewById(R.id.reg_input_email);
        reg_input_fn=findViewById(R.id.reg_input_fn);
        reg_input_sn=findViewById(R.id.reg_input_sn);
        reg_input_pw=findViewById(R.id.reg_input_pw);
        btn_reg = findViewById(R.id.btn_reg);
        progressBar = findViewById(R.id.reg_progressBar);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();

//      OnClick Event for createUser check if the user enter email and password in EDIT TEXT
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reg_input_email.getText().toString();
                String password = reg_input_pw.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter email and password",Toast.LENGTH_LONG).show();
                    return;
                }
                String firstName = reg_input_fn.getText().toString();
                String lastName = reg_input_sn.getText().toString();
                user = new User(email,password,firstName,lastName);
                registerUser(email,password);
            }
        });

//      Change class when he already have an account
        reg_already.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

//    Register function to create user in Firebase - Error messages and Toast for successfully or not
    private void registerUser(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            try{
                                Thread.sleep(3000);

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this,"User registered successfully.",Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"createUserWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else{
                            try {
                                Thread.sleep(3000);

                            progressBar.setVisibility(View.GONE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Email error: " + task.getException(),Toast.LENGTH_SHORT).show();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void  updateUI(FirebaseUser currentUser){
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }


}
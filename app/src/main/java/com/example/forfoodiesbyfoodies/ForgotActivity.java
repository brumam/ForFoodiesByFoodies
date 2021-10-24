package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
//    Declare Impostors
    private EditText fw_input_email;
    private TextView fw_log;
    private Button btn_fw;
    private ProgressBar progressBar;

//    Firebase Google - Declare
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

//        Bind Variables
        fw_log = (TextView) findViewById(R.id.fw_log);
        fw_input_email = findViewById(R.id.fw_input_email);
        btn_fw = findViewById(R.id.btn_fw);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();



//        EventListener for Send Reset Link Button and close keyboard onclick event
        btn_fw.setOnClickListener(view ->{
            resetPassword();
            closeKeyboard();

        });



//          EventListener for back to Log IN activity!
        fw_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotActivity.this,LoginActivity.class));
            }

        });

    }

// It was only a test for close keyboard after press the button. It works but is not useful in our case, just curiosity
    private void closeKeyboard(){
    View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    Function Action for ResetPassword with error message

    private void resetPassword(){
        String email = fw_input_email.getText().toString().trim();

        if(email.isEmpty()){
            fw_input_email.setError("Email is required");
            fw_input_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            fw_input_email.setError("Please provide valid email");
            fw_input_email.requestFocus();
            return;
        }
//        Set Progress bar from GONE in VISIBLE and bring to front is useless there I changed the Index in XML with android:elevation ="10dp"
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

//        Toast for Check credentials and Try Again / with a small delay Try function for Progress bar to be more real
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    try{
                        Thread.sleep(3000);

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotActivity.this,"Check your email to reset your password", Toast.LENGTH_LONG).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else{
                    try{
                        Thread.sleep(3000);

                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(ForgotActivity.this,"Try again! Your details does not match in our Database", Toast.LENGTH_LONG).show();

                } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
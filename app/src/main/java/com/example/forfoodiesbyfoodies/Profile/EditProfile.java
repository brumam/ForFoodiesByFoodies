package com.example.forfoodiesbyfoodies.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.forfoodiesbyfoodies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText edit_password, confirm_pw;
    ImageView edit_user_imageview;
    Button edit_save;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    StorageReference storageReference;
    DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_password = findViewById(R.id.edit_prof_pw);
        confirm_pw = findViewById(R.id.edit_prof_confirm_pw);
        edit_user_imageview = findViewById(R.id.edit_user_imageview);
        edit_save = findViewById(R.id.edit_save);


        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("user");

        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edit_password.getText().toString();
                FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();

                fuser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG,"User password updated");
                        Intent i = new Intent(EditProfile.this, ProfileActivity.class);
                    }
                    }
                });


            }
        });

    }

}
package com.example.forfoodiesbyfoodies.ReviewsRest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.App.AppClass;
import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.Restaurant.RecyclerActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;
import java.util.Map;

public class AddReviewRest extends AppCompatActivity {

//    Declare Impostors
    RatingBar ratingBar;
    EditText addreview;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    Button reviewbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_rest);

//        Bind impostors

        reviewbtn = findViewById(R.id.review_btn_submit);
        addreview = findViewById(R.id.rev_et);

        //        initiate a rating bar
        ratingBar = findViewById(R.id.ratingBar);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference("PROFILE_PIC_USERS");


        mDatabase = FirebaseDatabase.getInstance().getReference("Reviews");




//      Set on click, call method startReview and get number of stars with toast
        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = ratingBar.getNumStars();
                
                float ratedValue = ratingBar.getRating();

                Toast.makeText(AddReviewRest.this, "Your rating:" + ratedValue + "/" + total, Toast.LENGTH_SHORT).show();

                startReview();

            }
        });
//
    }

    //    Add new review to Database
    private void startReview(){
        String desc = addreview.getText().toString();
        String fn = AppClass.Session.user.getFirstName();
        String sn = AppClass.Session.user.getLastName();
        String image = AppClass.Session.user.getImageUrl();
        Float rat = ratingBar.getRating();



        DatabaseReference newPost = mDatabase.push();

        Map<String,String> dataToSave = new HashMap<>();
        dataToSave.put("firstName",fn);
        dataToSave.put("lastName",sn);
        dataToSave.put("imageURL",image);
        dataToSave.put("review",desc);
        dataToSave.put("rating", rat.toString());


        newPost.setValue(dataToSave);

        startActivity(new Intent(AddReviewRest.this, RecyclerActivity.class));


    }
}
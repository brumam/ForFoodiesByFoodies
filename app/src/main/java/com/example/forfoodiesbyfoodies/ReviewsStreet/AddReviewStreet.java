package com.example.forfoodiesbyfoodies.ReviewsStreet;

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

import com.example.forfoodiesbyfoodies.StreetFood.StreetRecyclerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;
import java.util.Map;

public class AddReviewStreet extends AppCompatActivity {

//    Declare impostors
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
        setContentView(R.layout.activity_add_review_street);

//        Bind impostors
        reviewbtn = findViewById(R.id.street_review_btn_submit);
        addreview = findViewById(R.id.street_rev_et);

        //        initiate a rating bar
        ratingBar = findViewById(R.id.street_ratingBar);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference("PROFILE_PIC_USERS");
        mDatabase = FirebaseDatabase.getInstance().getReference("ReviewsStreet");


//      Set on click, call method startReview and get number of stars with toast
        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = ratingBar.getNumStars();

                float ratedValue = ratingBar.getRating();

                Toast.makeText(AddReviewStreet.this, "Your rating:" + ratedValue + "/" + total, Toast.LENGTH_SHORT).show();

                startReview();

            }
        });
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

        startActivity(new Intent(AddReviewStreet.this, StreetRecyclerActivity.class));


    }
}
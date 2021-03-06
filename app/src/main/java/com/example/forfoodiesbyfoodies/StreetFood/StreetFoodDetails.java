package com.example.forfoodiesbyfoodies.StreetFood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;


import com.example.forfoodiesbyfoodies.Helpers.StreetFood;
import com.example.forfoodiesbyfoodies.R;


import com.example.forfoodiesbyfoodies.ReviewsStreet.AddReviewStreet;
import com.example.forfoodiesbyfoodies.ReviewsStreet.ReviewStreetList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class StreetFoodDetails extends AppCompatActivity {

//    Declare impostors
    ImageView rest_img;
    TextView rest_desc, rest_name;
    Button review, viewreview;
    TextView strcheck;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_food_details);

//        Bind impostors
        rest_img = findViewById(R.id.street_iv2);
        strcheck = findViewById(R.id.str_check_veg);
        rest_desc = findViewById(R.id.street_tvDesc);
        rest_name = findViewById(R.id.street_tvName);
        review = findViewById(R.id.street_add_review);
        viewreview = findViewById(R.id.street_rest_btn_viewrev);


//        Get intent from Street Food Recycler

        Intent i = getIntent();

        StreetFood street = i.getParcelableExtra("Street");
        dbref = FirebaseDatabase.getInstance().getReference("StreetFood");



//        Set details

        Picasso.get().load(street.getImageURL()).fit().into(rest_img);
        rest_name.setText(street.getName());
        rest_desc.setText(street.getDescription());
        strcheck.setText(street.getCheckbox());




//        Set on click listener Add review

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rev = new Intent(StreetFoodDetails.this, AddReviewStreet.class);

                startActivity(rev);
            }
        });

//        Set on click listener View Reviews
        viewreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewrev = new Intent(StreetFoodDetails.this, ReviewStreetList.class);

                startActivity(viewrev);
            }
        });


    }

}
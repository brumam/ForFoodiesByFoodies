package com.example.forfoodiesbyfoodies.StreetFood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forfoodiesbyfoodies.App.AppClass;
import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.Helpers.StreetFood;
import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.Restaurant.RestaurantDetails;
import com.example.forfoodiesbyfoodies.Restaurant.WebActivity;
import com.example.forfoodiesbyfoodies.ReviewsRest.AddReviewRest;
import com.example.forfoodiesbyfoodies.ReviewsRest.ReviewList;
import com.example.forfoodiesbyfoodies.ReviewsStreet.AddReviewStreet;
import com.example.forfoodiesbyfoodies.ReviewsStreet.ReviewStreetList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class StreetFoodDetails extends AppCompatActivity {

    ImageView rest_img;
    TextView rest_desc, rest_name;
    Button review, viewreview;
    TextView strcheck;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_food_details);

        rest_img = findViewById(R.id.street_iv2);
        strcheck = findViewById(R.id.str_check_veg);
        rest_desc = findViewById(R.id.street_tvDesc);
        rest_name = findViewById(R.id.street_tvName);
        review = findViewById(R.id.street_add_review);
        viewreview = findViewById(R.id.street_rest_btn_viewrev);



        Intent i = getIntent();

        StreetFood street = i.getParcelableExtra("Street");
        dbref = FirebaseDatabase.getInstance().getReference("StreetFood");




        Picasso.get().load(street.getImageURL()).fit().into(rest_img);
        rest_name.setText(street.getName());
        rest_desc.setText(street.getDescription());
        strcheck.setText(street.getCheckbox());





        if(AppClass.Session.user.getUserType().compareTo("user") ==0){
            review.setVisibility(View.INVISIBLE);
        }else{
            review.setVisibility(View.VISIBLE);
        }



        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rev = new Intent(StreetFoodDetails.this, AddReviewStreet.class);
                rev.putExtra("image",street.getImageURL());
                startActivity(rev);
            }
        });

        viewreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewrev = new Intent(StreetFoodDetails.this, ReviewStreetList.class);

                startActivity(viewrev);
            }
        });


    }

}
package com.example.forfoodiesbyfoodies.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.App.AppClass;
import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.Profile.ProfileActivity;
import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.ReviewsRest.AddReviewRest;
import com.example.forfoodiesbyfoodies.ReviewsRest.ReviewList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RestaurantDetails extends AppCompatActivity {

//    Declare impostors

    List<Restaurant> restList;


    ImageView rest_img;
    TextView rest_desc, rest_name;
    TextView bookingURL;
    Button btnbook, review, viewreview;
    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);


//        Bind impostors


        rest_img = findViewById(R.id.iv2);
        rest_desc = findViewById(R.id.tvDesc);
        rest_name = findViewById(R.id.tvName);
        btnbook = findViewById(R.id.btn_booking);
        bookingURL = findViewById(R.id.bookingURL);
        review = findViewById(R.id.add_review);
        viewreview = findViewById(R.id.rest_btn_viewrev);


//        Get intent from RecyclerActivity - Restaurant

        Intent i = getIntent();

        Restaurant rest = i.getParcelableExtra("Restaurant");
        dbref = FirebaseDatabase.getInstance().getReference("Restaurants");





//        Set details
        Picasso.get().load(rest.getImageURL()).fit().into(rest_img);
        rest_name.setText(rest.getName());
        rest_desc.setText(rest.getDescription());
        bookingURL.setText(rest.getBookingURL());

        String url = bookingURL.getText().toString();


//      Check userType - for review permissions
        if(AppClass.Session.user.getUserType().compareTo("user") ==0){
            review.setVisibility(View.INVISIBLE);
        }else{
            review.setVisibility(View.VISIBLE);
        }


//        Set on click listener Add review
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rev = new Intent(RestaurantDetails.this, AddReviewRest.class);
                startActivity(rev);
            }
        });
//        Set on click listener View Reviews
        viewreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewrev = new Intent(RestaurantDetails.this, ReviewList.class);

                startActivity(viewrev);
            }
        });

//        Book button putExtra - String BookingURL
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(RestaurantDetails.this,WebActivity.class);
                is.putExtra("key",bookingURL.getText().toString());
                startActivity(is);
            }
        });

    }



}
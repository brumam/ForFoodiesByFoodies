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


    List<Restaurant> restList;


    ImageView rest_img;
    TextView rest_desc, rest_name,db;
    TextView bookingURL;
    Button btnbook, review, viewreview;
    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);



        rest_img = findViewById(R.id.iv2);
        rest_desc = findViewById(R.id.tvDesc);
        rest_name = findViewById(R.id.tvName);
        btnbook = findViewById(R.id.btn_booking);
        bookingURL = findViewById(R.id.bookingURL);
        review = findViewById(R.id.add_review);
        viewreview = findViewById(R.id.rest_btn_viewrev);
        db = findViewById(R.id.db_ref);


        Intent i = getIntent();

        Restaurant rest = i.getParcelableExtra("Restaurant");
        dbref = FirebaseDatabase.getInstance().getReference("Restaurants");




        Picasso.get().load(rest.getImageURL()).fit().into(rest_img);
        rest_name.setText(rest.getName());
        rest_desc.setText(rest.getDescription());
        bookingURL.setText(rest.getBookingURL());

        String url = bookingURL.getText().toString();







        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rev = new Intent(RestaurantDetails.this, AddReviewRest.class);
                startActivity(rev);
            }
        });

        viewreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewrev = new Intent(RestaurantDetails.this, ReviewList.class);

                startActivity(viewrev);
            }
        });




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
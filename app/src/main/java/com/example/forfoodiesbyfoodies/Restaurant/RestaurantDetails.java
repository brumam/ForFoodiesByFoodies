package com.example.forfoodiesbyfoodies.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;


public class RestaurantDetails extends AppCompatActivity {
    ImageView rest_img;
    TextView rest_desc, rest_name;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        rest_img = findViewById(R.id.iv2);
        rest_desc = findViewById(R.id.tvDesc);
        rest_name = findViewById(R.id.tvName);
        button3 = findViewById(R.id.button3);

        Intent i = getIntent();

        Restaurant rest = i.getParcelableExtra("Restaurant");


        Picasso.get().load(rest.getImageURL()).fit().into(rest_img);
        rest_name.setText(rest.getName());
        rest_desc.setText(rest.getDescription());



    }
}
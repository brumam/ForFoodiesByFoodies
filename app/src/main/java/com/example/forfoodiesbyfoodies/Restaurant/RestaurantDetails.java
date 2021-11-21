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

import com.example.forfoodiesbyfoodies.App.AppClass;
import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.Profile.ProfileActivity;
import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;


public class RestaurantDetails extends AppCompatActivity {
    WebView wv;

    ImageView rest_img;
    TextView rest_desc, rest_name;
    TextView bookingURL;
    Button btnbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        wv = findViewById(R.id.id_wv);
        wv.setWebViewClient(new WebViewClient());

        rest_img = findViewById(R.id.iv2);
        rest_desc = findViewById(R.id.tvDesc);
        rest_name = findViewById(R.id.tvName);
        btnbook = findViewById(R.id.btn_booking);
        bookingURL = findViewById(R.id.bookingURL);


        Intent i = getIntent();

        Restaurant rest = i.getParcelableExtra("Restaurant");


        Picasso.get().load(rest.getImageURL()).fit().into(rest_img);
        rest_name.setText(rest.getName());
        rest_desc.setText(rest.getDescription());
        bookingURL.setText(rest.getBookingURL());

        String url = bookingURL.getText().toString();

        wv.loadUrl(url);

        WebSettings settings = wv.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);








        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent books = new Intent(RestaurantDetails.this,WebActivity.class);
                books.putExtra("BOOKs",rest_name.getText().toString());
                startActivity(books);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(wv.canGoBack()){
            wv.goBack();
        } else {
            super.onBackPressed();
        }


    }
}
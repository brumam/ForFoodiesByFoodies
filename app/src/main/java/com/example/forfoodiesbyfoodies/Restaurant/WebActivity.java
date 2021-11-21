package com.example.forfoodiesbyfoodies.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.R;

public class WebActivity extends AppCompatActivity {

    TextView testurl;

    String book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        testurl = findViewById(R.id.bookingURL);
        Intent i = getIntent();
        book = i.getStringExtra("book");



        testurl.setText(book);


    }
}
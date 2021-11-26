package com.example.forfoodiesbyfoodies.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.R;

public class WebActivity extends AppCompatActivity {

//    Declare impostors

    WebView wv;

    String book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

//        Bind impostors

        wv = findViewById(R.id.wv_id);
        wv.setWebViewClient(new WebViewClient());

//        get intent key from restaurant details

        Intent i = getIntent();

        book = i.getStringExtra("key");

        wv.loadUrl(book);
//      Set web settings
        WebSettings settings = wv.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);



    }

//    Avoid on back press button
    @Override
    public void onBackPressed() {
        if(wv.canGoBack()){
            wv.goBack();
        } else {
            super.onBackPressed();
        }


    }
}
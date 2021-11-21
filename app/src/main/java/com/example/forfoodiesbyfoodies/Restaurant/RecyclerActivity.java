package com.example.forfoodiesbyfoodies.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.Adaptors.EateryAdaptor;
import com.example.forfoodiesbyfoodies.App.AppClass;
import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.Helpers.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity implements EateryAdaptor.EateryHolder.RestaurantInterface {
        RecyclerView rv;
        EateryAdaptor adapter;
        Button add_rest;

        private String TAG = "user";

        DatabaseReference mDatabase;
        FirebaseUser fUser;
        FirebaseAuth mAuth;




        List<Restaurant> restList =  new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);


            add_rest = findViewById(R.id.add_restaurant);



            mAuth = FirebaseAuth.getInstance();
            fUser = mAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference("user");







        add_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerActivity.this,AddRestaurantActivity.class);
                startActivity(intent);
            }
        });

        rv = findViewById(R.id.rv_eatery);
        rv.setLayoutManager(new LinearLayoutManager(RecyclerActivity.this));
        FirebaseDatabase.getInstance().getReference("Restaurants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss: snapshot.getChildren()){
                    Restaurant rest = dss.getValue(Restaurant.class);
                    restList.add(rest);



                   }

                adapter = new EateryAdaptor(restList, RecyclerActivity.this);

                rv.setAdapter(adapter);
                Collections.sort(restList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant o1, Restaurant o2) {
                        return o1.getName().compareTo(o2.getName());
                    }

                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

            if (AppClass.Session.user.getUserType().compareTo("admin")==0){
                add_rest.setVisibility(View.VISIBLE);
            }else{
                add_rest.setVisibility(View.INVISIBLE);
            }



//
//        if(rest.isCanBook()){
//            button3.setVisibility(View.VISIBLE);
//        }else{
//            button3.setVisibility(View.GONE);
//        }



    }



    @Override
    public void onRestaurantClick(int i) {
        Intent intent = new Intent(RecyclerActivity.this, RestaurantDetails.class);
        intent.putExtra("Restaurant", restList.get(i));
        startActivity(intent);

//        Intent is = new Intent(RecyclerActivity.this,WebActivity.class);
//        is.putExtra("key",restList.get(i));


    }
}
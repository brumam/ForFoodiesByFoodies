package com.example.forfoodiesbyfoodies.StreetFood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.forfoodiesbyfoodies.Adaptors.StreetFoodAdapter;
import com.example.forfoodiesbyfoodies.Helpers.StreetFood;
import com.example.forfoodiesbyfoodies.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StreetRecyclerActivity extends AppCompatActivity implements StreetFoodAdapter.sHolder.streetFoodInterface {
//    Declare impostors
    RecyclerView rv;
    ImageView logo;
    StreetFoodAdapter adapter;
    Button add_street;

    DatabaseReference mDatabase;
    FirebaseUser mAuth;

//    Call List array for StreetFood Helper Class
    List<StreetFood> streetFoodList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_recycler);

//        Bind impostors

        add_street = findViewById(R.id.street_add_restaurant);
        logo = findViewById(R.id.street_main_logo);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference("user");



//      Set on click listener - Add Street Food
        add_street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreetRecyclerActivity.this,AddStreetFoodActivity.class);
                startActivity(intent);
            }
        });

//      Get Street Card -
        rv = findViewById(R.id.street_rv_eatery);
//      Set layout manager - Linear
        rv.setLayoutManager(new LinearLayoutManager(StreetRecyclerActivity.this));

//        Get instance firebase - for StreetFood - DataSnapshot - List array
        FirebaseDatabase.getInstance().getReference("StreetFood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss: snapshot.getChildren()){
                    StreetFood streetFood = dss.getValue(StreetFood.class);
                    streetFoodList.add(streetFood);
                }

                adapter = new StreetFoodAdapter(streetFoodList,StreetRecyclerActivity.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    Override Adapter - and send to StreetFood Details
    @Override
    public void onStreetClick(int i) {
        Intent intent = new Intent(StreetRecyclerActivity.this, StreetFoodDetails.class);
        intent.putExtra("Street", streetFoodList.get(i));


        startActivity(intent);
    }
}
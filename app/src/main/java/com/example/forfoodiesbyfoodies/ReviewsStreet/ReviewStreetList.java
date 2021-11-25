package com.example.forfoodiesbyfoodies.ReviewsStreet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.forfoodiesbyfoodies.Adaptors.ReviewAdapter;
import com.example.forfoodiesbyfoodies.Adaptors.ReviewStreetAdapter;
import com.example.forfoodiesbyfoodies.Helpers.ReviewsRest;
import com.example.forfoodiesbyfoodies.Helpers.ReviewsStreet;
import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.ReviewsRest.ReviewList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewStreetList extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView.LayoutManager manager;

    List<ReviewsStreet> streetList = new ArrayList<>();

    ReviewStreetAdapter adapter;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_street_list);

        rv = findViewById(R.id.street_id_rv);
        dbref = FirebaseDatabase.getInstance().getReference("ReviewsStreet");
        manager = new LinearLayoutManager(ReviewStreetList.this);
        rv.setLayoutManager(manager);

        FirebaseDatabase.getInstance().getReference("ReviewsStreet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss: snapshot.getChildren()){
                    ReviewsStreet street = dss.getValue(ReviewsStreet.class);
                    streetList.add(street);


                }
                adapter = new ReviewStreetAdapter(streetList);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
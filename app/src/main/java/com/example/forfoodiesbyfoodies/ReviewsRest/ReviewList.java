package com.example.forfoodiesbyfoodies.ReviewsRest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.forfoodiesbyfoodies.Adaptors.ReviewAdapter;
import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.Helpers.ReviewsRest;
import com.example.forfoodiesbyfoodies.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewList extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView.LayoutManager manager;

    List<ReviewsRest> revList = new ArrayList<>();
    List<Restaurant> restList;

    ReviewAdapter adapter;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        rv = findViewById(R.id.id_rv);
        dbref = FirebaseDatabase.getInstance().getReference("Reviews");
        manager = new LinearLayoutManager(ReviewList.this);
        rv.setLayoutManager(manager);



        FirebaseDatabase.getInstance().getReference("Reviews").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss: snapshot.getChildren()){
                    ReviewsRest rev = dss.getValue(ReviewsRest.class);
                    revList.add(rev);


                }
                adapter = new ReviewAdapter(revList);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
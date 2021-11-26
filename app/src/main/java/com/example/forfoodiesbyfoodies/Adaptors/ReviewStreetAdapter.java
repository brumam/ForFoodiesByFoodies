package com.example.forfoodiesbyfoodies.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.Helpers.ReviewsStreet;
import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewStreetAdapter extends RecyclerView.Adapter<ReviewStreetAdapter.StreetHolder> {

    // Call array list ReviewsStreet Helper Class
    List<ReviewsStreet> streetList;

    //    Constructor ReviewsStreet
    public ReviewStreetAdapter(List<ReviewsStreet> streetList) {
        this.streetList = streetList;

    }

    // Override StreetHolder - and inflate Eatery Card
    @NonNull
    @Override
    public StreetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcard,parent,false);
        StreetHolder streetHolder = new StreetHolder(v);
        return streetHolder;
    }
    //    Set details for Card

    @Override
    public void onBindViewHolder(@NonNull StreetHolder holder, int position) {
        holder.rev.setText(streetList.get(position).getReview());
        holder.rating.setRating(Float.parseFloat(streetList.get(position).getRating()));
        holder.fn.setText(streetList.get(position).getFirstName());
        holder.sn.setText(streetList.get(position).getLastName());
        Picasso.get().load(streetList.get(position).getImageURL()).fit().into(holder.iv);
    }

    //    Count items
    @Override
    public int getItemCount() {
        return streetList.size();
    }

    //    Extends recyclerview
    public static class StreetHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView fn,sn,rev;
        RatingBar rating;

        public StreetHolder(@NonNull View itemView){
            super(itemView);
            iv = itemView.findViewById(R.id.rev_prof);
            fn = itemView.findViewById(R.id.rev_fn);
            sn = itemView.findViewById(R.id.rev_sn);
            rev = itemView.findViewById(R.id.rev_review);
            rating = itemView.findViewById(R.id.rev_rating);
        }
    }


}

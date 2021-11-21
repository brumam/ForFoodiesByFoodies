package com.example.forfoodiesbyfoodies.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.Helpers.ReviewsRest;
import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends  RecyclerView.Adapter<ReviewAdapter.Holder> {

    List<ReviewsRest> revList;

    public ReviewAdapter(List<ReviewsRest> revList) {
        this.revList = revList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcard,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.rev.setText(revList.get(position).getReview());
        holder.rating.setRating(Float.parseFloat(revList.get(position).getRating()));
        holder.fn.setText(revList.get(position).getFirstName());
        holder.sn.setText(revList.get(position).getLastName());
        Picasso.get().load(revList.get(position).getImageURL()).fit().into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return revList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView fn,sn,rev;
        RatingBar rating;


        public Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.rev_prof);
            fn = itemView.findViewById(R.id.rev_fn);
            sn = itemView.findViewById(R.id.rev_sn);
            rev = itemView.findViewById(R.id.rev_review);
            rating = itemView.findViewById(R.id.rev_rating);
        }
    }
}

package com.example.forfoodiesbyfoodies.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.Helpers.Restaurant;
import com.example.forfoodiesbyfoodies.R;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EateryAdaptor extends RecyclerView.Adapter<EateryAdaptor.EateryHolder> {

    List<Restaurant> userList;

    EateryHolder.RestaurantInterface listener;

    public EateryAdaptor(List<Restaurant> userList, EateryHolder.RestaurantInterface _listener) {
        this.userList = userList;
        listener = _listener;
    }

    @NonNull
    @Override
    public EateryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eaterycard, parent, false);
        return new EateryHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EateryHolder holder, int position) {
        holder.tv.setText(userList.get(position).getName());
        Picasso.get().load(userList.get(position).getImageURL()).fit().into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class EateryHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView iv;
        TextView tv;
        RestaurantInterface listener;

        public EateryHolder(@NonNull View itemView, RestaurantInterface _listener) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_card_image);
            tv = itemView.findViewById(R.id.tv_card_name);
            listener = _listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        listener.onRestaurantClick(getAdapterPosition());
        }

        public interface RestaurantInterface{
            public void onRestaurantClick(int i);
        }
    }
}

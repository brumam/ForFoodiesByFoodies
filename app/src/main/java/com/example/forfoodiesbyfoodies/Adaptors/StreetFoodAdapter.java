package com.example.forfoodiesbyfoodies.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.Helpers.StreetFood;
import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StreetFoodAdapter extends RecyclerView.Adapter<StreetFoodAdapter.sHolder> {
    // Call array list StreetFood Helper Class
    List<StreetFood> streetFoodList;

    //  StreetFood interface  - listener
    sHolder.streetFoodInterface listener;

    //    Constructor StreetFood
    public StreetFoodAdapter(List<StreetFood> streetFoodList,sHolder.streetFoodInterface _listener) {
        this.streetFoodList = streetFoodList;
        listener = _listener;
    }

    // Override sHolder - and inflate Street Card
    @NonNull
    @Override
    public sHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.streetcard,parent,false);
        return new sHolder(v,listener);
    }

    //    Set details for Card
    @Override
    public void onBindViewHolder(@NonNull sHolder holder, int position) {
        holder.tv.setText(streetFoodList.get(position).getName());
        Picasso.get().load(streetFoodList.get(position).getImageURL()).fit().into(holder.iv);
    }


    //    Count items
    @Override
    public int getItemCount() {
        return streetFoodList.size();
    }

    //    Extends recyclerview
    public  static class sHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv;
        TextView tv;
        streetFoodInterface listener;


        public sHolder(@NonNull  View itemView,streetFoodInterface _listener) {
            super(itemView);
            iv = itemView.findViewById(R.id.street_iv_card_image);
            tv = itemView.findViewById(R.id.street_tv_card_name);
            listener = _listener;
            itemView.setOnClickListener(this);
        }
        //        Get position from count items and send details on Recycler View
        @Override
        public void onClick(View v) {
            listener.onStreetClick(getAdapterPosition());
        }

        public interface streetFoodInterface{
           public void onStreetClick(int i);


        }
    }

}

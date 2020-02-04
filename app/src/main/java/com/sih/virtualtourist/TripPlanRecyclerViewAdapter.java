package com.sih.virtualtourist;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripPlanRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<List<String>> data;
    private List<String> distances;
    private List<String> names;

    public TripPlanRecyclerViewAdapter(List<List<String>> data){
        this.data = data;
        //Deconstruct Bundle Data
        this.distances = data.get(0);
        this.names = data.get(1);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public CustomViewHolder(CardView cardView){
            super(cardView);
            cv = cardView;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_plan_cardview, parent, false);
        return new CustomViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.tv_trip_place_name);
        name.setText(names.get(position));
        TextView distance = holder.itemView.findViewById(R.id.tv_place_distance);
        distance.setText(distances.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}

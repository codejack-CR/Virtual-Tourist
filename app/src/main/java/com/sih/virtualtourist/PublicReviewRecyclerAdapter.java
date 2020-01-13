package com.sih.virtualtourist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PublicReviewRecyclerAdapter extends RecyclerView.Adapter {

    private int itemCount;
    private String data;

    public PublicReviewRecyclerAdapter(int count, String data){
        this.itemCount = count;
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

package com.sih.virtualtourist;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PublicReviewRecyclerAdapter extends RecyclerView.Adapter {

    private List<String> reviews;
    //TODO: Create a helper class to fetch Drawables from InputStream of Firebase and update the Adapter
    private List<Drawable> userProfilePics;
    public PublicReviewRecyclerAdapter(List<String> reviews){
        this.reviews = reviews;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cv;
        public ViewHolder(CardView cardView){
            super(cardView);
            cv = cardView;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card_layout, parent, false);
        return new ViewHolder(cardView);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView review = holder.itemView.findViewById(R.id.tv_user_review);
        review.setText(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}

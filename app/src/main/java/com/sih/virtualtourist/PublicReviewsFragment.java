package com.sih.virtualtourist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PublicReviewsFragment extends Fragment {

    public PublicReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public_reviews, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        TextView tv = view.findViewById(R.id.tv_public_reviews);
        StringBuilder s = new StringBuilder();
        for(int i=0;i<9999;i++){
            s.append("Review\n");
        }
        tv.setText(s.toString());
    }
}

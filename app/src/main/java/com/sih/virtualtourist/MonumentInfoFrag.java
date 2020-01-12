package com.sih.virtualtourist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MonumentInfoFrag extends Fragment {

    public MonumentInfoFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monument_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        TextView tv = view.findViewById(R.id.tv_monument_info);
        StringBuilder s = new StringBuilder();
        for(int i=0;i<9999;i++,s.append("Info\n"));
        tv.setText(s.toString());
    }
}

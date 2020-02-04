package com.sih.virtualtourist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlannedTripFragment extends Fragment {

    public PlannedTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planned_trip_activity, container, false);
    }

    public static PlannedTripFragment instantiate(Bundle args){
        PlannedTripFragment fragment = new PlannedTripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_trip_plan);
        TripPlanRecyclerViewAdapter adapter = new TripPlanRecyclerViewAdapter((List<List<String>>) getArguments().getSerializable("DATA"));
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}

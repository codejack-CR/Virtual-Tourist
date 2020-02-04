package com.sih.virtualtourist;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TripPlannerActivity extends AppCompatActivity {
    private FusedLocationProviderClient locationProviderClient;
    private double currLatitude; private double currLongitude;
    private AutoCompleteTextView mInput;
    private FloatingActionButton mPlanTripButton;
    private AppCompatTextView mGoingPlaces;
    private FloatingActionButton mAddButton;
    String[] favs = {"Calangute Beach", "Basilica of Bom Jesus", "Water Sports in Goa", "Fort Aguada", "Baga Beach"};
    List<String> goingPlaces = new ArrayList<>();
    List<Address> addresses = new ArrayList<>();

    private void initializeViewsOnCreate(){
        mInput = findViewById(R.id.place_autocomplete_query_text);
        mPlanTripButton = findViewById(R.id.fab_plan_trip);
        mGoingPlaces = findViewById(R.id.tv_going_places);
        mAddButton = findViewById(R.id.fab_add_place);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, favs);
        mInput.setAdapter(adapter);
        mInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                goingPlaces.add(selected);
            }
        });
    }

    private void getGeoCodes(List<String> locations) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        Iterator iterator = locations.iterator();
        while(iterator.hasNext()){
            this.addresses.add(geocoder.getFromLocationName((String) iterator.next(), 1).get(0));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_trip_main);
        initializeViewsOnCreate();
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!manager.isLocationEnabled()){
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setOnClickListeners();
    }

    private void setOnClickListeners(){
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPlace = mInput.getEditableText().toString();
                if(!enteredPlace.isEmpty()){
                    goingPlaces.add(enteredPlace);
                }
                mInput.setText("");
            }
        });
        mPlanTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getGeoCodes(goingPlaces);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    planTrip();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if(location != null){
                getLocationParams(location);
            }
        });
    }

    private void getLocationParams(Location location){
        this.currLatitude = location.getLatitude();
        this.currLongitude = location.getLongitude();
    }


    private void planTrip(){
        //TODO: Passing the arguments to the OSRM backend to fetch the trip details
        StringBuilder requestBuilderForOSRM = new StringBuilder("http://router.project-osrm.org/trip/v1/driving/");
        int index = 1;
        for(Address address : addresses){
            requestBuilderForOSRM.append(address.getLongitude()).append(",").append(address.getLatitude());
            if(index < addresses.size()){
                requestBuilderForOSRM.append(";");
            }
        }
        requestBuilderForOSRM.append("?source=first&destination=last");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, requestBuilderForOSRM.toString(), response -> {
            try {
                populateUI(OSRMParser.parseMapData(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {} );
        queue.add(request);
    }

    private void populateUI(List<List<String>> data){
        //TODO: Populating the UI with the parsed data
        Bundle args = new Bundle();
        args.putSerializable("DATA", (Serializable) data);
        PlannedTripFragment tripFragment = PlannedTripFragment.instantiate(args);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_for_plan_fragment, tripFragment).commit();
    }
}

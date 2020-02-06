package com.sih.virtualtourist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TripPlannerActivity extends AppCompatActivity {
//    private FusedLocationProviderClient locationProviderClient;
//    private double currLatitude; private double currLongitude;
//    private AutoCompleteTextView mInput;
//    private FloatingActionButton mPlanTripButton;
//    private AppCompatTextView mGoingPlaces;
//    private FloatingActionButton mAddButton;
//    String[] favs = {"Calangute Beach", "Basilica of Bom Jesus", "Water Sports in Goa", "Fort Aguada", "Baga Beach"};
//    List<String> goingPlaces = new ArrayList<>();
//    List<Address> addresses = new ArrayList<>();
//
//    private void initializeViewsOnCreate(){
//        mInput = findViewById(R.id.place_autocomplete_query_text);
//        mPlanTripButton = findViewById(R.id.fab_plan_trip);
//        mGoingPlaces = findViewById(R.id.tv_going_places);
//        mAddButton = findViewById(R.id.fab_add_place);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, favs);
//        mInput.setAdapter(adapter);
//        mInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selected = parent.getItemAtPosition(position).toString();
//                goingPlaces.add(selected);
//            }
//        });
//    }
//
//    private void getGeoCodes(List<String> locations) throws IOException {
//        Geocoder geocoder = new Geocoder(this);
//        Iterator iterator = locations.iterator();
//        while(iterator.hasNext()){
//            this.addresses.add(geocoder.getFromLocationName((String) iterator.next(), 1).get(0));
//        }
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.plan_trip_main);
//        initializeViewsOnCreate();
//        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if(!manager.isLocationEnabled()){
//            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//        }
//        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        setOnClickListeners();
//    }
//
//    private void setOnClickListeners(){
//        mAddButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String enteredPlace = mInput.getEditableText().toString();
//                if(!enteredPlace.isEmpty()){
//                    goingPlaces.add(enteredPlace);
//                }
//                mInput.setText("");
//            }
//        });
//        mPlanTripButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    getGeoCodes(goingPlaces);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                finally {
//                    planTrip();
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        locationProviderClient.getLastLocation().addOnSuccessListener(location -> {
//            if(location != null){
//                getLocationParams(location);
//            }
//        });
//    }
//
//    private void getLocationParams(Location location){
//        this.currLatitude = location.getLatitude();
//        this.currLongitude = location.getLongitude();
//    }
//
//
//    private void planTrip(){
//        //TODO: Passing the arguments to the OSRM backend to fetch the trip details
//        StringBuilder requestBuilderForOSRM = new StringBuilder("http://router.project-osrm.org/trip/v1/driving/");
//        int index = 1;
//        for(Address address : addresses){
//            requestBuilderForOSRM.append(address.getLongitude()).append(",").append(address.getLatitude());
//            if(index < addresses.size()){
//                requestBuilderForOSRM.append(";");
//            }
//        }
//        requestBuilderForOSRM.append("?source=first&destination=last");
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest request = new StringRequest(Request.Method.GET, requestBuilderForOSRM.toString(), response -> {
//            try {
//                populateUI(OSRMParser.parseMapData(response));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> {} );
//        queue.add(request);
//    }
//
//    private void populateUI(List<List<String>> data){
//        //TODO: Populating the UI with the parsed data
//        Bundle args = new Bundle();
//        args.putSerializable("DATA", (Serializable) data);
//        PlannedTripFragment tripFragment = PlannedTripFragment.instantiate(args);
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_for_plan_fragment, tripFragment).commit();
//    }
    private IMapController mapController;
    private MapView mapView;
    private FloatingActionButton mPlanConfirm;
    private AppCompatAutoCompleteTextView mInput;
    private String[] favs = {"Calangute Beach", "Basilica of Bom Jesus", "Water Sports in Goa", "Fort Aguada", "Baga Beach"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_trip_main);
        mPlanConfirm = findViewById(R.id.fab_trip_plan_trip);
        mPlanConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
//        ArrayAdapter adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_multiple_choice, favs);
//        mInput.setAdapter(adapter);
        mapView = findViewById(R.id.map_view_plan_trip);
        mapController = mapView.getController();
        mapController.setZoom(14);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!manager.isLocationEnabled()){
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if(location != null){
                double lats = location.getLatitude();
                double longs = location.getLongitude();
                GeoPoint geoPoint = new GeoPoint(lats,longs);
                mapController.setCenter(geoPoint);
                Marker startMarker = new Marker(mapView);
                startMarker.setPosition(geoPoint);
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                startMarker.setSubDescription("YOU ARE HERE !");
                mapView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        connect();
                        return false;
                    }
                });
                mapView.getOverlays().add(startMarker);
                final String userAgent = RandomStringUtils.randomAlphabetic(8)+"/"+"1.0";
                NominatimPOIProvider poiProvider = new NominatimPOIProvider(userAgent);
                ArrayList<POI> pois;
                do {
                    pois = poiProvider.getPOICloseTo(geoPoint, "attraction", 5, 1.5);
                }while(pois == null);
                FolderOverlay poiMarkers = new FolderOverlay(this);
                mapView.getOverlays().add(poiMarkers);
                Drawable poiIcon = getResources().getDrawable(R.drawable.marker_default_focused_base);
                for (POI poi:pois){
                    Marker poiMarker = new Marker(mapView);
                    poiMarker.setTitle(poi.mType);
                    poiMarker.setSnippet(poi.mDescription);
                    poiMarker.setPosition(poi.mLocation);
                    poiMarker.setIcon(poiIcon);
//                    poiMarker.setInfoWindow(new CustomInfoWindow(mapView));
                    poiMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker, MapView mapView) {
                            ArrayList<GeoPoint> temp = new ArrayList<>();
                            temp.addAll(geoPoints);
                            geoPoints.clear();
                            temp.add(marker.getPosition());
                            geoPoints.addAll(temp);
                            Toast.makeText(getApplicationContext(), "Place Selected !", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    });
                    poiMarker.setRelatedObject(poi);
                    if (poi.mThumbnail != null){
                        poiMarker.setImage( new BitmapDrawable(poi.mThumbnail));
                    }
                    poiMarkers.add(poiMarker);
                }
                mapView.invalidate();
            }
        });


    }
    ArrayList<GeoPoint> geoPoints = new ArrayList<>();

//    public class CustomInfoWindow extends MarkerInfoWindow{
//        POI mSelectedPoi;
//        public CustomInfoWindow(MapView mapView, Marker marker){
//            super(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, mapView);
//            Button btn = mapView.findViewById(marker.);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mSelectedPoi.mLocation != null){
//                        //TODO: Passing this as a reference
//                        geoPoints.add(mSelectedPoi.mLocation);
//                        //connect();
//                    }
//                }
//            });
//        }
//        @Override public void onOpen(Object item){
//            super.onOpen(item);
//            mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
//            Marker marker = (Marker) item;
//            mSelectedPoi = (POI)marker.getRelatedObject();
//        }
//    }

    private void connect(){
        RoadManager roadManager = new OSRMRoadManager(this);
        Road road = roadManager.getRoad(geoPoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
        mapView.getOverlays().add(roadOverlay);
        mapView.invalidate();
        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();

    }
}

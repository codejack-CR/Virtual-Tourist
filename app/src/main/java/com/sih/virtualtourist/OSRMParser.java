package com.sih.virtualtourist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OSRMParser {

    public static List<String> parseMapData(String json) throws JSONException {
        //Parsing the distance info
        List<String> distanceInfo = new ArrayList<>();
        List<String> nameOfPlace = new ArrayList<>();
        List<String> coordinates = new ArrayList<>();
        JSONObject page = new JSONObject(json);
        JSONObject trip = page.getJSONArray("trips").getJSONObject(0);
        JSONArray wayPointArray = trip.getJSONArray("legs");
        for(int i=0; i < wayPointArray.length();i++){
            JSONObject wayPoint = wayPointArray.getJSONObject(i);
            String distance = wayPoint.getString("distance");
            distanceInfo.add(distance);
        }
        //Parsing the wayPointInfo
        JSONArray wayPoints = page.getJSONArray("waypoints");
        for(int i=0; i<wayPoints.length(); i++){
            JSONObject wayPoint = wayPoints.getJSONObject(i);
            String name = wayPoint.getString("name");
            nameOfPlace.add(name);
            JSONArray coords = wayPoint.getJSONArray("location");
            for(int j=0;i<2;i++){
                coordinates.add(coords.getString(i));
            }
        }
        return coordinates;
    }
}

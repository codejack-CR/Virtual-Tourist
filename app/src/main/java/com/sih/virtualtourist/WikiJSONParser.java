package com.sih.virtualtourist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class WikiJSONParser {

    public static String getMonumentInfo(String JSON){
        String info = null;
        try{
            info = JSONParse(JSON);
        }
        catch(JSONException e ){
            e.printStackTrace();
        }
        return info;
    }

    private static String JSONParse(String JSON) throws JSONException {
        JSONObject page = new JSONObject(JSON.toString());
        JSONObject query = page.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");
        Iterator<String> pageIter = pages.keys();
        String pageID = pageIter.next();
        String result = pages.getJSONObject(pageID).getString("extract");
        return result;
    }
}
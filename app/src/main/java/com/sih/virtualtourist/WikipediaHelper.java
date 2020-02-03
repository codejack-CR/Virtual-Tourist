package com.sih.virtualtourist;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WikipediaHelper{
    public static final StringBuilder WIKI_STATIC = new StringBuilder("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=");

//    public static String getSummaryFromWiki(String charSequence) throws IOException{
//        String response = null;
//        response = getResponseFromURL(getURL(charSequence));
////        return WikiJSONParser.getMonumentInfo(response);
//        return response;
//    }
//    private static String extract;
//    private static URL getURL(String query) throws MalformedURLException {
//        Uri request = new Uri.Builder().scheme("http").authority("en.wikipedia.org").appendPath("w").appendPath("api.php")
//                .appendQueryParameter("format" , "json").appendQueryParameter("action", "query").appendQueryParameter("prop", "extract")
//                .appendQueryParameter("exintro", "").appendQueryParameter("explaintext", "").appendQueryParameter("redirects", "")
//                .appendQueryParameter("titles", query).build();
//        return new URL(request.toString());
//    }
////
////    private static String getResponseFromURL(URL url) throws IOException{
////        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
////        InputStream inputStream = urlConnection.getInputStream();
////        StringBuilder stringBuilder = new StringBuilder();
////        Scanner scanner = new Scanner(inputStream);
////        while(scanner.hasNext()){
////            stringBuilder.append(scanner.next());
////        }
////        urlConnection.disconnect();
////        return stringBuilder.toString();
////    }
//
//    public static void getResponseFromWikipedia(String query, Context context) throws MalformedURLException {
//        //Volley logic
//        RequestQueue queue = Volley.newRequestQueue(context);
//        StringRequest request = new StringRequest(Request.Method.GET, getURL(query).toString(), response -> {
//            try {
//                JSONParse(response);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> extract = null);
//        queue.add(request);
//    }
//
//    private static void JSONParse(String JSON) throws JSONException {
//        JSONObject page = new JSONObject(JSON.toString());
//        JSONObject query = page.getJSONObject("query");
//        JSONObject pages = query.getJSONObject("pages");
//        Iterator<String> pageIter = pages.keys();
//        String pageID = pageIter.next();
//        extract = pages.getJSONObject(pageID).getString("extract");
//    }
//
//    public static String getExtract(){    return extract;    }
}

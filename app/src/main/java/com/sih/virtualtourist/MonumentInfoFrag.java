package com.sih.virtualtourist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MonumentInfoFrag extends Fragment {

    public MonumentInfoFrag() {
        // Required empty public constructor
    }
    private static String query;

    public static void setQuery(String query1){
        query = query1;
    }

    AppCompatTextView mMonumentInfo;
    AppCompatTextView mMonumentName;
    AppCompatImageView mMonumentImage;
    FloatingActionButton mCameraButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monument_info_temp, container, false);
    }

    private void initializeViewsOnStart(){
        View view = getView();
        mMonumentInfo = view.findViewById(R.id.tv_monument_info);
        mCameraButton = view.findViewById(R.id.fab_camera);
        mMonumentImage = view.findViewById(R.id.iv_user_image);
        mMonumentName = view.findViewById(R.id.tv_monument_name);
        if(getActivity().getIntent().equals(Intent.ACTION_SEARCH)){
            //TODO: Fetching Image from the internet
            Log.i("SEARCH_STATUS_CHECK", "SUCCESS");
        }
        else {
            setImageFromURI();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        initializeViewsOnStart();
        mCameraButton.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
                File photoFile = null;
                try{
                    photoFile = MainActivity.createImageFile(getActivity());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                if(photoFile != null){
                    Uri photoURI = FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID + ".fileprovider",photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    startActivityForResult(cameraIntent, MainActivity.ID_CAMERA_ACTION);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        populateInfoWithQuery();
    }

    public void populateInfoWithQuery(){
        mMonumentName.setText(query);
        try {
            getResponseFromWikipedia(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.ID_CAMERA_ACTION && resultCode == RESULT_OK){
            setImageFromURI();
        }
    }

    private void setImageFromURI(){
        Bitmap bitmap = BitmapFactory.decodeFile(MainActivity.currentPhotoPath);
        mMonumentImage.setMaxHeight(100);
        mMonumentImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mMonumentImage.setImageBitmap(bitmap);
    }

//    class Querier extends AsyncTask<String, Void, String>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String summary = "Cannot connect...";
//            try{
//                summary = WikipediaHelper.getSummaryFromWiki(strings[0]).toString();
//            }
//            catch(IOException e){
//                e.printStackTrace();
//            }
//            finally {
//                return summary;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            mMonumentInfo.setText(s);
//        }
//    }
//    private static URL getURL(String query) throws MalformedURLException {
//        Uri request = new Uri.Builder().scheme("http").authority("en.wikipedia.org").appendPath("w").appendPath("api.php")
//            .appendQueryParameter("format" , "json").appendQueryParameter("action", "query").appendQueryParameter("prop", "extract")
//            .appendQueryParameter("exintro", "").appendQueryParameter("explaintext", "").appendQueryParameter("redirects", "")
//            .appendQueryParameter("titles", query).build();
//        return new URL(request.toString());
//    }

    private void getResponseFromWikipedia(String query) throws MalformedURLException {
        //Volley logic
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, WikipediaHelper.WIKI_STATIC.append(query).toString(), response -> {
            try {
                JSONParse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {showFetchError();});
        queue.add(request);
    }

    TextToSpeech engine;

    private void JSONParse(String JSON) throws JSONException {
        JSONObject page = new JSONObject(JSON.toString());
        JSONObject query = page.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");
        Iterator<String> pageIter = pages.keys();
        String pageID = pageIter.next();
        String info = pages.getJSONObject(pageID).getString("extract");
        mMonumentInfo.setText(info);

    }

    private void showFetchError(){
        mMonumentInfo.setText("Cannot Connect !");
    }
}

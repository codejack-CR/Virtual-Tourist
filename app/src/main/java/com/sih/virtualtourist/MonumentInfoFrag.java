package com.sih.virtualtourist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MonumentInfoFrag extends Fragment {

    public MonumentInfoFrag() {
        // Required empty public constructor
    }

    TextView mMonumentInfo;
    TextView mMonumentName;
    AppCompatImageView mMonumentImage;
    FloatingActionButton mCameraButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monument_info_temp, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        mMonumentInfo = view.findViewById(R.id.tv_monument_info);
        mCameraButton = view.findViewById(R.id.fab_camera);
        mMonumentImage = view.findViewById(R.id.iv_user_image);
        mMonumentName = view.findViewById(R.id.tv_monument_name);
        setImage();
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        //TODO: Receiving the Query Param after analysis
        //Null check can be removed after analysis is successful
        if(PostDetectActivity.detectedCity != null){
            new Querier().execute(PostDetectActivity.detectedCity.toString());
            mMonumentName.setText(PostDetectActivity.detectedCity.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.ID_CAMERA_ACTION && resultCode == RESULT_OK){
            setImage();
        }
    }

    private void setImage(){
        Bitmap bitmap = BitmapFactory.decodeFile(MainActivity.currentPhotoPath);
        mMonumentImage.setMaxHeight(100);
        mMonumentImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mMonumentImage.setImageBitmap(bitmap);
    }

    class Querier extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String summary = "Cannot connect...";
            try{
                summary = WikipediaHelper.getSummaryFromWiki(strings[0]).toString();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            finally {
                return summary;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mMonumentInfo.setText(s);
        }
    }
}

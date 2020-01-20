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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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

    TextView tv;
    WebView webView;
    AppCompatImageView mMonumentImage;
    FloatingActionButton mCameraButton;
    static final int ID_CAMERA_ACTION = 101;
    String currentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monument_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        webView = view.findViewById(R.id.wv_monument_wiki);
        mCameraButton = view.findViewById(R.id.fab_camera);
        mMonumentImage = view.findViewById(R.id.iv_user_image);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
                    File photoFile = null;
                    try{
                        photoFile = createImageFile();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    if(photoFile != null){
                        //TODO: Reading this photo in another service to get the info about it
                        Uri photoURI = FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID + ".fileprovider",photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                        startActivityForResult(cameraIntent, ID_CAMERA_ACTION);
                    }
                }
            }
        });
//        tv = view.findViewById(R.id.tv_monument_info);
        new Querier().execute("Taj Mahal");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ID_CAMERA_ACTION && resultCode == RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            mMonumentImage.setImageBitmap(bitmap);
        }
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
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
//            tv.setText(s);
            String base64 = null;
            try{
                base64 = android.util.Base64.encodeToString(s.getBytes("UTF-8"), Base64.DEFAULT);
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            webView.loadData(base64,"text/html; charset=utf-8","base64");
        }
    }
}

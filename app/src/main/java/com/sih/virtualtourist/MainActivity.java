package com.sih.virtualtourist;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.os.ConfigurationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    //TODO: Reading this photo in another service to get the info about it
    static String currentPhotoPath;
    static final int ID_CAMERA_ACTION = 101;
    ImageButton mCameraButton;
    Toolbar toolbar;
    FloatingActionButton mSOS;
    Button mPlanTripButton;

    private void initializeViewsOnCreate(){
        mCameraButton = findViewById(R.id.imgbtn_main_camera);
        mSOS = findViewById(R.id.fab_sos);
//        mSOS.setOnTouchListener(new View.OnTouchListener() {
//            long then = 0;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN){
//                    then = (Long) System.currentTimeMillis();
//                }
//                else if(event.getAction() == MotionEvent.ACTION_UP){
//                    if(((Long) System.currentTimeMillis() - then) > 1200){
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
        mSOS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, SOSActivity.class);
                startActivity(intent);
                return true;
            }
        });
        toolbar = findViewById(R.id.toolbar_main);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.ar.lens");
                startActivity(intent);
            }
        });
        mPlanTripButton = findViewById(R.id.fab_main_plan_trip);
        mPlanTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripPlannerActivity.class);
                startActivity(intent);
            }
        });
//        mCameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(cameraIntent.resolveActivity(getPackageManager()) != null){
//                    File photo = null;
//                    try{
//                        photo = createImageFile(MainActivity.this);
//                    }
//                    catch(IOException e){
//                        e.printStackTrace();
//                    }
//                    if(photo != null){
//                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".fileprovider", photo);
//                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//                        startActivityForResult(cameraIntent, ID_CAMERA_ACTION);
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ID_CAMERA_ACTION && resultCode == RESULT_OK){
            Intent intent = new Intent(MainActivity.this, PostDetectActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViewsOnCreate();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.mi_search_main).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }

    public static File createImageFile(Activity activity) throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}

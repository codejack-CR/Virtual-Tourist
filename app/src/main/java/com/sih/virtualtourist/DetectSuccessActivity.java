package com.sih.virtualtourist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetectSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect_success_result);
        TextView mMain = findViewById(R.id.tv_main);
        StringBuilder s = new StringBuilder();
        for(int i=0;i<9999;i++){
            s.append("LOL   \t");
        }
        mMain.setText(s.toString());
    }
}

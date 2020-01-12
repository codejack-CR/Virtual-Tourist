package com.sih.virtualtourist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class WikipediaHelper extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detect_result);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final ViewPager vp = findViewById(R.id.vp_result_window);
        vp.setAdapter(new ResultViewPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        TabLayout tab = findViewById(R.id.tab_results_viewpager);
        tab.setupWithViewPager(vp);
    }
}

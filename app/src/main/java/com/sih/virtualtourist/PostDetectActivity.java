package com.sih.virtualtourist;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class PostDetectActivity extends AppCompatActivity {
    public static String detectResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detect_result);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final ViewPager vp = findViewById(R.id.vp_result_window);
        ResultViewPagerAdapter adapter = new ResultViewPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vp.setAdapter(adapter);
        TabLayout tab = findViewById(R.id.tab_results_viewpager);
        tab.setupWithViewPager(vp);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
//            Bundle bundle = new Bundle();
//            bundle.putString("QUERY", SearchManager.QUERY);
//            final ViewPager pager = findViewById(R.id.vp_result_window);
//            Fragment fragment = adapter.getItem(pager.getCurrentItem());
//            fragment.setArguments(bundle);
            MonumentInfoFrag.setQuery(intent.getStringExtra(SearchManager.QUERY));
        }
        else{
            //TODO: Code for result acceptance after reverse image search
        }
    }

}

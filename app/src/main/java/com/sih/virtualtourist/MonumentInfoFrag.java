package com.sih.virtualtourist;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MonumentInfoFrag extends Fragment {

    public MonumentInfoFrag() {
        // Required empty public constructor
    }

    TextView tv;
    WebView webView;

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
//        tv = view.findViewById(R.id.tv_monument_info);
        new Querier().execute("Taj Mahal");
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

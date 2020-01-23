package com.sih.virtualtourist;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WikipediaHelper{
    private static final String WIKI_STATIC = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=";

    public static CharSequence getSummaryFromWiki(CharSequence charSequence) throws IOException{
        CharSequence response = null;
        response = getResponsefromURL(getURL(charSequence));
        return WikiJSONParser.getMonumentInfo(response);
    }

    private static URL getURL(CharSequence charSequence){
        StringBuilder url = new StringBuilder();
        url.append(WIKI_STATIC);
        StringTokenizer stringTokenizer = new StringTokenizer(charSequence.toString()," ");
        do{
            url.append(stringTokenizer.nextToken()).append("%20");
        }while(stringTokenizer.hasMoreTokens());
        URL resultURL = null;
        try{
            resultURL = new URL(url.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        finally {
            return resultURL;
        }
    }

    private static CharSequence getResponsefromURL(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNext()){
            stringBuilder.append(scanner.next());
        }
        urlConnection.disconnect();
        return stringBuilder;
    }
}

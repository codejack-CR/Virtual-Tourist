package com.sih.virtualtourist;

import android.util.JsonReader;

public class WikiJSONParser {

    private static String monumentInfo;
    public static void parseJSON(CharSequence JSON){
        //Dummy Parser
        WikiJSONParser.monumentInfo = JSON.toString();
    }
    public static CharSequence getMonumentInfoPostParse(){
        return WikiJSONParser.monumentInfo;
    }
}

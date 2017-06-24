package com.sriley.gobyshankspony.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferenceManager {


    public static void safePropertyImageUrl(Context context, String url, String zpId){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(zpId,url).commit();
    }

    public static String getPropertyImageUrl(Context context,String zpId){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        String imageUrl=preferences.getString(zpId,null);

        return imageUrl;
    }

}

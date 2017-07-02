package com.sriley.gobyshankspony.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferenceManager {


    private static final String PHONE_NUMBER = "phone_number";


    public static void savePhoneNumber(String phoneNumber,Context context){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(PHONE_NUMBER,phoneNumber).commit();
    }


    public static String getPhoneNumber(Context context){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        String phoneNumber=preferences.getString(PHONE_NUMBER,"");

        return phoneNumber;
    }

}

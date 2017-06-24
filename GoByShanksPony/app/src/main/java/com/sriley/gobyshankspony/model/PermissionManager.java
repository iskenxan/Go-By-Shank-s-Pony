package com.sriley.gobyshankspony.model;


import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class PermissionManager {
    public static final int LOCATION_PERMISSION_CODE=0;


    public static void requestLocationPermission(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest
                .permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }


}

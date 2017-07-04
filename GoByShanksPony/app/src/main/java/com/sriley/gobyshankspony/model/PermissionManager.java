package com.sriley.gobyshankspony.model;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionManager {
    public static final int LOCATION_PERMISSION_CODE=0;
    public static final int CALL_PERMISSION_REQUEST = 1;
    public static final int EXTERNAL_FILE_PERMISSION=2;


    public static void requestLocationPermission(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }

    public static void requestCallPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST);
    }


    public static boolean isPermissionGranted(int[] grantResults){
        return grantResults!=null&&grantResults[0]== PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isExternalFilePermissionGranted(Activity activity){
       boolean isGranted=(ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED);

        return isGranted;
    }


    public static void requestExternalFilePermission(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_FILE_PERMISSION);
    }


}

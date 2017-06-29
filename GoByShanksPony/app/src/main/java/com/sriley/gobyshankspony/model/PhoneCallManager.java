package com.sriley.gobyshankspony.model;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;



public class PhoneCallManager {



    public static void callNumber(String number, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            activity.startActivity(intent);
        else {
            MyPreferenceManager.savePhoneNumber(number,activity);
            PermissionManager.requestCallPermission(activity);
        }

    }




}
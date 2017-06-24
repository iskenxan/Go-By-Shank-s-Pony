package com.sriley.gobyshankspony.model;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.sriley.gobyshankspony.ContentActivity;
import com.sriley.gobyshankspony.model.interfaces.LocationPermissionListener;
import com.sriley.gobyshankspony.model.interfaces.UserLocationListener;

public class MyLocationManager implements LocationListener, LocationPermissionListener {
    private LocationManager mLocationManager;
    private ContentActivity mActivity;
    private UserLocationListener mUserLocationListener;


    public MyLocationManager( Activity activity, UserLocationListener listener) {
        mLocationManager= (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        mActivity = (ContentActivity) activity;
        mActivity.setLocationPermissionListener(this);
        mUserLocationListener=listener;
    }



    public void checkPermissionAndRequestLocation() {

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mActivity, Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionManager.requestLocationPermission(mActivity);
        } else {
            mLocationManager.requestLocationUpdates(mLocationManager.NETWORK_PROVIDER, 0,0, this);
        }
    }


    @Override
    public void onLocationPermissionResult(boolean result) {
        if (result)
            checkPermissionAndRequestLocation();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission
                .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mUserLocationListener.onUserLocationDetected(location);
            mLocationManager.removeUpdates(this);
        }


    }



    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}

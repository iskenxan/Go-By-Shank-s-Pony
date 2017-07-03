package com.sriley.gobyshankspony;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.MyPreferenceManager;
import com.sriley.gobyshankspony.model.PermissionManager;
import com.sriley.gobyshankspony.model.PhoneCallManager;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUsertypeListener;
import com.sriley.gobyshankspony.model.interfaces.GalleryImageSelectedListener;
import com.sriley.gobyshankspony.model.interfaces.LocationPermissionListener;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.utils.GalleryBrowser;
import com.sriley.gobyshankspony.model.utils.NavigationDrawerManager;

public class ContentActivity extends AppCompatActivity implements FirebaseUserCheckListener, FirebaseUsertypeListener {

    LocationPermissionListener mLocationPermissionListener;
    GalleryImageSelectedListener mGalleryImageSelectedListener;


    NavigationDrawerManager mDrawerManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mDrawerManager=new NavigationDrawerManager(this);
        mDrawerManager.setupDrawer();

        FirebaseManager.checkIfNewUser(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean option= mDrawerManager.onOptionsSelected(item);
        if(option)
            return option;

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onUserCheck(boolean isNew) {
        if(isNew)
            FragmentFactory.startUserTypeFragment(this);
        else
            getUserTypeAndStartFragment();
    }


    private void getUserTypeAndStartFragment(){
        FirebaseManager.getUsertype(this);
    }


    @Override
    public void onUsertypeExtracted(String usertype) {
      FragmentFactory.startFragmentBasedOnUserType(usertype,this);
    }



    public void setLocationPermissionListener(LocationPermissionListener listener){
        mLocationPermissionListener=listener;
    }


    public void setGalleryImageSelectedListener(GalleryImageSelectedListener listener){
        mGalleryImageSelectedListener=listener;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode== GalleryBrowser.SELECT_PICTURE_INTENT){
                Uri selectedImageUri=data.getData();
                mGalleryImageSelectedListener.onImageSelected(selectedImageUri);
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== PermissionManager.LOCATION_PERMISSION_CODE){
           handleLocationPermissionRequest(grantResults);
        }
        else if(requestCode==PermissionManager.CALL_PERMISSION_REQUEST){
            handleCallPermissionRequest(grantResults);
        }

    }



    private void handleCallPermissionRequest(int[] grantResults){
        if(PermissionManager.isPermissionGranted(grantResults)){
            String phoneNumber= MyPreferenceManager.getPhoneNumber(this);
            PhoneCallManager.callNumber(phoneNumber,this);
        }
    }



    private void handleLocationPermissionRequest(int[] grantResults){
        if(PermissionManager.isPermissionGranted(grantResults)){
            mLocationPermissionListener.onLocationPermissionResult(true);
        }
        else
            mLocationPermissionListener.onLocationPermissionResult(false);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerManager.onConfigurationChanged(newConfig);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerManager.syncToggleState();
    }


    @Override
    public void onBackPressed() {
        //Do nothing
    }


}

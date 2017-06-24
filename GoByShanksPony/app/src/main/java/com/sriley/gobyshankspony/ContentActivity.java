package com.sriley.gobyshankspony;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.User;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.PermissionManager;
import com.sriley.gobyshankspony.model.interfaces.LocationPermissionListener;

public class ContentActivity extends AppCompatActivity implements FirebaseUserCheckListener {

    LocationPermissionListener mLocationPermissionListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        User user=new User();
        user.setEmail(firebaseUser.getEmail());
        user.setFullName(firebaseUser.getDisplayName());

        FirebaseManager.checkIfNewUser(user,this);
    }


    @Override
    public void onUserCheck(boolean isNew) {
        if(isNew)
            FragmentFactory.startUserTypeFragment(this);
        else
            FragmentFactory.startSearchResultFragment(this,1);
    }



    public void setLocationPermissionListener(LocationPermissionListener listener){
        mLocationPermissionListener=listener;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== PermissionManager.LOCATION_PERMISSION_CODE){
           handleLocationPermissionRequest(grantResults);
        }

    }



    private void handleLocationPermissionRequest(int[] grantResults){
        if(grantResults!=null&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
            mLocationPermissionListener.onLocationPermissionResult(true);
        }
        else
            mLocationPermissionListener.onLocationPermissionResult(false);
    }

}

package com.sriley.gobyshankspony;

import android.app.Application;

import com.facebook.FacebookSdk;

import static com.sriley.gobyshankspony.model.FacebookSignInManager.SIGN_UP_INTENT;



public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
    }
}

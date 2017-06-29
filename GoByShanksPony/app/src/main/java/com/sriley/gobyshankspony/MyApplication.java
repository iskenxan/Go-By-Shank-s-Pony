package com.sriley.gobyshankspony;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.sriley.gobyshankspony.model.FirebaseManager;

import static com.sriley.gobyshankspony.model.FacebookSignInManager.SIGN_UP_INTENT;



public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }
}

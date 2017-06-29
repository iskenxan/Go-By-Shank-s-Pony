package com.sriley.gobyshankspony;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.GoogleSignInManager;
import com.sriley.gobyshankspony.model.interfaces.GoogleSignInIntentListener;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInIntentListener mGoogleSignInIntentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentFactory.startWelcomeFragment(this);
    }


    public void setGoogleSignInIntentListener(GoogleSignInIntentListener listener){
        mGoogleSignInIntentListener=listener;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== GoogleSignInManager.SIGN_UP_INTENT){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mGoogleSignInIntentListener.onSignInIntentReturn(result,requestCode);
        }

    }

    @Override
    public void onBackPressed() {
    }
}

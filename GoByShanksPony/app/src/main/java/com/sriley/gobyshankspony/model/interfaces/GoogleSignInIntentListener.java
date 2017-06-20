package com.sriley.gobyshankspony.model.interfaces;


import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public interface GoogleSignInIntentListener {
    void onSignInIntentReturn(GoogleSignInResult result,int requestCode);
}

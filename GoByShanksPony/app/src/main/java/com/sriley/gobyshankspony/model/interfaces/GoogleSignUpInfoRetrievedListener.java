package com.sriley.gobyshankspony.model.interfaces;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.sriley.gobyshankspony.model.User;

public interface GoogleSignUpInfoRetrievedListener {
    void onUserInfoRetrieved(GoogleSignInAccount account);
}

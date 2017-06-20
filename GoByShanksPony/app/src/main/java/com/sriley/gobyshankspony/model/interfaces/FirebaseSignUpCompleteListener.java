package com.sriley.gobyshankspony.model.interfaces;

import com.google.firebase.auth.FirebaseUser;



public interface FirebaseSignUpCompleteListener {
    void onUserSignUpComplete(FirebaseUser user);
}

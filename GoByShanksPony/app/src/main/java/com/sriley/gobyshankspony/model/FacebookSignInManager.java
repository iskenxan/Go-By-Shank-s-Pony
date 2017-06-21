package com.sriley.gobyshankspony.model;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FacebookSignInManager implements FacebookCallback<LoginResult> {

    public static final int SIGN_UP_INTENT=2;


    private LoginButton mLoginButton;
    private Fragment mFragment;
    private CallbackManager mCallbackManager;
    FirebaseAuth mAuth;

    public FacebookSignInManager(LoginButton facebookLoginButton, Fragment fragment){
        LoginManager.getInstance().logOut();
        mFragment=fragment;
        mCallbackManager=CallbackManager.Factory.create();
        mAuth=FirebaseAuth.getInstance();

        mLoginButton=facebookLoginButton;
        mLoginButton.setReadPermissions("email","public_profile");
        mLoginButton.setFragment(mFragment);
        mLoginButton.registerCallback(mCallbackManager,this);
    }


    public int getLoginRequestCode(){
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    public CallbackManager getCallbackManager(){
        return mCallbackManager;
    }


    @Override
    public void onSuccess(LoginResult loginResult) {
        handleFacebookAccessToken(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }


    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mFragment.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String name=user.getDisplayName();
                            String email=user.getEmail();
                        }
                    }

                });
    }




}

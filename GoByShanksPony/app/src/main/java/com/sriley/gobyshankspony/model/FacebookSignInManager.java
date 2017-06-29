package com.sriley.gobyshankspony.model;


import android.support.v4.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.view.dialogs.ProgressBarDialog;


public class FacebookSignInManager implements FacebookCallback<LoginResult> {

    public static final int SIGN_UP_INTENT=2;


    private LoginButton mLoginButton;
    private Fragment mFragment;
    private CallbackManager mCallbackManager;
    FirebaseAuth mAuth;
    private ProgressBarDialog mProgressBarDialog;


    public FacebookSignInManager(LoginButton facebookLoginButton, Fragment fragment, ProgressBarDialog progressBarDialog){
        LoginManager.getInstance().logOut();
        mFragment=fragment;
        mCallbackManager=CallbackManager.Factory.create();
        mAuth=FirebaseAuth.getInstance();

        mProgressBarDialog=progressBarDialog;
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
        mProgressBarDialog=new ProgressBarDialog();
        mProgressBarDialog.show(mFragment.getFragmentManager(),"progress_bar");
        FirebaseManager.signInWithFacebook(token.getToken(), (FirebaseAuthenticationListener) mFragment);
    }




}

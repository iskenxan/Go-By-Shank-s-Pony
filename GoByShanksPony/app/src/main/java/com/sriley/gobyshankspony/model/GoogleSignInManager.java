package com.sriley.gobyshankspony.model;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sriley.gobyshankspony.LoginActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.interfaces.GoogleSignInIntentListener;
import com.sriley.gobyshankspony.model.interfaces.GoogleSignUpInfoRetrievedListener;
import com.sriley.gobyshankspony.view.dialogs.ProgressBarDialog;

public class GoogleSignInManager implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleSignInIntentListener {

    public static final int SIGN_UP_INTENT =0;


    private GoogleSignInOptions mSignInOptions;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignUpInfoRetrievedListener mSignUpInfoRetrievedListener;

    private ProgressBarDialog mProgressBarDialog;

    private SignInButton mSignInButton;
    private LoginActivity mActivity;


    public GoogleSignInManager(SignInButton signInButton, LoginActivity activity, GoogleSignUpInfoRetrievedListener
            listener, ProgressBarDialog progressBarDialog){
        mSignInButton=signInButton;
        mSignUpInfoRetrievedListener=listener;
        mActivity=activity;

        mProgressBarDialog=progressBarDialog;
        mActivity.setGoogleSignInIntentListener(this);
        setupGoogleApiClient();
        customizeSignInButton();
    }


    private void customizeSignInButton(){
        TextView textView= (TextView) mSignInButton.getChildAt(0);
        textView.setTextSize(18);

        mSignInButton.setOnClickListener(this);
    }



    public void setupGoogleApiClient(){

        String clientId=mActivity.getResources().getString(R.string.default_web_client_id);
        mSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(clientId)
                .requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity).enableAutoManage( mActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mSignInOptions).build();
    }


    public  void stopGoogleClient(){
        mGoogleApiClient.stopAutoManage(mActivity);
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onClick(View view) {
        mProgressBarDialog.show(mActivity.getSupportFragmentManager(),"progress_bar_dialog");
        FirebaseManager.SignOutGmail(mGoogleApiClient);
        startSignInIntent();
    }



    private void startSignInIntent(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, SIGN_UP_INTENT);
    }


    @Override
    public void onSignInIntentReturn(GoogleSignInResult result, int requestCode) {
        if(requestCode== SIGN_UP_INTENT){
            handleSignUpIntentResult(result);
        }
    }


    private void handleSignUpIntentResult(GoogleSignInResult result){
        mProgressBarDialog.dismiss();
        if (result.isSuccess()) {
            GoogleSignInAccount userAccount = result.getSignInAccount();
            mSignUpInfoRetrievedListener.onGmailUserInfoRetrieved(userAccount);
        }
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



}

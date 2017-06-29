package com.sriley.gobyshankspony.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.sriley.gobyshankspony.ContentActivity;
import com.sriley.gobyshankspony.MainActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FacebookSignInManager;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.GoogleSignInManager;
import com.sriley.gobyshankspony.model.User;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.model.interfaces.GoogleSignUpInfoRetrievedListener;
import com.sriley.gobyshankspony.model.utils.RadioButtonSwitcher;
import com.sriley.gobyshankspony.view.dialogs.ErrorDialog;
import com.sriley.gobyshankspony.view.dialogs.ProgressBarDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomePageFragment extends Fragment implements GoogleSignUpInfoRetrievedListener, FirebaseAuthenticationListener {



    private GoogleSignInManager mGoogleSignInManager;
    private FacebookSignInManager mFacebookSignInManager;
    private ProgressBarDialog mProgressBarDialog;

    @BindView(R.id.SignUpGoogleSignButton)SignInButton mGoogleSignInButton;
    @BindView(R.id.SignUpFacebookSignUpbutton)LoginButton mFacebookSignUpButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome_page,container,false);
        ButterKnife.bind(this,view);


        mFacebookSignInManager=new FacebookSignInManager(mFacebookSignUpButton,this,mProgressBarDialog);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==mFacebookSignInManager.getLoginRequestCode()){
            mFacebookSignInManager.getCallbackManager().onActivityResult(requestCode,resultCode,data);
        }
    }


    @Override
    public void onGmailUserInfoRetrieved(GoogleSignInAccount account) {
        FirebaseManager.signInWithGmail(account.getIdToken(),this);
    }


    @Override
    public void onAuthenticationComplete(boolean result) {
        if(result)
            startContentActivity();
        else{
            ErrorDialog.displayDialog(getFragmentManager(),ErrorDialog.SIGN_IN_ERROR_MESSAGE);
        }
    }



    private void startContentActivity(){
        Intent myIntent = new Intent(getActivity(), ContentActivity.class);
        getActivity().startActivity(myIntent);
    }


    @Override
    public void onResume() {
        super.onResume();
        mProgressBarDialog=new ProgressBarDialog();
        mGoogleSignInManager=new GoogleSignInManager(mGoogleSignInButton, (MainActivity) getActivity(),this,mProgressBarDialog);

    }


    @Override
    public void onPause() {
        super.onPause();

        mGoogleSignInManager.stopGoogleClient();
    }


}

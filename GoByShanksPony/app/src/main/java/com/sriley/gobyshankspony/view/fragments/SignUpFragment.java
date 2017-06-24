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
import com.sriley.gobyshankspony.model.FacebookSignInManager;
import com.sriley.gobyshankspony.MainActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.GoogleSignInManager;
import com.sriley.gobyshankspony.model.User;
import com.sriley.gobyshankspony.model.interfaces.GoogleSignUpInfoRetrievedListener;
import com.sriley.gobyshankspony.model.utils.RadioButtonSwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignUpFragment extends Fragment implements GoogleSignUpInfoRetrievedListener {


    private RadioButtonSwitcher mSignUpButtonsSwitcher;
    private GoogleSignInManager mGoogleSignInManager;
    private FacebookSignInManager mFacebookSignInManager;


    private User mUser;

    @BindView(R.id.SignUpBrokersRadioButton)RadioButton mBrokersRadioButton;
    @BindView(R.id.SignUpLandlordRadioButton)RadioButton mLandlordRadioButton;
    @BindView(R.id.SignUpRentersRadioButton)RadioButton mRentersRadioButton;
    @BindView(R.id.SignUpGoogleSignButton)SignInButton mGoogleSignInButton;
    @BindView(R.id.SignUpFacebookSignUpbutton)LoginButton mFacebookSignUpButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_up,container,false);
        ButterKnife.bind(this,view);

        mSignUpButtonsSwitcher=new RadioButtonSwitcher(mBrokersRadioButton,mRentersRadioButton,mLandlordRadioButton);


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

    }




    private void startContentActivity(){
        Intent myIntent = new Intent(getActivity(), ContentActivity.class);
        getActivity().startActivity(myIntent);
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();

        mGoogleSignInManager.stopGoogleClient();
    }


}

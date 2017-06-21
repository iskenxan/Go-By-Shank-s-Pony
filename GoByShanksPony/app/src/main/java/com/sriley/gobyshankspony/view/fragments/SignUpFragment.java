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
import com.google.firebase.auth.FirebaseUser;
import com.sriley.gobyshankspony.ContentActivity;
import com.sriley.gobyshankspony.model.FacebookSignInManager;
import com.sriley.gobyshankspony.MainActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.GoogleSignInManager;
import com.sriley.gobyshankspony.model.User;
import com.sriley.gobyshankspony.model.interfaces.CheckIfUserExistsListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseSignUpCompleteListener;
import com.sriley.gobyshankspony.model.interfaces.GoogleSignUpInfoRetrievedListener;
import com.sriley.gobyshankspony.model.utils.RadioButtonSwitcher;
import com.sriley.gobyshankspony.view.dialogs.SignInErrorDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignUpFragment extends Fragment implements GoogleSignUpInfoRetrievedListener,CheckIfUserExistsListener {


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
        mFacebookSignInManager=new FacebookSignInManager(mFacebookSignUpButton,this);


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
        mUser=mGoogleSignInManager.createUserFromAccount(account);
        mUser.setUserType(mSignUpButtonsSwitcher.getSelectedUserType());

        FirebaseManager.SignUpUserToDataBase(mUser,this);
    }


    @Override
    public void OnCheckIfUserExistsResult(boolean exists) {
        if(!exists){
            startContentActivity();
        }
        else{
            SignInErrorDialog signInErrorDialog=SignInErrorDialog.newInstance(SignInErrorDialog.SIGN_UP_ERROR);
            signInErrorDialog.show(getFragmentManager(),"error_dialog");
        }

    }



    private void startContentActivity(){
        Intent myIntent = new Intent(getActivity(), ContentActivity.class);
        getActivity().startActivity(myIntent);
    }


    @Override
    public void onResume() {
        super.onResume();
        mGoogleSignInManager=new GoogleSignInManager(mGoogleSignInButton, (MainActivity) getActivity(),this);

    }


    @Override
    public void onPause() {
        super.onPause();

        mGoogleSignInManager.stopGoogleClient();
    }


}

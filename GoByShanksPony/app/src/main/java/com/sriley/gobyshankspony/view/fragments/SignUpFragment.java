package com.sriley.gobyshankspony.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;
import com.sriley.gobyshankspony.MainActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.GoogleSignInManager;
import com.sriley.gobyshankspony.model.interfaces.FirebaseSignUpCompleteListener;
import com.sriley.gobyshankspony.model.interfaces.GoogleSignUpInfoRetrievedListener;
import com.sriley.gobyshankspony.model.utils.RadioButtonSwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignUpFragment extends Fragment implements GoogleSignUpInfoRetrievedListener, FirebaseSignUpCompleteListener {


    private RadioButtonSwitcher mSignUpButtonsSwitcher;
    private GoogleSignInManager mGoogleSignInManager;



    @BindView(R.id.SignUpBrokersRadioButton)RadioButton mBrokersRadioButton;
    @BindView(R.id.SignUpLandlordRadioButton)RadioButton mLandlordRadioButton;
    @BindView(R.id.SignUpRentersRadioButton)RadioButton mRentersRadioButton;
    @BindView(R.id.SignUpGoogleSignButton)SignInButton mGoogleSignInButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_up,container,false);
        ButterKnife.bind(this,view);

        mSignUpButtonsSwitcher=new RadioButtonSwitcher(mBrokersRadioButton,mRentersRadioButton,mLandlordRadioButton);
        mGoogleSignInManager=new GoogleSignInManager(mGoogleSignInButton, (MainActivity) getActivity(),this);


        return view;
    }



    @Override
    public void onUserInfoRetrieved(GoogleSignInAccount account) {
        FirebaseManager.SignUpUserToDataBase(account,this);
    }


    @Override
    public void onUserSignUpComplete(FirebaseUser user) {

    }
}

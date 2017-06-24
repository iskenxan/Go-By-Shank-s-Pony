package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.User;
import com.sriley.gobyshankspony.model.utils.Formatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectUserTypeFragment extends Fragment {

    @BindView(R.id.UserTypeSpinner)Spinner mUserTypeSpinner;
    @BindView(R.id.UserTypeNextButton)Button mNextButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_select_user_type,container,false);
        ButterKnife.bind(this,view);
        setupUserTypeSpinner();

        return view;
    }


    private void setupUserTypeSpinner(){
        String[] userTypes=new String[]{"Renter","Landlord or Manager","Agent or Broker"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_list_item,
                userTypes);
        mUserTypeSpinner.setAdapter(adapter);
    }


    @OnClick(R.id.UserTypeNextButton)
    public void onNextButtonClicked(){
        User user=getUserDetails();
        FirebaseManager.saveUserDetails(user);
        FragmentFactory.startSearchResultFragment((AppCompatActivity) getActivity(),1);
    }


    private  String getUserType(){
        String spinnerItem= (String) mUserTypeSpinner.getSelectedItem();

        return Formatter.formatUsertypeForDatabase(spinnerItem);
    }


    private  User getUserDetails(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        User user=new User();
        user.setFullName(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());
        user.setUserType(getUserType());

        return user;
    }



}

package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sriley.gobyshankspony.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectUserTypeFragment extends Fragment {

    @BindView(R.id.UserTypeSpinner)Spinner mUserTypeSpinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_select_user_type,container,false);
        ButterKnife.bind(this,view);
        setupUserTypeSpinner();

        return view;
    }


    private void setupUserTypeSpinner(){
        String[] userTypes=new String[]{"Renter","Landlord or Manager","Agent or Brocker"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,
                userTypes);
        mUserTypeSpinner.setAdapter(adapter);
    }
    


}

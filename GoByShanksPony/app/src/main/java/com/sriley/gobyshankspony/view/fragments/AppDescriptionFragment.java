package com.sriley.gobyshankspony.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sriley.gobyshankspony.ContentActivity;
import com.sriley.gobyshankspony.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppDescriptionFragment extends Fragment {


    @BindView(R.id.AppDescriptionFragmentButton)Button mButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_app_description,container,false);
        ButterKnife.bind(this,view);

        return view;
    }


    @OnClick(R.id.AppDescriptionFragmentButton)
    public void onButtonClick(){
        startContentActivity();
    }


    private void startContentActivity(){
        Intent myIntent = new Intent(getActivity(), ContentActivity.class);
        getActivity().startActivity(myIntent);
    }
}

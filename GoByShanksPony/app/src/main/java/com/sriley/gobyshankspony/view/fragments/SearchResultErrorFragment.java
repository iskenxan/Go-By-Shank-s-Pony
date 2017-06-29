package com.sriley.gobyshankspony.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchResultErrorFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_error,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.errorReloadButton)
    public void onReloadButtonClicked(){
        FragmentFactory.startSearchResultFragment((AppCompatActivity) getActivity(),1);
    }
}

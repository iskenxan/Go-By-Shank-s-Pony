package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.view.adapters.ApartmentTypeViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectApartmentTypeFragment extends Fragment {


    @BindView(R.id.ApartmentTypeViewPager)ViewPager mViewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_select_apartment_type,container,false);
        ButterKnife.bind(this,view);

        setupViewpager();


        return view;
    }


    private void setupViewpager(){
        ApartmentTypeViewPagerAdapter adapter=new ApartmentTypeViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    @OnClick(R.id.searchApartmentButton)
    public void onSearchButtonClicked(){
        int selectedApartmentType=mViewPager.getCurrentItem()+1;
        FragmentFactory.startSearchResultFragment((AppCompatActivity) getActivity(),selectedApartmentType);
    }
}

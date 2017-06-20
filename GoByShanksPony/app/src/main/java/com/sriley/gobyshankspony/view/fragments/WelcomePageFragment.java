package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.view.adapters.WelcomePageViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomePageFragment extends Fragment {


    @BindView(R.id.WelcomePageLoginViewPager)ViewPager mViewPager;
    @BindView(R.id.WelcomePageTabLayout)TabLayout mTabLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome_page,container,false);
        ButterKnife.bind(this,view);

        WelcomePageViewPagerAdapter viewPagerAdapter=new WelcomePageViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        View tabView=LayoutInflater.from(getContext()).inflate(R.layout.custom_viewpager_tab,null);
        mTabLayout.getTabAt(0).setCustomView(tabView);
        mTabLayout.getTabAt(1).setCustomView(tabView);



        return view;
    }


}

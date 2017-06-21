package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        setupTabLayout();


        return view;
    }



    private void setupTabLayout(){
        mTabLayout.setupWithViewPager(mViewPager);

        View signUpTabView=getTabView("SIGN UP");
        mTabLayout.getTabAt(0).setCustomView(signUpTabView);

        View loginTabView=getTabView("LOGIN");
        mTabLayout.getTabAt(1).setCustomView(loginTabView);
    }



    private View getTabView(String tabTitle){
        View tabView=LayoutInflater.from(getContext()).inflate(R.layout.custom_viewpager_tab,null);
        TextView textView= (TextView) tabView.findViewById(R.id.tabTextView);
        textView.setText(tabTitle);

        return tabView;
    }



}

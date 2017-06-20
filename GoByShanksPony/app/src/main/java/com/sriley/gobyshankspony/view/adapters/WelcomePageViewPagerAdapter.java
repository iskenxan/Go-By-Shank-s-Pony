package com.sriley.gobyshankspony.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sriley.gobyshankspony.view.fragments.LoginFragment;
import com.sriley.gobyshankspony.view.fragments.SignUpFragment;


public class WelcomePageViewPagerAdapter extends FragmentPagerAdapter {


    public WelcomePageViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new SignUpFragment();
        else if(position==1)
            return new LoginFragment();
        else return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

package com.sriley.gobyshankspony.model;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.view.fragments.SelectUserTypeFragment;
import com.sriley.gobyshankspony.view.fragments.WelcomePageFragment;

public class FragmentFactory {


    public static void startWelcomeFragment(AppCompatActivity activity){
        WelcomePageFragment welcomePageFragment=new WelcomePageFragment();
        beginWelcomeActivityFragmentTransaction(activity,welcomePageFragment);
    }


    public static void startUserTypeFragment(AppCompatActivity activity){
        SelectUserTypeFragment selectUserTypeFragment=new SelectUserTypeFragment();
        beginContentActivityFragmentTransaction(activity,selectUserTypeFragment);
    }


    private static void beginContentActivityFragmentTransaction(AppCompatActivity activity,Fragment fragment){
        FragmentManager fragmentManager=activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainContentPlaceHolder,fragment).commit();
    }


    private static void beginWelcomeActivityFragmentTransaction(AppCompatActivity activity, Fragment fragment){
        FragmentManager fragmentManager=activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainWelcomePlaceHolder,fragment).commit();
    }



}

package com.sriley.gobyshankspony.model;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.view.fragments.WelcomePageFragment;

public class FragmentFactory {


    public static void startWelcomeFragment(AppCompatActivity activity){

        WelcomePageFragment welcomePageFragment=new WelcomePageFragment();
       beginFragmentTransaction(activity,welcomePageFragment);
    }


    private static void beginFragmentTransaction(AppCompatActivity activity, Fragment fragment){
        FragmentManager fragmentManager=activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainPlaceHolder,fragment).commit();
    }


}

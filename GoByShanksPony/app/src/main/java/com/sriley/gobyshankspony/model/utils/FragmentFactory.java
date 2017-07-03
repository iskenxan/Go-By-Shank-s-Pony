package com.sriley.gobyshankspony.model.utils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.view.fragments.AddNewPropertyFragment;
import com.sriley.gobyshankspony.view.fragments.FavoritesFragment;
import com.sriley.gobyshankspony.view.fragments.ManagerPropertyListFragment;
import com.sriley.gobyshankspony.view.fragments.SearchResultErrorFragment;
import com.sriley.gobyshankspony.view.fragments.SearchResultFragment;
import com.sriley.gobyshankspony.view.fragments.SelectApartmentTypeFragment;
import com.sriley.gobyshankspony.view.fragments.SelectUserTypeFragment;
import com.sriley.gobyshankspony.view.fragments.WelcomePageFragment;

public class FragmentFactory {


    public static void startFragmentBasedOnUserType(String usertype,AppCompatActivity activity){
        if(usertype.equals(Formatter.USERTYPE_RENTER)||usertype.equals(Formatter.USERTYPE_AGENT))
            FragmentFactory.startSearchResultFragment(activity,1);
        else
            FragmentFactory.startManagerPropertyListFragment(activity);
    }


    public static void startEditPropertyFragment(AppCompatActivity activity, ListingProperty property){
        AddNewPropertyFragment fragment=new AddNewPropertyFragment();

        String propertyStr=GSONFactory.convertListingPropertyToString(property);
        Bundle args=new Bundle();
        args.putString(AddNewPropertyFragment.PROPERTY_ARGS,propertyStr);
        fragment.setArguments(args);

        beginContentActivityFragmentTransaction(activity,fragment);
    }


    public static void startAddPropertyFragment(AppCompatActivity activity){
        AddNewPropertyFragment fragment=new AddNewPropertyFragment();
        beginContentActivityFragmentTransaction(activity,fragment);
    }


    public static void startWelcomeFragment(AppCompatActivity activity){
        WelcomePageFragment welcomePageFragment=new WelcomePageFragment();
        beginWelcomeActivityFragmentTransaction(activity,welcomePageFragment);
    }


    public static void startUserTypeFragment(AppCompatActivity activity){
        SelectUserTypeFragment selectUserTypeFragment=new SelectUserTypeFragment();
        beginContentActivityFragmentTransaction(activity,selectUserTypeFragment);
    }

    public static void startSelectApartmentTypeFragment(AppCompatActivity activity){
        SelectApartmentTypeFragment fragment=new SelectApartmentTypeFragment();
        beginContentActivityFragmentTransaction(activity,fragment);
    }

    public static void startSearchResultFragment(AppCompatActivity activity,int apartmentType){
        SearchResultFragment searchResultFragment=new SearchResultFragment();

        Bundle args=new Bundle();
        args.putInt(SearchResultFragment.APARTMENT_TYPE_ARGS,apartmentType);
        searchResultFragment.setArguments(args);

        beginContentActivityFragmentTransaction(activity,searchResultFragment);
    }

    public static void startSearchErrorFragment(AppCompatActivity activity){
        SearchResultErrorFragment fragment=new SearchResultErrorFragment();
        beginContentActivityFragmentTransaction(activity,fragment);
    }

    public static void startFavoritesFragment(AppCompatActivity activity){
        FavoritesFragment favoritesFragment=new FavoritesFragment();
        beginContentActivityFragmentTransaction(activity,favoritesFragment);
    }

    public static void startManagerPropertyListFragment(AppCompatActivity activity){
        ManagerPropertyListFragment fragment=new ManagerPropertyListFragment();
        beginContentActivityFragmentTransaction(activity,fragment);
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

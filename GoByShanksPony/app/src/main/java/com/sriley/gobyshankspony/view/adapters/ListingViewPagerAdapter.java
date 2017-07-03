package com.sriley.gobyshankspony.view.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.utils.GSONFactory;
import com.sriley.gobyshankspony.view.fragments.SingleFavoriteFragment;
import com.sriley.gobyshankspony.view.fragments.SingleSearchResultFragment;

import java.util.ArrayList;


public class ListingViewPagerAdapter extends FragmentPagerAdapter {

    public static final String FRAGMENT_TYPE_SEARCH_RESULT="sarch_result";
    public static final String FRAGMENT_TYPE_FAVORITE="favorite";

    private ArrayList<ListingProperty> mProperties;
    private String mApartmentType;

    public ListingViewPagerAdapter(FragmentManager fragmentManager, ArrayList<ListingProperty> properties, String apartmentType){
        super(fragmentManager);
        mProperties =properties;
        mApartmentType=apartmentType;
    }


    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        if(mApartmentType.equals(FRAGMENT_TYPE_SEARCH_RESULT))
            fragment=new SingleSearchResultFragment();
        else
            fragment=new SingleFavoriteFragment();

            setFragmentArgs(position,fragment);


        return fragment;
    }


    private void setFragmentArgs(int position,Fragment fragment){
        Bundle args=new Bundle();
        String venueStr= GSONFactory.convertListingPropertyToString(mProperties.get(position));
        args.putString(SingleSearchResultFragment.PROPERTY_ARGS_KEY,venueStr);

        fragment.setArguments(args);
    }



    @Override
    public int getCount() {
        return mProperties.size();
    }
}

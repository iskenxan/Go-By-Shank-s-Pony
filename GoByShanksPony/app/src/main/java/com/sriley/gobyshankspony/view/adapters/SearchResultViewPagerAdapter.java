package com.sriley.gobyshankspony.view.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.utils.GSONFactory;
import com.sriley.gobyshankspony.view.fragments.SingleSearchResultFragment;

import java.util.ArrayList;


public class SearchResultViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ListingProperty> mProperties;

    public SearchResultViewPagerAdapter(FragmentManager fragmentManager, ArrayList<ListingProperty> properties){
        super(fragmentManager);
        mProperties =properties;
    }


    @Override
    public Fragment getItem(int position) {
        SingleSearchResultFragment fragment=new SingleSearchResultFragment();
        setFragmentArgs(position,fragment);


        return fragment;
    }


    private void setFragmentArgs(int position,Fragment fragment){
        Bundle args=new Bundle();
        String venueStr= GSONFactory.convertVenueToString(mProperties.get(position));
        args.putString(SingleSearchResultFragment.PROPERTY_ARGS_KEY,venueStr);

        fragment.setArguments(args);
    }



    @Override
    public int getCount() {
        return mProperties.size();
    }
}

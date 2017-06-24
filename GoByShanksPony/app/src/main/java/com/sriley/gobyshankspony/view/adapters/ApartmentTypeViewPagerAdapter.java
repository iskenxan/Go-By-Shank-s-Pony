package com.sriley.gobyshankspony.view.adapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.view.fragments.ApartmentTypeFragment;

public class ApartmentTypeViewPagerAdapter extends FragmentPagerAdapter {


    public ApartmentTypeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        ApartmentTypeFragment fragment = new ApartmentTypeFragment();
        setFragmentArgs(fragment,position);

        return fragment;
    }



    private void setFragmentArgs(ApartmentTypeFragment fragment,int position){
        int numberOfBedroomsImageId=getNumberOfBedroomsImageId(position);

        Bundle args=new Bundle();
        args.putInt(ApartmentTypeFragment.BEDROOM_NUM_ARGS,numberOfBedroomsImageId);

        fragment.setArguments(args);
    }


    private int getNumberOfBedroomsImageId(int position){
        switch (position){
            case 0:
              return R.drawable.one_bed;
            case 1:
                return R.drawable.two_bed;
            case 2:
                return R.drawable.three_bed;
            default:
                return 0;
        }

    }


    @Override
    public int getCount() {
        return 3;
    }


}

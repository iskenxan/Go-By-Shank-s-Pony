package com.sriley.gobyshankspony.view.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.MyLocationManager;
import com.sriley.gobyshankspony.model.Place;
import com.sriley.gobyshankspony.model.ScrapeManager;
import com.sriley.gobyshankspony.model.ZillowManager;
import com.sriley.gobyshankspony.model.interfaces.UserLocationListener;
import com.sriley.gobyshankspony.model.interfaces.ScrapeRequestListener;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.model.utils.GeoCalculator;
import com.sriley.gobyshankspony.view.adapters.SearchResultViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchResultFragment extends Fragment implements UserLocationListener, ScrapeRequestListener {

    public static final String APARTMENT_TYPE_ARGS="apartment_type";

    @BindView(R.id.searchResultViewPager)ViewPager mViewPager;
    @BindView(R.id.searchResultLoadingImageContainer)LinearLayout mLoadingImageContainer;


    MyLocationManager mMyLocationManager;
    ArrayList<ListingProperty> mZillowProperties=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_result,container,false);
        ButterKnife.bind(this,view);

        mMyLocationManager=new MyLocationManager(getActivity(),this);
        mMyLocationManager.checkPermissionAndRequestLocation();

        return view;
    }


    @Override
    public void onUserLocationDetected(Location location) {

        Place place=Formatter.getPlaceFromLocation(getContext(),location);
        ScrapeManager.getZillowRentals(getContext(),place,this);
    }



    @Override
    public void onScrapeRequestComplete(final ArrayList<ListingProperty> resultProperties) {
        handleRequestResult(resultProperties);
    }


    private void handleRequestResult(final  ArrayList<ListingProperty> resultProperties){
        if(resultProperties!=null){
            mZillowProperties.addAll(resultProperties);
            getActivity().runOnUiThread(setupViewPagerRunnable);
        }
    }



    Runnable setupViewPagerRunnable=new Runnable() {
        @Override
        public void run() {
            mLoadingImageContainer.setVisibility(View.INVISIBLE);
            setupViewPager();
        }
    };



    private void setupViewPager(){
        Formatter.removePropertyDuplicates(mZillowProperties);
        if(mViewPager.getAdapter()!=null)
            mViewPager.getAdapter().notifyDataSetChanged();
        else{
            SearchResultViewPagerAdapter adapter=new SearchResultViewPagerAdapter(getChildFragmentManager(),mZillowProperties);
            mViewPager.setAdapter(adapter);
        }
    }
}

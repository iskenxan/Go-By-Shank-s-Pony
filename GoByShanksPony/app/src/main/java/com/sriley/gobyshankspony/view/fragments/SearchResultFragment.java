package com.sriley.gobyshankspony.view.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sriley.gobyshankspony.LoginActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.MyLocationManager;
import com.sriley.gobyshankspony.model.Place;
import com.sriley.gobyshankspony.model.ScrapeManager;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractPropertiesListener;
import com.sriley.gobyshankspony.model.interfaces.ListingScrapeRequestListener;
import com.sriley.gobyshankspony.model.interfaces.TimerListener;
import com.sriley.gobyshankspony.model.interfaces.UserLocationListener;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.view.adapters.ListingViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchResultFragment extends Fragment implements UserLocationListener, ListingScrapeRequestListener,TimerListener, FirebaseExtractPropertiesListener {

    public static final String APARTMENT_TYPE_ARGS="apartment_type";

    @BindView(R.id.searchResultViewPager)ViewPager mViewPager;
    @BindView(R.id.searchResultLoadingImageContainer)LinearLayout mLoadingImageContainer;
    boolean timeOut=true;



    MyLocationManager mMyLocationManager;
    ArrayList<ListingProperty> mPropertyList =new ArrayList<>();
    WebView mWebView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_result,container,false);
        ButterKnife.bind(this,view);

        mMyLocationManager=new MyLocationManager(getActivity(),this);
        checkIfLocationServiceOn();
        setActionBarTitle("GO BY SHANK'S PONY");

        return view;
    }



    private void checkIfLocationServiceOn(){
       boolean isServiceOn= mMyLocationManager.checkIfLocationServicesAreEnabled();
        if(isServiceOn)
            mMyLocationManager.checkPermissionAndRequestLocation();
        else{
            Toast.makeText(getContext(),"Turn on location services before proceeding",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getActivity(),LoginActivity.class);
            getActivity().startActivity(intent);
        }
    }




    @Override
    public void onUserLocationDetected(Location location) {
        Place place=Formatter.getPlaceFromLocation(getContext(),location);
        mWebView=new WebView(getContext());
        ScrapeManager.getRentalsList(mWebView,place,this);
        FirebaseManager.extractPropertiesFromDatabase(place.getZip(),this);
    }


    @Override
    public void onTimerComplete() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.stopLoading();
                FragmentFactory.startSearchErrorFragment((AppCompatActivity) getActivity());
            }
        });
    }


    @Override
    public void onScrapeRequestComplete(final ArrayList<ListingProperty> resultProperties) {
        timeOut=false;
        handleRequestResult(resultProperties);
    }


    @Override
    public void onPropertiesExtracted(ArrayList<ListingProperty> properties) {
        mPropertyList.addAll(properties);
    }


    private void handleRequestResult(final  ArrayList<ListingProperty> resultProperties){
        if(resultProperties!=null){
            mPropertyList.addAll(resultProperties);
            getActivity().runOnUiThread(setupViewPagerRunnable);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mMyLocationManager.removeLocationUpdates();
    }



    Runnable setupViewPagerRunnable=new Runnable() {
        @Override
        public void run() {
            setActionBarTitle("HELLO!");
            mLoadingImageContainer.setVisibility(View.INVISIBLE);
            setupViewPager();
        }
    };


    private void setupViewPager(){
        Formatter.removePropertyDuplicates(mPropertyList);
        if(mViewPager.getAdapter()!=null)
            mViewPager.getAdapter().notifyDataSetChanged();
        else{
            ListingViewPagerAdapter adapter=new ListingViewPagerAdapter(getChildFragmentManager(), mPropertyList, ListingViewPagerAdapter.FRAGMENT_TYPE_SEARCH_RESULT);
            mViewPager.setAdapter(adapter);
        }
    }



    private void setActionBarTitle(String title){
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(title);
    }



}

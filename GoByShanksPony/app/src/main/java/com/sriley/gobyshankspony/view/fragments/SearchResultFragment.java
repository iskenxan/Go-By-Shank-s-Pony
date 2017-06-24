package com.sriley.gobyshankspony.view.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FoursquareManager;
import com.sriley.gobyshankspony.model.MyLocationManager;
import com.sriley.gobyshankspony.model.Place;
import com.sriley.gobyshankspony.model.Venue;
import com.sriley.gobyshankspony.model.ZillowManager;
import com.sriley.gobyshankspony.model.ZillowProperty;
import com.sriley.gobyshankspony.model.interfaces.FoursquareRequestListener;
import com.sriley.gobyshankspony.model.interfaces.UserLocationListener;
import com.sriley.gobyshankspony.model.interfaces.ZillowRequestListener;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.view.adapters.SearchResultViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchResultFragment extends Fragment implements UserLocationListener, FoursquareRequestListener, ZillowRequestListener {

    public static final String APARTMENT_TYPE_ARGS="apartment_type";

    @BindView(R.id.apartmentSearchResultViewPager)ViewPager mViewPager;


    MyLocationManager mMyLocationManager;
    ArrayList<ZillowProperty> mZillowProperties=new ArrayList<>();



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
      requestListOfPropertiesForUsersCurrentLocation(location);
        FoursquareManager.sendAsyncSearchVenueRequest(getContext(),location,this);
    }




    private void requestListOfPropertiesForUsersCurrentLocation(Location location){
        try {
            Geocoder geoCoder = new Geocoder(getContext());
            List<Address> matches = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            Address address=matches.get(0);
            final Venue venue=new Venue();
            venue.setAddress(address.getAddressLine(0));
            venue.setState(address.getAdminArea());
            venue.setCity(address.getLocality());
            final Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    ZillowManager.getNearbyPropertiesForSingleVenue(getContext(), venue,SearchResultFragment.this);
                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onFoursquareReturn(final ArrayList<Venue> venues) {
                ArrayList<Venue> fullAddressVenues= Formatter.removePartialAddressVenues(venues);
                requestZillowDetailsForPlaceList(fullAddressVenues);
    }



    private void requestZillowDetailsForPlaceList(final ArrayList placeList){
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                ZillowManager.getNearbyPropertiesForVenueList(getContext(),placeList,SearchResultFragment.this);
            }
        });
        thread.start();
    }



    private void setupViewPager(ArrayList<ZillowProperty> newPropertyList){
        if(newPropertyList!=null){
            mZillowProperties.addAll(newPropertyList);
        }

        Formatter.removeZillowPropertyDuplicates(mZillowProperties);
        if(mViewPager.getAdapter()!=null)
            mViewPager.getAdapter().notifyDataSetChanged();
        else{
            SearchResultViewPagerAdapter adapter=new SearchResultViewPagerAdapter(getChildFragmentManager(),mZillowProperties);
            mViewPager.setAdapter(adapter);
        }

    }


    @Override
    public void onZillowRequestCompleted(final ArrayList<ZillowProperty> resultProperties) {
        getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupViewPager(resultProperties);
                }
            });
    }
}

package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.MyPreferenceManager;
import com.sriley.gobyshankspony.model.ZillowManager;
import com.sriley.gobyshankspony.model.ZillowProperty;
import com.sriley.gobyshankspony.model.interfaces.ZillowPhotoUrlRequestListener;
import com.sriley.gobyshankspony.model.utils.GSONFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleSearchResultFragment extends Fragment implements ZillowPhotoUrlRequestListener {

    public static final String PROPERTY_ARGS_KEY ="property";


    @BindView(R.id.SingleSearchResultImageView)ImageView mImageView;
    @BindView(R.id.SingleSearchResultAddressTextView)TextView mAddressTextView;
    @BindView(R.id.SingleSearchResultBedroomsTextView)TextView mBedroomsTextView;
    @BindView(R.id.SingleSearchResultBathroomsTextView)TextView mBathroomsTextView;
    @BindView(R.id.SingleSearchResultRentTextView)TextView mRentTextView;


    ZillowProperty mZillowProperty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_single_search_result,container,false);
        ButterKnife.bind(this,view);

        extractArgs();
        populateViews();


        return view;
    }


    private void extractArgs(){
        String propertyStr=getArguments().getString(PROPERTY_ARGS_KEY,"");
        mZillowProperty= GSONFactory.convertStringToVenue(propertyStr);

    }


    private void populateViews(){
        mAddressTextView.setText(mZillowProperty.getAddress()+", "+mZillowProperty.getCity()+", "+mZillowProperty.getState());
        if(mZillowProperty.getBedrooms()!=null)
            mBedroomsTextView.setText("Bedrooms: "+mZillowProperty.getBedrooms());
        if(mZillowProperty.getBathrooms()!=null)
            mBathroomsTextView.setText("Bathrooms: "+mZillowProperty.getBathrooms());
        if(mZillowProperty.getRent()!=null);
            mRentTextView.setText("Rent: "+mZillowProperty.getRent());
        mAddressTextView.setText(mZillowProperty.getAddress()+", "+mZillowProperty.getCity()+", "+mZillowProperty.getState());

        String imageUrl= MyPreferenceManager.getPropertyImageUrl(getContext(),mZillowProperty.getId());
        if(imageUrl==null)
            ZillowManager.asyncRequestPropertyPhotoUrl(getContext(),mZillowProperty.getId(),this);
        else
            loadImage(imageUrl);
    }


    @Override
    public void onZillowUrlRequestResult(final String url) {
        MyPreferenceManager.safePropertyImageUrl(getContext(),url,mZillowProperty.getId());
        loadImage(url);
    }

    private void loadImage(final String imageUrl){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!imageUrl.equals("empty_url"))
                    Glide.with(getContext()).load(imageUrl).into(mImageView);
                else
                    Glide.with(getContext()).load(R.drawable.image_not_available).into(mImageView);
            }
        });
    }
}

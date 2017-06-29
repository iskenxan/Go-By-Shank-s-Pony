package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.utils.GSONFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleSearchResultFragment extends Fragment{

    public static final String PROPERTY_ARGS_KEY = "property";




    @BindView(R.id.SingleSearchResultImageView) ImageView mImageView;
    @BindView(R.id.SingleSearchResultAddressTextView) TextView mAddressTextView;
    @BindView(R.id.SingleSearchResultBedroomsTextView) TextView mBedroomsTextView;
    @BindView(R.id.SingleSearchResultBathroomsTextView) TextView mBathroomsTextView;
    @BindView(R.id.SingleSearchResultRentTextView) TextView mRentTextView;
    @BindView(R.id.SingleSearchResultNameTextView)TextView mNameTextView;
    @BindView(R.id.callFabButton)FloatingActionButton mCallFab;
    @BindView(R.id.favoritesFabButton)FloatingActionButton mFavoritesFab;
    @BindView(R.id.refreshFabButton)FloatingActionButton mRefreshFab;

    ListingProperty mListingProperty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_search_result, container, false);
        ButterKnife.bind(this, view);

        extractArgs();
        populateViews();


        return view;
    }


    private void extractArgs() {
        String propertyStr = getArguments().getString(PROPERTY_ARGS_KEY, "");
        mListingProperty = GSONFactory.convertStringToVenue(propertyStr);

    }


    private void populateViews() {
        mNameTextView.setText(mListingProperty.getName());
        mAddressTextView.setText(mListingProperty.getAddress() + ", " + mListingProperty.getCity() + ", " + mListingProperty.getState());
        mBedroomsTextView.setText(mListingProperty.getBedrooms());
        mBathroomsTextView.setText(mListingProperty.getBathrooms());
        mRentTextView.setText(mListingProperty.getRent());
        mAddressTextView.setText(mListingProperty.getAddress() + ", " + mListingProperty.getCity() + ", " + mListingProperty.getState());
        loadImage(mListingProperty.getImageUrl());
    }


    private void loadImage(String imageUrl){
        if(imageUrl.contains(".svg"))
            Picasso.with(getContext()).load(R.drawable.image_not_available).resize(400, 300).centerCrop().into(mImageView);
        else
            Picasso.with(getContext()).load(imageUrl).resize(400, 300).centerCrop().into(mImageView);
    }


    @OnClick(R.id.callFabButton)
    public void onCallFabClicked(){
        Toast.makeText(getContext(),"Call",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.favoritesFabButton)
    public void onFavoritesFabClicked(){
        Toast.makeText(getContext(),"Favorites",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.refreshFabButton)
    public void onRefreshFabClicked(){
        FragmentFactory.startSearchResultFragment((AppCompatActivity) getActivity(),1);
    }

}

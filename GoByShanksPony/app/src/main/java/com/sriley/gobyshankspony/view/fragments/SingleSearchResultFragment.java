package com.sriley.gobyshankspony.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseDatabaseManager;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.PhoneCallManager;
import com.sriley.gobyshankspony.model.ScrapeManager;
import com.sriley.gobyshankspony.model.interfaces.FirebaseFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.PhoneScrapeRequestListener;
import com.sriley.gobyshankspony.model.interfaces.PropertyImageListRequestListener;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.utils.GSONFactory;
import com.sriley.gobyshankspony.view.dialogs.ErrorDialog;
import com.sriley.gobyshankspony.view.dialogs.GalleryDialog;
import com.sriley.gobyshankspony.view.dialogs.ProgressBarDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleSearchResultFragment extends Fragment implements FirebaseFavoritesListener, PhoneScrapeRequestListener, PropertyImageListRequestListener {

    public static final String PROPERTY_ARGS_KEY = "property";


    @BindView(R.id.SingleSearchResultImageView)
    ImageView mImageView;
    @BindView(R.id.SingleSearchResultAddressTextView)
    TextView mAddressTextView;
    @BindView(R.id.SingleSearchResultBedroomsTextView)
    TextView mBedroomsTextView;
    @BindView(R.id.SingleSearchResultBathroomsTextView)
    TextView mBathroomsTextView;
    @BindView(R.id.SingleSearchResultRentTextView)
    TextView mRentTextView;
    @BindView(R.id.SingleSearchResultNameTextView)
    TextView mNameTextView;
    @BindView(R.id.callFabButton)
    FloatingActionButton mCallFab;
    @BindView(R.id.favoritesFabButton)
    FloatingActionButton mFavoritesFab;
    @BindView(R.id.fabMenu)FloatingActionsMenu mFabMenu;
    @BindView(R.id.SingleSearchResultGalleryButton)Button mGalleryButton;


    ListingProperty mListingProperty;
    ProgressBarDialog mProgressBarDialog;
    WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_search_result, container, false);
        ButterKnife.bind(this, view);

        mWebView=new WebView(getContext());
        extractArgs();
        populateViews();


        return view;
    }


    private void extractArgs() {
        String propertyStr = getArguments().getString(PROPERTY_ARGS_KEY, "");
        mListingProperty = GSONFactory.convertStringToListingProperty(propertyStr);
    }


    private void populateViews() {
        if(!mListingProperty.getManagerUsername().equals(""))
            mGalleryButton.setVisibility(View.INVISIBLE);
        mNameTextView.setText(mListingProperty.getName());
        mAddressTextView.setText(mListingProperty.getAddress() + ", " + mListingProperty.getCity() + ", " + mListingProperty.getState());
        mBedroomsTextView.setText(mListingProperty.getBedrooms());
        mBathroomsTextView.setText(mListingProperty.getBathrooms());
        mRentTextView.setText(mListingProperty.getPrice());
        mAddressTextView.setText(mListingProperty.getAddress() + ", " + mListingProperty.getCity() + ", " + mListingProperty.getState());
        loadImage(mListingProperty.getImageUrl());
    }


    private void loadImage(String imageUrl) {
        if (imageUrl.contains(".svg"))
            Picasso.with(getContext()).load(R.drawable.image_not_available).resize(400, 300).centerCrop().into(mImageView);
        else
            Picasso.with(getContext()).load(imageUrl).resize(400, 300).centerCrop().into(mImageView);
    }


    @OnClick(R.id.callFabButton)
    public void onCallFabClicked() {
        mFabMenu.collapse();
        showProgressBar();
        checkIfPropertyManagerIsUser();
    }


    private void checkIfPropertyManagerIsUser(){
        String managerUsername=mListingProperty.getManagerUsername();
        if(managerUsername==null||managerUsername.equals(""))
            ScrapeManager.getPropertyPhone(mWebView,mListingProperty.getDetailsUrl(),this);
        else{
            mProgressBarDialog.dismiss();
            PhoneCallManager.callNumber(mListingProperty.getPhoneNumber(),getActivity());
        }
    }



    private void showProgressBar(){
        mProgressBarDialog=new ProgressBarDialog();
        mProgressBarDialog.show(getFragmentManager(),"progress_bar");
    }



    @Override
    public void onPhoneRetrieved(String phone) {
        mProgressBarDialog.dismiss();
        if(phone!=null)
            PhoneCallManager.callNumber(phone,getActivity());
        else
            getBrokerNameAndDisplayDialog();
    }



    private void getBrokerNameAndDisplayDialog(){
        String broker=mListingProperty.getBroker();
        if(broker==null)
            broker="the broker";
        ErrorDialog dialog= ErrorDialog.newInstance(ErrorDialog.BROKER_ERROR_MESSAGE+broker);
        dialog.show(getFragmentManager(),"no_phone_dialog");
    }



    @OnClick(R.id.favoritesFabButton)
    public void onFavoritesFabClicked() {
        mFabMenu.collapse();
        FirebaseDatabaseManager.checkIfInFavoritesAndAdd(mListingProperty,this);
    }



    @Override
    public void onPropertyAddedToFavorites(boolean success) {
        if (success)
            Toast.makeText(getContext(), "Property was added to your favorites", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getContext(), "Connection error. Check your internet connection", Toast.LENGTH_LONG).show();
    }



    @OnClick(R.id.SingleSearchResultGalleryButton)
    public void onGalleryButtonClicked(){
        showProgressBar();
        ScrapeManager.getPropertyImageList(mWebView,mListingProperty.getDetailsUrl(),this);
    }


    @OnClick(R.id.SingleSearchResultRefreshButton)
    public void onRefreshButtonClicked() {
        FragmentFactory.startSearchResultFragment((AppCompatActivity) getActivity(),mListingProperty.getPropertyType());
    }


    @Override
    public void onImageUrlsExtracted(ArrayList<String> imageUrls) {
        mProgressBarDialog.dismiss();
        GalleryDialog.displayDialog(getFragmentManager(),imageUrls);
    }


    @Override
    public void onStop() {
        super.onStop();
        try{
            if(mProgressBarDialog!=null)
                mProgressBarDialog.dismiss();
        }
        catch (Exception e){

        }
    }
}

package com.sriley.gobyshankspony.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.PhoneCallManager;
import com.sriley.gobyshankspony.model.ScrapeManager;
import com.sriley.gobyshankspony.model.interfaces.PhoneScrapeRequestListener;
import com.sriley.gobyshankspony.model.utils.GSONFactory;
import com.sriley.gobyshankspony.view.dialogs.ErrorDialog;
import com.sriley.gobyshankspony.view.dialogs.ProgressBarDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class SingleFavoriteFragment extends Fragment implements PhoneScrapeRequestListener {

    public static final String PROPERTY_ARGS_KEY = "property";

    @BindView(R.id.SingleFavoriteImageView)
    ImageView mImageView;
    @BindView(R.id.SingleFavoriteAddressTextView)
    TextView mAddressTextView;
    @BindView(R.id.SingleFavoriteBedroomsTextView)
    TextView mBedroomsTextView;
    @BindView(R.id.SingleFavoriteBathroomsTextView)
    TextView mBathroomsTextView;
    @BindView(R.id.SingleFavoriteRentTextView)
    TextView mRentTextView;
    @BindView(R.id.SingleFavoriteNameTextView)
    TextView mNameTextView;
    @BindView(R.id.favoriteCallFabButton)
    FloatingActionButton mCallFab;
    @BindView(R.id.FavoritefabMenu)FloatingActionsMenu mFabMenu;


    ListingProperty mListingProperty;
    ProgressBarDialog mProgressBarDialog;
    WebView mWebView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_favorite, container, false);
        ButterKnife.bind(this, view);

        mWebView=new WebView(getContext());
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


    private void loadImage(String imageUrl) {
        if (imageUrl.contains(".svg"))
            Picasso.with(getContext()).load(R.drawable.image_not_available).resize(400, 300).centerCrop().into(mImageView);
        else
            Picasso.with(getContext()).load(imageUrl).resize(400, 300).centerCrop().into(mImageView);
    }


    @OnClick(R.id.favoriteCallFabButton)
    public void onCallFabClicked() {

        mFabMenu.collapse();
        mProgressBarDialog=new ProgressBarDialog();
        mProgressBarDialog.show(getFragmentManager(),"progress_bar");
        ScrapeManager.getPropertyPhone(mWebView,mListingProperty.getDetailsUrl(),this);
    }

    @Override
    public void onPhoneRetrieved(String phone) {
        mFabMenu.collapse();
        mProgressBarDialog.dismiss();
        if(phone!=null)
            PhoneCallManager.callNumber(phone,getActivity());
        else {
            ErrorDialog.displayDialog(getFragmentManager(),ErrorDialog.BROKER_ERROR_MESSAGE+mListingProperty.getBroker());
        }
    }
}
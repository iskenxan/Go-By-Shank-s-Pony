package com.sriley.gobyshankspony.view.fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.sriley.gobyshankspony.ContentActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseDatabaseManager;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.PermissionManager;
import com.sriley.gobyshankspony.model.interfaces.ExternalFilePermissionListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyPhotoUploadListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyRegisteredListener;
import com.sriley.gobyshankspony.model.interfaces.GalleryImageSelectedListener;
import com.sriley.gobyshankspony.model.utils.BitmapHandler;
import com.sriley.gobyshankspony.model.utils.EmptyFieldsChecker;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.utils.GSONFactory;
import com.sriley.gobyshankspony.model.utils.GalleryBrowser;
import com.sriley.gobyshankspony.model.utils.StatesHashmap;
import com.sriley.gobyshankspony.view.dialogs.ErrorDialog;
import com.sriley.gobyshankspony.view.dialogs.ProgressBarDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddNewPropertyFragment extends Fragment implements FirebasePropertyRegisteredListener, GalleryImageSelectedListener, FirebasePropertyPhotoUploadListener, ExternalFilePermissionListener {

    public static final String PROPERTY_ARGS = "property";
    public static final String MESSAGE_ERROR="There was an error saving the property. Please try again.";
    public static final String MESSAGE_SUCCESS="Property was successfully saved";


    @BindView(R.id.AddPropertyUploadPhotoButton)
    Button mUploadPhotoButton;
    @BindView(R.id.AddPropertyPhotoImageView)
    ImageView mImageView;
    @BindView(R.id.AddPropertyNameEditText)
    EditText mNameEditText;
    @BindView(R.id.AddPropertyAddressEditText)
    EditText mAddressEditText;
    @BindView(R.id.AddPropertyCityEditText)
    EditText mCityEditText;
    @BindView(R.id.AddPropertyPostalEditText)
    EditText mPostalCodeEditText;
    @BindView(R.id.AddPropertyPriceEditText)
    EditText mPriceEditText;
    @BindView(R.id.AddPropertyPhoneNumberEditText)
    EditText mPhonenumberEditText;
    @BindView(R.id.AddPropertyStateSpinner)
    Spinner mStateSpinner;
    @BindView(R.id.AddPropertyTypeSpinner)
    Spinner mPropertyTypeSpinner;
    @BindView(R.id.AddPropertyBedroomsSpinner)
    Spinner mBedroomsSpinner;
    @BindView(R.id.AddPropertyBathroomsSpinner)
    Spinner mBathroomSpinner;

    @BindView(R.id.AddPropertySaveButton)
    Button mSaveButton;
    @BindView(R.id.AddPropertyMainContainer)
    LinearLayout mMainContainer;

    Bitmap mReducedSizePhoto;
    Uri mUploadedImageUri;
    ListingProperty mProperty=new ListingProperty();
    ProgressBarDialog mProgressBarDialog;


    List<String> mStatesList;
    List<String> mPropertyTypeList;
    List<String> mRoomNumbersList;

    boolean mNewPhotoUploaded = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_property, container, false);
        ButterKnife.bind(this, view);

        setupSpinnerLists();
        addListeners();
        setupSpinners();
        extractArgsProperty();



        return view;
    }



    private void setupSpinnerLists(){
        mStatesList = new ArrayList<>(StatesHashmap.getAbreviationHashMap().values());
        Collections.sort(mStatesList);
        mRoomNumbersList = Formatter.getRoomNumbersList();
        mPropertyTypeList = Formatter.getPropertyTypeList();
    }


    private void extractArgsProperty() {
        if(getArguments()!=null){
            String propertyStr = getArguments().getString(PROPERTY_ARGS, null);
            ListingProperty property = GSONFactory.convertStringToListingProperty(propertyStr);
            mProperty = property;
            FirebaseDatabaseManager.removeListingPropertyFromDatabase(property,null);
            populateViews();
        }
    }


    private void addListeners() {
        ContentActivity activity = (ContentActivity) getActivity();
        activity.setGalleryImageSelectedListener(this);
        activity.setExternalFilePermissionListener(this);
    }



    private void setupSpinners() {
        Formatter.setupSpinner(mStatesList, mStateSpinner);
        Formatter.setupSpinner(mPropertyTypeList, mPropertyTypeSpinner);
        Formatter.setupSpinner(mRoomNumbersList, mBathroomSpinner);
        Formatter.setupSpinner(mRoomNumbersList, mBedroomsSpinner);
    }


    private void populateViews() {
        Picasso.with(getContext()).load(mProperty.getImageUrl()).into(mImageView);
        populateTextViews();
        populateSpinners();
  }



    private void populateTextViews(){
        mNameEditText.setText(mProperty.getName());
        mAddressEditText.setText(mProperty.getAddress());
        mCityEditText.setText(mProperty.getCity());
        mPostalCodeEditText.setText(mProperty.getZip());
        mPriceEditText.setText(mProperty.getPrice());
        mPhonenumberEditText.setText(mProperty.getPhoneNumber());
    }


    private void populateSpinners(){
        int stateIndex = mStatesList.indexOf(mProperty.getState());
        int propertyTypeIndex=mPropertyTypeList.indexOf(mProperty.getPropertyType());
        int bedroomNumIndex=mRoomNumbersList.indexOf(mProperty.getBedrooms());
        int bathroomNumIndex=mRoomNumbersList.indexOf(mProperty.getBathrooms());

        mStateSpinner.setSelection(stateIndex);
        mPropertyTypeSpinner.setSelection(propertyTypeIndex);
        mBedroomsSpinner.setSelection(bedroomNumIndex);
        mBathroomSpinner.setSelection(bathroomNumIndex);
    }



    @OnClick(R.id.AddPropertyUploadPhotoButton)
    public void onUploadPhotoButtonClicked() {
        if(PermissionManager.isExternalFilePermissionGranted(getActivity()))
            GalleryBrowser.startGalleryBrowsingIntent(getActivity());
        else
            PermissionManager.requestExternalFilePermission(getActivity());
    }


    @Override
    public void onExternalFilePermissionResult(boolean result) {
        if(result)
            GalleryBrowser.startGalleryBrowsingIntent(getActivity());
    }


    @Override
    public void onImageSelected(Uri imageUri) {
        if (imageUri != null) {
            mUploadedImageUri = imageUri;

            mReducedSizePhoto= BitmapHandler.decodeFile(mUploadedImageUri,getContext());

            mImageView.setImageBitmap(mReducedSizePhoto);
            mNewPhotoUploaded=true;
        }
    }



    @OnClick(R.id.AddPropertySaveButton)
    public void onSaveButtonClicked() {
        boolean allFieldsPopulated = EmptyFieldsChecker.areAllFieldsPopulated(mMainContainer);
        if (allFieldsPopulated) {
          startRegisteringProcess();
        } else
            ErrorDialog.displayDialog(getFragmentManager(), ErrorDialog.EMPTY_FELDS_ERROR_MESSAGE);
    }



    private void startRegisteringProcess(){
        mProgressBarDialog = new ProgressBarDialog();
        mProgressBarDialog.show(getFragmentManager(), "progress_bar");

        createPropertyFromUserInput();
        FirebaseDatabaseManager.registerProperty(mProperty, this);
    }


    @Override
    public void onPropertyRegistered(boolean success) {
        if(success&&mNewPhotoUploaded){

            FirebaseDatabaseManager.savePropertyPhoto(mProperty, mReducedSizePhoto, this);
        }
        else if (success&&!mNewPhotoUploaded)
            showMessageAndStartManagerFragment(MESSAGE_SUCCESS);
        else if(!success)
            showMessageAndStartManagerFragment(MESSAGE_ERROR);
    }



    private void showMessageAndStartManagerFragment(String message){
        mProgressBarDialog.dismiss();
        Toast.makeText(getContext(),message, Toast.LENGTH_LONG).show();
        FragmentFactory.startManagerPropertyListFragment((AppCompatActivity) getActivity());
    }



    @Override
    public void onUploadFinished(boolean success) {
        if (success) {
            showMessageAndStartManagerFragment(MESSAGE_SUCCESS);
        }
        else
            showMessageAndStartManagerFragment(MESSAGE_ERROR);
    }


    private void createPropertyFromUserInput() {
        mProperty.setName(mNameEditText.getText() + "");
        String formattedAddress = mAddressEditText.getText() + "";
        formattedAddress = Formatter.removeFirebaseInvalidPathChars(formattedAddress);
        mProperty.setAddress(formattedAddress);
        mProperty.setCity(mCityEditText.getText() + "");
        mProperty.setState(mStateSpinner.getSelectedItem() + "");
        mProperty.setZip(mPostalCodeEditText.getText() + "");
        mProperty.setPropertyType(mPropertyTypeSpinner.getSelectedItem() + "");
        mProperty.setBedrooms(mBedroomsSpinner.getSelectedItem() + "");
        mProperty.setBathrooms(mBathroomSpinner.getSelectedItem() + "");

        mProperty.setPrice(mPriceEditText.getText() + "");
        if(mProperty.getPropertyType().equals(Formatter.RENTAL))
            mProperty.setPrice(mProperty.getPrice()+"/m");
        mProperty.setPhoneNumber(mPhonenumberEditText.getText() + "");

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String username = Formatter.convertEmailIntoUserkey(userEmail);
        mProperty.setManagerUsername(username);
    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            if(mProgressBarDialog!=null)
                mProgressBarDialog.dismiss();
        }
        catch (Exception e  ){

        }

    }
}

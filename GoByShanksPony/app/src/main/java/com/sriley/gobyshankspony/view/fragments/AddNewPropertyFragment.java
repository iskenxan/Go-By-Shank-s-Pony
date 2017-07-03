package com.sriley.gobyshankspony.view.fragments;

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
import com.sriley.gobyshankspony.ContentActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.PhoneCallManager;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyPhotoUploadListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyRegisteredListener;
import com.sriley.gobyshankspony.model.interfaces.GalleryImageSelectedListener;
import com.sriley.gobyshankspony.model.utils.EmptyFieldsChecker;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.utils.GalleryBrowser;
import com.sriley.gobyshankspony.model.utils.StatesHashmap;
import com.sriley.gobyshankspony.view.dialogs.ErrorDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddNewPropertyFragment extends Fragment implements FirebasePropertyRegisteredListener, GalleryImageSelectedListener, FirebasePropertyPhotoUploadListener {

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

    Uri mUploadedImageUri;
    ListingProperty mProperty;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_property, container, false);
        ButterKnife.bind(this, view);

        addListeners();
        setupSpinners();

        return view;
    }

    private void addListeners() {
        ContentActivity activity = (ContentActivity) getActivity();
        activity.setGalleryImageSelectedListener(this);
    }

    private void setupSpinners() {
        List<String> states = new ArrayList<>(StatesHashmap.getAbreviationHashMap().values());
        Collections.sort(states);
        Formatter.setupSpinner(states, mStateSpinner);

        String[] propertyType = new String[]{"Rental", "For Sale"};
        Formatter.setupSpinner(propertyType, mPropertyTypeSpinner);

        String[] roomsNumbers = new String[]{"0", "1", "2", "3", "4", "5"};
        Formatter.setupSpinner(roomsNumbers, mBathroomSpinner);
        Formatter.setupSpinner(roomsNumbers, mBedroomsSpinner);
    }


    @OnClick(R.id.AddPropertyUploadPhotoButton)
    public void onUploadPhotoButtonClicked() {
        GalleryBrowser.startGalleryBrowsingIntent(getActivity());
    }


    @Override
    public void onImageSelected(Uri imageUri) {
        if (imageUri != null)
            mUploadedImageUri = imageUri;
        mImageView.setImageURI(mUploadedImageUri);
    }


    @OnClick(R.id.AddPropertySaveButton)
    public void onSaveButtonClicked() {
        boolean allFieldsPopulated = EmptyFieldsChecker.areAllFieldsPopulated(mMainContainer);
        if (allFieldsPopulated) {
            createPropertyFromUserInput();
            FirebaseManager.registerProperty(mProperty, this);
        } else
            ErrorDialog.displayDialog(getFragmentManager(), ErrorDialog.EMPTY_FELDS_ERROR_MESSAGE);
    }


    @Override
    public void onPropertyRegistered(boolean success) {
        if (success)
            FirebaseManager.savePropertyPhoto(mProperty, mUploadedImageUri, this);
        else {
            Toast.makeText(getContext(), "There was an error saving the property. Please try again.", Toast.LENGTH_LONG).show();
            FragmentFactory.startManagerPropertyListFragment((AppCompatActivity) getActivity());
        }
    }


    @Override
    public void onUploadFinished(boolean success) {
        if (success) {
            Toast.makeText(getContext(), "Property was successfully saved", Toast.LENGTH_LONG).show();
            FragmentFactory.startManagerPropertyListFragment((AppCompatActivity) getActivity());
        }
    }


    private void createPropertyFromUserInput() {
        ListingProperty property = new ListingProperty();
        property.setName(mNameEditText.getText() + "");
        property.setAddress(mAddressEditText.getText() + "");
        property.setCity(mCityEditText.getText() + "");
        property.setState(mStateSpinner.getSelectedItem() + "");
        property.setZip(mPostalCodeEditText.getText() + "");
        property.setPropertyType(mPropertyTypeSpinner.getSelectedItem() + "");
        property.setBedrooms(mBedroomsSpinner.getSelectedItem() + "");
        property.setBathrooms(mBathroomSpinner.getSelectedItem() + "");
        property.setPrice(mPriceEditText.getText() + "");
        property.setPhoneNumber(mPhonenumberEditText.getText() + "");

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String username = Formatter.convertEmailIntoUserkey(userEmail);
        property.setManagerUsername(username);

        mProperty = property;
    }


}

package com.sriley.gobyshankspony.model;


import android.net.Uri;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractManagerListingRecordsListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractSinglePropertyListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractPropertiesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyPhotoUploadListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyRegisteredListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUsertypeListener;
import com.sriley.gobyshankspony.model.utils.Formatter;

public class FirebaseManager {
    public static final String USERS = "users";
    public static final String USER_FAVORITES="favorites";
    public static final String USER_TYPE="userType";
    public static final String PROPERTIES="user_registered_properties";
    public static final String MANAGER_PROPERTIES_LIST="properties_managed";


    // user's email is used as unique key  for user database objects



    public static void SignOutGmail(GoogleApiClient googleApiClient) {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient);

    }


    public static void signInWithFacebook(String token, FirebaseAuthenticationListener listener) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token);
        signInUser(credential,listener);
    }


    public static void signInWithGmail(String token,FirebaseAuthenticationListener listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        signInUser(credential,listener);
    }


    private static void signInUser(AuthCredential credential,FirebaseAuthenticationListener listener) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(new FirebaseCallBacks
                .onFirebaseSignUpCompleteListener(listener));
    }


    public static void checkIfNewUser( final FirebaseUserCheckListener listener){
        FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(currentUser.getEmail());

        database.child(USERS).child(userKey).addListenerForSingleValueEvent(new FirebaseCallBacks.onCheckIfUserExistsCallBack(listener));
    }



    public static void getUsertype(FirebaseUsertypeListener listener){
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userkey=Formatter.convertEmailIntoUserkey(currentUser.getEmail());

        database.child(USERS).child(userkey).child(USER_TYPE).addListenerForSingleValueEvent(new FirebaseCallBacks
                .onUserTypeExtractCallBack(listener));
    }


    public static void saveUserDetails(User user) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(user.getEmail());

        database.child(USERS).child(userKey).setValue(user);
    }


    public static void getFavorites(final FirebaseExtractPropertiesListener listener){
        FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(currentUser.getEmail());

        database.child(USERS).child(userKey).child(USER_FAVORITES).addValueEventListener(new FirebaseCallBacks.onFavoriteExtractCallBack(listener));
    }



    public static void savePropertyInFavorites(ListingProperty property, FirebaseFavoritesListener listener){
        FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(currentUser.getEmail());

        database.child(USERS).child(userKey).child(USER_FAVORITES).child(property.getAddress()).setValue(property)
                .addOnCompleteListener(new FirebaseCallBacks.onFavoritesAddCallBack(listener));
    }



    public static void savePropertyPhoto(ListingProperty property, Uri imageFile, FirebasePropertyPhotoUploadListener listener){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        storageRef.child(property.getZip()).child(property.getAddress()).putFile(imageFile)
                .addOnSuccessListener(new FirebaseCallBacks.onFileUploadCallback(listener,property));
    }


    public static void getManagedPropertyRecords(FirebaseExtractManagerListingRecordsListener listener){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userKey=Formatter.convertEmailIntoUserkey(user.getEmail());

        database.child(USERS).child(userKey).child(MANAGER_PROPERTIES_LIST).addListenerForSingleValueEvent(new
                FirebaseCallBacks.onManagerListingRecordsExtractedCallBack(listener));
    }

    public static void getManagedProperty(FirebaseExtractSinglePropertyListener listener, FirebaseManagerListingRecord listingRecord){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(PROPERTIES).child(listingRecord.getZip()).child(listingRecord.getAddress())
                .addListenerForSingleValueEvent(new FirebaseCallBacks.onManagedPropertyExtractCallback(listener));
    }



    public static void addListingToManagerList(ListingProperty property){
        FirebaseManagerListingRecord listingRecord=new FirebaseManagerListingRecord();
        listingRecord.setZip(property.getZip());
        listingRecord.setAddress(property.getAddress());

        FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(currentUser.getEmail());

        database.child(USERS).child(userKey).child(MANAGER_PROPERTIES_LIST).child(listingRecord.getAddress()).setValue(listingRecord);
    }



    public static void addPhotoUrlToListing(ListingProperty property,String photoUrl){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(PROPERTIES).child(property.getZip()).child(property.getAddress()).child("imageUrl").setValue(photoUrl);
    }

    public static void registerProperty(ListingProperty property, FirebasePropertyRegisteredListener listener){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child(PROPERTIES).child(property.getZip()).child(property.getAddress()).setValue(property)
                .addOnCompleteListener(new FirebaseCallBacks.onPropertyRegisteredCallBack(listener));
    }


}

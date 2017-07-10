package com.sriley.gobyshankspony.model;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractManagerListingRecordsListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractSinglePropertyListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractPropertiesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyDeleteListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyPhotoUploadListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyRegisteredListener;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyUserActivityListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUsertypeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseCallBacks {

    public static class onFirebaseSignUpCompleteListener implements OnCompleteListener<AuthResult> {

        private FirebaseAuthenticationListener mListener;

        public onFirebaseSignUpCompleteListener(FirebaseAuthenticationListener listener) {
            mListener = listener;
        }


        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                mListener.onAuthenticationComplete(true);
            } else
                mListener.onAuthenticationComplete(false);
        }

    }


    public static class onCheckIfUserExistsCallBack implements ValueEventListener {


        private FirebaseUserCheckListener mListener;

        public onCheckIfUserExistsCallBack(FirebaseUserCheckListener listener) {
            mListener = listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() != null)
                mListener.onUserCheck(false);
            else
                mListener.onUserCheck(true);
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


    public static class onModifyFavoritesCallback implements OnCompleteListener<Void> {

        FirebaseFavoritesListener mListener;

        public onModifyFavoritesCallback(FirebaseFavoritesListener listener) {
            mListener = listener;
        }

        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful())
                mListener.onPropertyAddedToFavorites(true);
            else
                mListener.onPropertyAddedToFavorites(false);
        }
    }

    public static class isPropertyInFavoritesCallback implements ValueEventListener{

        ListingProperty mProperty;
        FirebaseFavoritesListener mListener;

        public isPropertyInFavoritesCallback(ListingProperty property,FirebaseFavoritesListener listener){
            mProperty=property;
            mListener=listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.getValue()==null){
                FirebaseDatabaseManager.savePropertyInFavorites(mProperty,mListener);
                FirebaseDatabaseManager.updateFavoriteNumForProperty(mProperty,FirebaseDatabaseManager.ACTION_INCREMENT);
            }
            else
                mListener.onPropertyAddedToFavorites(true);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }



    public static class onPropertiesExtractCallBack implements ValueEventListener {
        FirebaseExtractPropertiesListener mListener;


        public onPropertiesExtractCallBack(FirebaseExtractPropertiesListener listener) {
            mListener = listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> list = dataSnapshot.getChildren();
            ArrayList<ListingProperty> properties = new ArrayList<>();

            addPropertiesToFavorites(list, properties);
            mListener.onPropertiesExtracted(properties);
        }


        private void addPropertiesToFavorites(Iterable<DataSnapshot> firebaseList, ArrayList<ListingProperty> properties) {
            for (DataSnapshot data : firebaseList) {
                ListingProperty property = data.getValue(ListingProperty.class);
                properties.add(property);
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }



    public static class onUserTypeExtractCallBack implements ValueEventListener {
        FirebaseUsertypeListener mListener;

        public onUserTypeExtractCallBack(FirebaseUsertypeListener listener) {
            mListener = listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() != null) {
                String userType = (String) dataSnapshot.getValue();
                mListener.onUsertypeExtracted(userType);
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }



    public static class onPropertyRegisteredCallBack implements OnCompleteListener<Void> {

        FirebasePropertyRegisteredListener mListener;
        ListingProperty mProperty;

        public onPropertyRegisteredCallBack(FirebasePropertyRegisteredListener listener,ListingProperty property) {
            mListener = listener;
            mProperty=property;
        }


        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                FirebaseDatabaseManager.addListingToManagerList(mProperty);
                mListener.onPropertyRegistered(true);
            }
            else
                mListener.onPropertyRegistered(false);
        }
    }



    public static class onFileUploadCallback implements OnSuccessListener<UploadTask.TaskSnapshot>, OnFailureListener {
        ListingProperty mProperty;
        FirebasePropertyPhotoUploadListener mListener;


        public onFileUploadCallback(FirebasePropertyPhotoUploadListener listener, ListingProperty property) {
            mListener = listener;
            mProperty = property;
        }


        @Override
        public void onFailure(@NonNull Exception e) {
            mListener.onUploadFinished(false);
        }


        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            String photoUrl = taskSnapshot.getDownloadUrl().toString();
            FirebaseDatabaseManager.addPhotoUrlToListing(mProperty, photoUrl);
            mListener.onUploadFinished(true);
        }

    }



    public static class onManagerListingRecordsExtractedCallBack implements ValueEventListener{

        FirebaseExtractManagerListingRecordsListener mListener;


        public onManagerListingRecordsExtractedCallBack(FirebaseExtractManagerListingRecordsListener listener){
            mListener=listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshots) {
            if(dataSnapshots.getChildren()!=null){
                ArrayList<FirebaseManagerListingRecord> listingRecords=new ArrayList<>();
                for (DataSnapshot dataSnapshot:dataSnapshots.getChildren()){
                    FirebaseManagerListingRecord listingRecord= dataSnapshot.getValue(FirebaseManagerListingRecord.class);
                    listingRecords.add(listingRecord);
                }
                mListener.onListingRecordsExtracted(listingRecords);
            }
        }



        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


    public static class onManagedPropertyExtractCallback implements ValueEventListener{
        private FirebaseExtractSinglePropertyListener mListener;


        public onManagedPropertyExtractCallback(FirebaseExtractSinglePropertyListener listener){
            mListener=listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.getValue()!=null){
                ListingProperty property= dataSnapshot.getValue(ListingProperty.class);
                mListener.onPropertyExtracted(property);
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


    public static class onPropertyDeleteCallback implements OnCompleteListener{

        FirebasePropertyDeleteListener mListener;

        public onPropertyDeleteCallback(FirebasePropertyDeleteListener listener){
            mListener=listener;
        }

        @Override
        public void onComplete(@NonNull Task task) {
            if(mListener!=null){
                if(task.isSuccessful())
                    mListener.onPropertyRemoved(true);
                else
                    mListener.onPropertyRemoved(false);
            }
        }
    }


    public static class onPropertyUserActivityModifyCallback implements ValueEventListener{
        String mAction;
        ListingProperty mProperty;

        public onPropertyUserActivityModifyCallback(String action,ListingProperty property){
            mAction=action;
            mProperty=property;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.getValue()!=null){
                int value=dataSnapshot.getValue(Integer.class);
                if(mAction.equals(FirebaseDatabaseManager.ACTION_INCREMENT))
                    value+=1;
                else
                    value-=1;

                    dataSnapshot.getRef().setValue(value);
            }
            else
                dataSnapshot.getRef().setValue(1);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            String error=databaseError.getMessage();
            Log.v("FIREBASE",error);
        }
    }

    public static class onGetPropertyUserActivityCallback implements ValueEventListener{

        ListingProperty mProperty;
        FirebasePropertyUserActivityListener mListener;


        public onGetPropertyUserActivityCallback(ListingProperty property,FirebasePropertyUserActivityListener listener){
            mProperty=property;
            mListener=listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.getValue()!=null){
                Map<String,Long> userActivity= (Map<String, Long>) dataSnapshot.getValue();
                long viewed=userActivity.get("viewed");
                mProperty.setViewed(viewed);
                if(userActivity.size()>1){
                    long inFavorites=userActivity.get("favorites");
                    mProperty.setInFavorites(inFavorites);
                }
            }
            else{
                mProperty.setViewed(0);
                mProperty.setInFavorites(0);
            }
            mListener.onUserActivityExtracted(true);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


}

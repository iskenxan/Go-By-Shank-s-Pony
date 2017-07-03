package com.sriley.gobyshankspony.model;


import android.support.annotation.NonNull;

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
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUsertypeListener;

import java.util.ArrayList;

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


    public static class onFavoritesAddCallBack implements OnCompleteListener<Void> {

        FirebaseFavoritesListener mListener;

        public onFavoritesAddCallBack(FirebaseFavoritesListener listener) {
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



    public static class onFavoriteExtractCallBack implements ValueEventListener {
        FirebaseExtractPropertiesListener mListener;


        public onFavoriteExtractCallBack(FirebaseExtractPropertiesListener listener) {
            mListener = listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> list = dataSnapshot.getChildren();
            ArrayList<ListingProperty> favorites = new ArrayList<>();

            addPropertiesToFavorites(list, favorites);
            mListener.onPropertiesExtracted(favorites);
        }


        private void addPropertiesToFavorites(Iterable<DataSnapshot> firebaseList, ArrayList<ListingProperty> favorites) {
            for (DataSnapshot data : firebaseList) {
                ListingProperty property = data.getValue(ListingProperty.class);
                favorites.add(property);
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
                FirebaseManager.addListingToManagerList(mProperty);
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
            FirebaseManager.addPhotoUrlToListing(mProperty, photoUrl);
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


}

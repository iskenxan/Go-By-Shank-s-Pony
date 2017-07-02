package com.sriley.gobyshankspony.model;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseGetFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;

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

        public onFavoritesAddCallBack(FirebaseFavoritesListener listener){
            mListener=listener;
        }

        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful())
                mListener.onPropertyAddedToFavorites(true);
            else
                mListener.onPropertyAddedToFavorites(false);
        }
    }


    public static class onFavoriteExtractCallBack implements ValueEventListener{
        FirebaseGetFavoritesListener mListener;


        public onFavoriteExtractCallBack(FirebaseGetFavoritesListener listener){
            mListener=listener;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> list = dataSnapshot.getChildren();
            ArrayList<ListingProperty> favorites=new ArrayList<>();

            addPropertiesToFavorites(list,favorites);
            mListener.onFavoritesExtracted(favorites);
        }



        private void addPropertiesToFavorites(Iterable<DataSnapshot> firebaseList, ArrayList<ListingProperty> favorites){
            for (DataSnapshot data:firebaseList){
                ListingProperty property=data.getValue(ListingProperty.class);
                favorites.add(property);
            }
        }



        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}

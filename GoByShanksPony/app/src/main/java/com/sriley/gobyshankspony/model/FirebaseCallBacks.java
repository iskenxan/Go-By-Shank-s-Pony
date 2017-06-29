package com.sriley.gobyshankspony.model;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;

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
}

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
import com.sriley.gobyshankspony.model.interfaces.CheckIfUserExistsListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseSignUpCompleteListener;

public class FirebaseCallBacks {

    public static class OnGmailUserSignUpCompleteCallBack implements OnCompleteListener<AuthResult>{

        private FirebaseSignUpCompleteListener mListener;

        public OnGmailUserSignUpCompleteCallBack(FirebaseSignUpCompleteListener listener){
            mListener=listener;
        }


        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            final FirebaseAuth auth= FirebaseAuth.getInstance();

            if(task.isSuccessful()){
                FirebaseUser currentUser=auth.getCurrentUser();
                mListener.onGmailUserSignInComplete(currentUser);
            }
        }

    }

}

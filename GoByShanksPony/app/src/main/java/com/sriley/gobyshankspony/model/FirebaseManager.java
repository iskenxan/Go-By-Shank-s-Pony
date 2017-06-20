package com.sriley.gobyshankspony.model;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sriley.gobyshankspony.model.interfaces.FirebaseSignUpCompleteListener;

public class FirebaseManager {

    public static void SignUpUserToDataBase(GoogleSignInAccount account, final FirebaseSignUpCompleteListener listener){
        final FirebaseAuth auth= FirebaseAuth.getInstance();
        String token=account.getIdToken();
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);

        auth.signInWithCredential(credential).addOnCompleteListener(new FirebaseCallBacks
                .OnUserSignUpCompleteCallBack(listener));

    }

    public static void SignOut(GoogleApiClient googleApiClient){
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient);
    }

}

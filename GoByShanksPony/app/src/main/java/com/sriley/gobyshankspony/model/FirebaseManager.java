package com.sriley.gobyshankspony.model;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.model.utils.Formatter;

public class FirebaseManager {
    public static final String USERS = "users";
    public static final String USER_EMAIL = "email";
    public static final String USER_FIRST = "first_name";
    public static final String USER_LAST = "last_name";
    public static final String USER_TYPE = "user_type";

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


    public static void checkIfNewUserAndSaveDetails(final User user) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(user.getEmail());
        database.child(USERS).child(userKey).addListenerForSingleValueEvent(new FirebaseCallBacks.onCheckIfUserExistsCallBack(user));
    }



    public static void saveUserDetails(User user) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(user.getEmail());

        DatabaseReference users = database.child(USERS).child(userKey);
        users.child(USER_EMAIL).setValue(user.getEmail());
        users.child(USER_FIRST).setValue(user.getFirtName());
        users.child(USER_LAST).setValue(user.getLastName());
        users.child(USER_TYPE).setValue(user.getUserType());
    }

}

package com.sriley.gobyshankspony.model;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseAuthenticationListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseGetFavoritesListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseUserCheckListener;
import com.sriley.gobyshankspony.model.utils.Formatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {
    public static final String USERS = "users";
    public static final String USER_EMAIL = "email";
    public static final String USER_NAME="user_name";
    public static final String USER_TYPE = "user_type";
    public static final String USER_FAVORITES="favorites";


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


    public static void checkIfNewUser(User user, final FirebaseUserCheckListener listener){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(user.getEmail());

        database.child(USERS).child(userKey).addListenerForSingleValueEvent(new FirebaseCallBacks
                .onCheckIfUserExistsCallBack(listener));
    }


    public static void saveUserDetails(User user) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(user.getEmail());
        Map<String,String> userDetailsMap=new HashMap<>();

        userDetailsMap.put(USER_NAME,user.getFullName());
        userDetailsMap.put(USER_EMAIL,user.getEmail());
        userDetailsMap.put(USER_TYPE,user.getUserType());

        database.child(USERS).child(userKey).setValue(userDetailsMap);
    }


    public static void getFavorites(final FirebaseGetFavoritesListener listener){
        FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(currentUser.getEmail());

        database.child(USERS).child(userKey).child(USER_FAVORITES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    Iterable<DataSnapshot> list = dataSnapshot.getChildren();
                    ArrayList<ListingProperty> properties=new ArrayList<ListingProperty>();
                    for (DataSnapshot data:list){
                        ListingProperty property=data.getValue(ListingProperty.class);
                        properties.add(property);
                    }
                    listener.onFavoritesExtracted(properties);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void savePropertyInFavorites(ListingProperty property, FirebaseFavoritesListener listener){
        FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userKey = Formatter.convertEmailIntoUserkey(currentUser.getEmail());

        database.child(USERS).child(userKey).child(USER_FAVORITES).child(property.getAddress()).setValue(property)
                .addOnCompleteListener(new FirebaseCallBacks.onFavoritesAddCallBack(listener));
    }


}

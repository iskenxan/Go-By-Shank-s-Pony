package com.sriley.gobyshankspony.model;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sriley.gobyshankspony.model.interfaces.CheckIfUserExistsListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseSignUpCompleteListener;
import com.sriley.gobyshankspony.model.utils.Formatter;

public class FirebaseManager {
    public static final String USERS="users";
    public static final String USER_EMAIL="email";
    public static final String USER_FIRST="first_name";
    public static final String USER_LAST="last_name";
    public static final String USER_TYPE="user_type";
    // user's email is used as unique key  for user database objects


    public static void SignUpUserToDataBase(User user, CheckIfUserExistsListener listener){
        checkIfUserExistsAndSignUp(user,listener);
    }


    public static void SignOutGmail(GoogleApiClient googleApiClient){
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient);
    }


    public static void SignOutFacebook(){

    }


    public static void checkIfUserExistsAndSignUp(final User user, final CheckIfUserExistsListener listener){
        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        String userKey=Formatter.convertEmailIntoUserkey(user.getEmail());

        database.child(USERS).child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    signUpNewUser(user);
                    saveUserDetails(user);
                    listener.OnCheckIfUserExistsResult(false);
                }
                else
                    listener.OnCheckIfUserExistsResult(true);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private static void signUpNewUser(User user){
        final FirebaseAuth auth= FirebaseAuth.getInstance();
        String token=user.getTokenId();
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);

        auth.signInWithCredential(credential);
    }


    public static void saveUserDetails(User user){
        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        String userKey= Formatter.convertEmailIntoUserkey(user.getEmail());

        DatabaseReference users= database.child(USERS).child(userKey);
        users.child(USER_EMAIL).setValue(user.getEmail());
        users.child(USER_FIRST).setValue(user.getFirtName());
        users.child(USER_LAST).setValue(user.getLastName());
        users.child(USER_TYPE).setValue(user.getUserType());
    }

}

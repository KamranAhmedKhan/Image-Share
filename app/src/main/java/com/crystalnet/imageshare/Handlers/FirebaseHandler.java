package com.crystalnet.imageshare.Handlers;

import android.util.Log;

import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


/**
 * Created by root on 05/01/16.
 */
public class FirebaseHandler {

    private Firebase firebaseRef;
    private static FirebaseHandler ourInstance;
    private static User user;
    private AuthData authData = FirebaseHandler.getInstance().firebaseRef.getAuth();

    public static FirebaseHandler getInstance() {
        if (ourInstance == null) {
            ourInstance = new FirebaseHandler();
            Log.i(Utilities.FirebaseTAG, "FB Handler - Instance is creating..\n" +
                    ourInstance.toString());
        }
        return ourInstance;
    }

    private FirebaseHandler() {
        firebaseRef = new Firebase("Firebase Url");
    }

    private boolean getAuthStatus() {
        if (authData != null) {
            Log.i(Utilities.FirebaseTAG, "FB Handler - User Already authenticated..");
            return true;
        }
        else
            Log.i(Utilities.FirebaseTAG, "FB Handler - Session Expired..");
            return false;
    }

    private User getLoginedUser() {
        if (this.user == null) {
            FirebaseHandler.getInstance().firebaseRef.child("Users").child(authData.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        return user;
    }

    private void getPosts() {
        //returning the posts array list
    }
}

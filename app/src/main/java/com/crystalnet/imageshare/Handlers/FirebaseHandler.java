package com.crystalnet.imageshare.Handlers;

import android.util.Log;

import com.cloudinary.Util;
import com.crystalnet.imageshare.Activities.MainActivity;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import javax.security.auth.callback.Callback;


/**
 * Created by root on 05/01/16.
 */
public class FirebaseHandler {

    private Firebase firebaseRef;
    private static FirebaseHandler ourInstance;
    private User user;
//    private AuthData authData = FirebaseHandler.getInstance().firebaseRef.getAuth();

    public static FirebaseHandler getInstance() {
        if (ourInstance == null) {
            ourInstance = new FirebaseHandler();
            Log.i(Utilities.FirebaseTAG, "FB Handler - Instance is creating..\n" +
                    ourInstance.toString());
        }
        return ourInstance;
    }

    private FirebaseHandler() {
        Firebase.setAndroidContext(MainActivity.context);
        firebaseRef = new Firebase("https://fb-todolist.firebaseio.com/");
    }

//    private boolean getAuthStatus() {
//        if (authData != null) {
//            Log.i(Utilities.FirebaseTAG, "FB Handler - User Already authenticated..");
//            return true;
//        }
//        else
//            Log.i(Utilities.FirebaseTAG, "FB Handler - Session Expired..");
//            return false;
//    }

    public User getLoginedUser() {Log.e("Logined User: ","in");
        if (user == null) {
            FirebaseHandler.getInstance().firebaseRef.child("Users").child("c209a2d4-afa5-4f88-886a-38cafe74394d"/*authData.getUid()*/).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e("datasnapshot: ", dataSnapshot.toString());

                    user = dataSnapshot.getValue(User.class);
                    Log.e("datasnapshot User: ", "Name: "+user.getName()+
                    ", Email: "+user.getEmail()+", Image: "+user.getImage());
//                    Utilities.successToast("Name: " + user.getName()+
//                            ", Email: "+user.getEmail()+", Image: "+user.getImage());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Utilities.errorToast(firebaseError.toString());
                }
            });
        }
        return this.user;
    }

    private void getPosts() {
        //returning the posts array list
    }
}

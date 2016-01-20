package com.crystalnet.imageshare.Handlers;

import android.util.Log;
import android.widget.Toast;

import com.cloudinary.Util;
import com.crystalnet.imageshare.Activities.MainActivity;
import com.crystalnet.imageshare.Fragments.HomeFragment;
import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.ServiceListener;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import javax.security.auth.callback.Callback;


/**
 * Created by root on 05/01/16.
 */
public class FirebaseHandler {

    public static Firebase firebaseRef;
    private static FirebaseHandler ourInstance;
    private User user;
    public AuthData authData;

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
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        firebaseRef = new Firebase("https://fb-todolist.firebaseio.com/");
        authData = firebaseRef.getAuth();
    }

    public boolean getAuthStatus() {
        if (authData != null) {
            Log.i(Utilities.FirebaseTAG, "FB Handler - User Already authenticated..");
            return true;
        }
        else
            Log.i(Utilities.FirebaseTAG, "FB Handler - Session Expired..");
            return false;
    }

    public void firebaseLogin(String email, String password, final ServiceListener<AuthData,FirebaseError> listener){
        firebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Utilities.successToast("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                //If login successfull Intent for MAIN Activity......
                listener.success(authData);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error in login
                listener.error(firebaseError);
            }
        });
    }

    public void getLoginedUser(final ServiceListener<User,FirebaseError> listener) {Log.e("Logined User: ", "in");
        if (user == null) {
            FirebaseHandler.getInstance().firebaseRef.child("Users")
                    .child(authData.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("datasnapshot User: ", dataSnapshot.toString());

                            user = dataSnapshot.getValue(User.class);

                            Log.e("datasnapshot User: ", "Name: " + user.getName() +
                                    ", Email: " + user.getEmail() + ", Image: " + user.getImage());
                            listener.success(user);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Utilities.errorToast(firebaseError.toString());
                            listener.error(firebaseError);
                        }
                    });
        } else {
            listener.success(user);
        }
    }

    public Firebase getLoginedUserNode(){
        return FirebaseHandler.getInstance().firebaseRef.child("Users")
                .child(authData.getUid());
    }

    public Firebase getLoginedWallNode(){
        return FirebaseHandler.getInstance().firebaseRef.child("Wall")
                .child(authData.getUid());
    }

    public void getPosts(final ServiceListener<Post,FirebaseError> listener) {
        //returning the posts array list
        getLoginedWallNode().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("Post: ",dataSnapshot.toString());
                Post post = dataSnapshot.getValue(Post.class);
                listener.success(post);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.error(firebaseError);
            }
        });

    }
}

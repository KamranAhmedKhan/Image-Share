package com.crystalnet.imageshare.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.Util;
import com.cloudinary.utils.ObjectUtils;
import com.crystalnet.imageshare.Fragments.NewPostFragment;
import com.crystalnet.imageshare.Handlers.CloudinaryHandler;
import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.ServiceListener;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NM on 1/17/2016.
 */
public class Async extends AsyncTask<String, Void, HashMap<String, Object>> {

    private String url;
    String lstring;
    private User user;
    private HashMap<String, String> testpost;

    @Override
    protected HashMap<String, Object> doInBackground(String... params) {
        File file1 = new File(params[0]);
        Map wait = null;
        try {
               /* wait =*/
            return (HashMap<String, Object>) CloudinaryHandler.getInstance().getCloudinaryInstance().uploader().upload(file1, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
            return (HashMap<String, Object>) wait;
        }
    }

    @Override
    protected void onPostExecute(HashMap<String, Object> stringObjectHashMap) {
        super.onPostExecute(stringObjectHashMap);
        try {
            url = (String) stringObjectHashMap.get("url");
            Log.e("URL: ", url);
        } catch (Exception e) {

        }
try {

        FirebaseHandler.getInstance().getLoginedUser(new ServiceListener<User,FirebaseError>() {
            @Override
            public void success(User obj) {
                user = obj;
                if(!url.matches("")){
                    //post to firebase
                    testpost = new HashMap<String,String>();
                    testpost.put("name",user.getName());
                    testpost.put("image",url);
                    testpost.put("msg",NewPostFragment.desc.getText().toString());
                    testpost.put("time" ," - "+Utilities.Time());
                    testpost.put("date",Utilities.Date());

                    final Firebase local = FirebaseHandler.getInstance().getLoginedWallNode().push();
                    local.setValue(testpost);
                    lstring = local.getKey();
                    Log.e("New Post Key",lstring);
                    FirebaseHandler.getInstance().getLoginedUserNode().child("Posts").push().setValue(lstring);
                    FirebaseHandler.getInstance().getLoginedUserNode().child("Wall").push().setValue(lstring);

                    //Followers Wall
                    FirebaseHandler.getInstance().getLoginedUserNode().child("followers").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            FirebaseHandler.firebaseRef.child("Users").child(dataSnapshot.getKey()).child("Wall").push().setValue(lstring);
                            FirebaseHandler.firebaseRef.child("Wall").child(dataSnapshot.getKey()).child(lstring).setValue(testpost);
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

                        }
                    });
                }else{
                    Utilities.errorToast("Cant post without Details!");
                }
            }

            @Override
            public void error(FirebaseError firebaseError) {
                Utilities.errorToast(firebaseError.toString());
            }
        });
} catch (NullPointerException e) {
    Utilities.errorToast("Check your Network Connection!");
}
    }
}
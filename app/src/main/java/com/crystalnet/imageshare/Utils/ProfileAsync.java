package com.crystalnet.imageshare.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.utils.ObjectUtils;
import com.crystalnet.imageshare.Fragments.EditProfileFragment;
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
public class ProfileAsync extends AsyncTask<String, Void, HashMap<String, Object>> {

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
            return (HashMap<String, Object>) CloudinaryHandler.getInstance().getCloudinaryInstance()
                    .uploader().upload(file1, ObjectUtils.emptyMap());
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

        FirebaseHandler.getInstance().getLoginedUser(new ServiceListener<User,FirebaseError>() {
            @Override
            public void success(User obj) {
                user = obj;
                if(!url.matches("")){
                    //Update to firebase
                    FirebaseHandler.getInstance().getLoginedUserNode().child("image")
                            .setValue(url);
                    FirebaseHandler.getInstance().getLoginedUserNode().child("age")
                            .setValue(EditProfileFragment.edit_age.getText().toString());
                    FirebaseHandler.getInstance().getLoginedUserNode().child("location")
                            .setValue(EditProfileFragment.edit_location.getText().toString());
                    FirebaseHandler.getInstance().getLoginedUserNode().child("about")
                            .setValue(EditProfileFragment.edit_about.getText().toString());

                }else{
                    Utilities.errorToast("Unable to Update: Something went Wrong!..");
                }
            }

            @Override
            public void error(FirebaseError firebaseError) {
                Utilities.errorToast(firebaseError.toString());
            }
        });

    }
}
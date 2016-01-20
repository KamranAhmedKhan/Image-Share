package com.crystalnet.imageshare.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.utils.ObjectUtils;
import com.crystalnet.imageshare.Handlers.CloudinaryHandler;
import com.firebase.client.Firebase;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NM on 1/17/2016.
 */
public class Async extends AsyncTask<String, Void, HashMap<String, Object>> {

    private String url;

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
        } catch (Exception e) {

        }
        Log.d("Amazing", url);
//        Firebase messRef = fire.child("AppData").child("Conversations").child(userId).child(partnerId);
//        Firebase messRef2 = fire.child("AppData").child("Conversations").child(partnerId).child(userId);
//        messRef.push().setValue(new signature_msgs("", userId, date, url));
//        messRef2.push().setValue(new signature_msgs("", userId, date, url));
//        url = "N/A";
//        editText.setText("");
    }
}
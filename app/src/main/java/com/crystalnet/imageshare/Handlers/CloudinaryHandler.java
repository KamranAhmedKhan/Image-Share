package com.crystalnet.imageshare.Handlers;

/**
 * Created by root on 05/01/16.
 */
public class CloudinaryHandler {
    private static CloudinaryHandler ourInstance;

    private CloudinaryHandler() {
    }

    public static CloudinaryHandler getInstance() {
        if(ourInstance==null)
            ourInstance = new CloudinaryHandler();
        return ourInstance;
    }


    private void uploadImage(){

    }
}

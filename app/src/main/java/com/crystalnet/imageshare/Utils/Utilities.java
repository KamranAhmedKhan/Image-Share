package com.crystalnet.imageshare.Utils;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import android.content.Context;

/**
 * Created by root on 05/01/16.
 */
public class Utilities {
    public static String FirebaseTAG = "Firebase";
static Application a = new Application();
    public static void successToast(String msg){
        Toast.makeText(a.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    public static void errorToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}

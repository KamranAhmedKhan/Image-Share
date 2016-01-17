package com.crystalnet.imageshare.Utils;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import android.content.Context;

import com.crystalnet.imageshare.Activities.MainActivity;

/**
 * Created by root on 05/01/16.
 */
public class Utilities {
    public static String FirebaseTAG = "Firebase";
    public static void successToast(String msg){
        Toast.makeText(MainActivity.context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void errorToast(String msg){
        Toast.makeText(MainActivity.context,msg,Toast.LENGTH_SHORT).show();
    }
}

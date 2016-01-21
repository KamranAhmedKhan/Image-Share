package com.crystalnet.imageshare.Handlers;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.content.res.Configuration;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.widget.ImageView;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.Utils.Utilities;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 05/01/16.
 */
public class CloudinaryHandler {
    private static CloudinaryHandler ourInstance;
    Cloudinary cloudinary;

    private CloudinaryHandler() {
        cloudinary = new Cloudinary("cloudinary://573468729839527:k8qo3_8Ex_kPh-M9qkB8BJ3BxyQ@kamran");
    }

    public static CloudinaryHandler getInstance() {
        if(ourInstance==null)
            ourInstance = new CloudinaryHandler();
        return ourInstance;
    }

    public Cloudinary getCloudinaryInstance(){
        return this.cloudinary;
    }

    private void uploadImage(Bitmap bitmap){

    }
}

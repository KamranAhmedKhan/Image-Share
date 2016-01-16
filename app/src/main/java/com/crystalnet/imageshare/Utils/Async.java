package com.crystalnet.imageshare.Utils;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by NM on 1/17/2016.
 */
class Async extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... params) {
        Log.i("Async", "doInBackground" + params[1]);
        publishProgress(params[1]);

        return params[1];
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.i("Async", "onProgressUpdate \n"+values[0]);

    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        Log.i("Async", "onPostExecute\n"+aVoid);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("Async", "onPreExecute");
    }
}



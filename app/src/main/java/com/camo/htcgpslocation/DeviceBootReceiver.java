package com.camo.htcgpslocation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * @author Neel
 *         <p/>
 *         Broadcast reciever, starts when the device gets starts.
 *         Start your repeating alarm here.
 */
public class DeviceBootReceiver extends BroadcastReceiver {

    private static final String TAG = "DeviceBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            /* Setting the location request here */
            boolean locationUpdatesRequested = requestLocationUpdates(context);

            if (locationUpdatesRequested) {
            /* Setting the alarm here to dump requests to site */
                setupUploadService(context);
            }
        }
    }

    private void setupUploadService(Context context){

        Log.v(TAG, "Setting alarm");
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                BackendSettings.ALARM_INTERVAL,
                pendingAlarmIntent
        );
    }

    private boolean requestLocationUpdates(Context context){

        boolean success = false;

        Log.v(TAG, "Requesting location updates");

        Intent locationIntent = new Intent(context, LocationUpdatesBroadcastReceiver.class);
        locationIntent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        PendingIntent pendingLocationIntent = PendingIntent.getBroadcast(context, 0, locationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(BackendSettings.UPDATE_INTERVAL);
        locationRequest.setFastestInterval(BackendSettings.FASTEST_UPDATE_INTERVAL);
        locationRequest.setPriority(BackendSettings.REQUEST_PRIORITY);
        locationRequest.setMaxWaitTime(BackendSettings.MAX_WAIT_TIME);

        try {
            Utils.setRequestingLocationUpdates(context, true);
            fusedLocationClient.requestLocationUpdates(locationRequest, pendingLocationIntent);
            success = true;
        } catch (SecurityException e) {
            Utils.setRequestingLocationUpdates(context, false);
            e.printStackTrace();
        }

        return success;
    }
}
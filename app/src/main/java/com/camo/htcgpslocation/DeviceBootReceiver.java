package com.camo.htcgpslocation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            boolean success = false;

            Log.v("DeviceBootReceiver", "Requesting location updates");
            /* Setting the location request here */
            Intent locationIntent = new Intent(context, LocationUpdatesBroadcastReceiver.class);
            locationIntent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
            PendingIntent pendingLocationIntent = PendingIntent.getBroadcast(context, 0, locationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            LocationRequest locationRequest = new LocationRequest();

            locationRequest.setInterval(LocationRequestSettings.UPDATE_INTERVAL);
            locationRequest.setFastestInterval(LocationRequestSettings.FASTEST_UPDATE_INTERVAL);
            locationRequest.setPriority(LocationRequestSettings.REQUEST_PRIORITY);
            locationRequest.setMaxWaitTime(LocationRequestSettings.MAX_WAIT_TIME);

            try {
                Utils.setRequestingLocationUpdates(context, true);
                fusedLocationClient.requestLocationUpdates(locationRequest, pendingLocationIntent);
                success = true;
            } catch (SecurityException e) {
                Utils.setRequestingLocationUpdates(context, false);
                e.printStackTrace();
            }

            if (success) {
            /* Setting the alarm here */
                Log.v("DeviceBootReceiver", "Setting alarm");
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                manager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(),
                        LocationRequestSettings.ALARM_INTERVAL,
                        pendingAlarmIntent
                );
                Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
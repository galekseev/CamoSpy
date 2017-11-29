package com.camo.htcgpslocation;

/**
 * Created by GAlekseev on 24.11.2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("AlarmReceiver", "Alarm received");

        Intent uploadIntent = new Intent(context, UploadService.class);
        context.startService(uploadIntent);
        // For our recurring task, we'll just display a message
        //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }
}
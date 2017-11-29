package com.camo.htcgpslocation;

/**
 * Created by GAlekseev on 24.11.2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "Alarm received");

        Intent uploadIntent = new Intent(context, UploadService.class);
        context.startService(uploadIntent);
    }
}
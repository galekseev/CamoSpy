package com.camo.htcgpslocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlekseevGA on 24.11.2017.
 */

public class IncomingSmsReceiver extends BroadcastReceiver {

    private static final String TAG = "IncomingSmsReceiver";


    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "Sms incoming");

        boolean isLogOn = Utils.getRequestingLocationUpdates(context);

        Log.v(TAG, "Sms log: " + isLogOn);

        if (isLogOn) {

            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();

            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    ArrayList<SmsMessage> messageList = new ArrayList<SmsMessage>();

                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        messageList.add(currentMessage);
                    } // end for loop

                    SmsMessage[] smsMessages = messageList.toArray(new SmsMessage[messageList.size()]);

                    if (smsMessages.length > 0) {
                        Utils.sendSmsResults(context, smsMessages);
                    }
                } // bundle is null

            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" + e);

            }
        }
    }
}

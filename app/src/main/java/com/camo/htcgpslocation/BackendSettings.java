package com.camo.htcgpslocation;

import com.google.android.gms.location.LocationRequest;

import java.text.SimpleDateFormat;

/**
 * Created by AlekseevGA on 23.11.2017.
 */

public class BackendSettings {

    private static final int UPD_IN_MINUTES = 3;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL = UPD_IN_MINUTES * 60 * 1000 ;
    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    public static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    public static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 2; // 5 minutes
    /**
     * Request priority
     * PRIORITY_HIGH_ACCURACY - The finest location available.
     * PRIORITY_BALANCED_POWER_ACCURACY - Block level accuracy is considered to be about 100 meter accuracy. Using a coarse accuracy such as this often consumes less power.
     * PRIORITY_LOW_POWER - City level accuracy is considered to be about 10km accuracy. Using a coarse accuracy such as this often consumes less power.
     * PRIORITY_NO_POWER - No locations will be returned unless a different client has requested location updates in which case this request will act as a passive listener to those locations.
     */
    public static final int REQUEST_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;

    public static final int ALARM_INTERVAL = ((int)UPDATE_INTERVAL) * 4;

    final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}

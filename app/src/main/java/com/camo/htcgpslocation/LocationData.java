package com.camo.htcgpslocation;

/**
 * Created by GAlekseev on 24.11.2017.
 */

public class LocationData {

    public LocationData(String datetime, String latitude, String longitude) {
        Latitude = latitude;
        Longitude = longitude;
        Datetime = datetime;
    }

    public LocationData(String deviceId, /*String provider,*/ String datetime, String latitude, String longitude, String altitude, String accuracy) {
        //String format: deviceid;provider;datetime;latitude;longitude;altitude;accuracy
        Latitude = latitude;
        Longitude = longitude;
        Datetime = datetime;
        Accuracy = accuracy;
        Altitude = altitude;
//        Provider = provider;
        DeviceId = deviceId;
    }


    private String Latitude;
    private String Longitude;
    private String Datetime;
    private String Accuracy;
    private String Altitude;
//    private String Provider;
    private String DeviceId;

    public String getLatitude() { return Latitude; }
    public String getLongitude() { return Longitude; }
    public String getDatetime(){ return Datetime; }
    public String getAccuracy() { return Accuracy; }
    public String getAltitude() { return Altitude; }
//    public String getProvider() { return Provider; }
    public String getDeviceId() { return DeviceId; }
}

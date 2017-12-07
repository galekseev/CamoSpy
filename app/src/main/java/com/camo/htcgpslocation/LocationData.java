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

    public LocationData(String datetime, String latitude, String longitude, String accuracy, String altitude, String provider) {
        Latitude = latitude;
        Longitude = longitude;
        Datetime = datetime;
        Accuracy = accuracy;
        Altitude = altitude;
        Provider = provider;
    }


    private String Latitude;
    private String Longitude;
    private String Datetime;
    private String Accuracy;
    private String Altitude;
    private String Provider;

    public String getLatitude() {
        return Latitude;
    }
    public String getLongitude() {
        return Longitude;
    }
    public String getDatetime(){
        return Datetime;
    }
    public String getAccuracy() { return Accuracy; }
    public String getAltitude() { return Altitude; }
    public String getProvider() { return Provider; }
}

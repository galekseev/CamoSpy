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

    private String Latitude;
    private String Longitude;
    private String Datetime;

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getDatetime(){
        return Datetime;
    }

}

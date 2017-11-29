package com.camo.htcgpslocation;

public class LocationData {

    public LocationData(String datetime, String latitude, String longtitude)
    {
        Latitude = latitude;
        Longtitude = longtitude;
        Datetime = datetime;
    }

    public String Latitude;
    public String Longtitude;
    public String Datetime;
}

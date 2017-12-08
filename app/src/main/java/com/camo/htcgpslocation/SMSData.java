package com.camo.htcgpslocation;

/**
 * Created by AlekseevGA on 24.11.2017.
 */

public class SMSData {

    public SMSData(String deviceId, String datetime, String phoneNumber, String message){
        DeviceId = deviceId;
        Datetime = datetime;
        PhoneNumber = phoneNumber;
        Message = message;
    }

    public SMSData(String datetime, String phoneNumber, String message){
        PhoneNumber = phoneNumber;
        Message = message;
        Datetime = datetime;
    }

    private String PhoneNumber;
    private String Message;
    private String Datetime;
    private String DeviceId;

    public String getPhoneNumber(){
        return PhoneNumber;
    }
    public String getMessage(){
        return Message;
    }
    public String getDatetime(){
        return Datetime;
    }
    public String getDeviceId(){
        return DeviceId;
    }
}

package com.camo.htcgpslocation;

/**
 * Created by AlekseevGA on 24.11.2017.
 */

public class SMSData {

    public SMSData(String datetime, String phoneNumber, String message){
        PhoneNumber = phoneNumber;
        Message = message;
        Datetime = datetime;
    }

    private String PhoneNumber;
    private String Message;
    private String Datetime;

    public String getPhoneNumber(){
        return PhoneNumber;
    }

    public String getMessage(){
        return Message;
    }

    public String getDatetime(){
        return Datetime;
    }
}

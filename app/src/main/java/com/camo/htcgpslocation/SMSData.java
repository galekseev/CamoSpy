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

    public String PhoneNumber;
    public String Message;
    public String Datetime;
}

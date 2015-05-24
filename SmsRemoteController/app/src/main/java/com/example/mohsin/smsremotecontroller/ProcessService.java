package com.example.mohsin.smsremotecontroller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.mohsin.smsremotecontroller.SQLite.Database;
import com.example.mohsin.smsremotecontroller.SQLite.User_Admin;

import java.util.ArrayList;

public class ProcessService extends Service {

    private String Password=null;
    private String ActionPerform=null;
    private String SMS=null;
    private String Requester_No=null;
    private boolean already_registered=false;
    private boolean authenticated_user=false;
    private String criteria=null;

    public ProcessService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            this.SMS = extras.getString("SMS");
            this.Requester_No=extras.getString("requester_no");
            String []SplitMessage = SMS.split(" ");
            if(SplitMessage.length>2)
            {
                this.Password=SplitMessage[1];
                this.ActionPerform=SplitMessage[2];

                //Check Whether actionperform has arguments or not?
                String []arguments_check_arr = ActionPerform.split(":");
                if(arguments_check_arr.length==2)
                {
                    ActionPerform=arguments_check_arr[0];
                    criteria=arguments_check_arr[1];
                }
                else if(arguments_check_arr.length==1)
                {
                    ActionPerform=arguments_check_arr[0];
                }

            }
            else if(SplitMessage.length>1)
            {
                this.ActionPerform=SplitMessage[1];

                //Check Whether actionperform has arguments or not?
                String []arguments_check_arr = ActionPerform.split(":");
                if(arguments_check_arr.length==2)
                {
                    ActionPerform=arguments_check_arr[0];
                    criteria=arguments_check_arr[1];
                }
                else if(arguments_check_arr.length==1)
                {
                    ActionPerform=arguments_check_arr[0];
                }
            }
        }
        Database db=new Database(getApplicationContext());
        User_Admin admin_o=null;
        if(Password == null) //No password means we got to check whether he is already authenticated himself
        {
            already_registered=db.Verify(Requester_No);
        }
        else    //given password means we have to authenticate him and with that store user info if not exist
        {
            admin_o = db.getUser_Admin("Admin");
            if (admin_o.getPassword().equals(this.Password)) {
                authenticated_user = true;
                already_registered = db.Verify(Requester_No);
                if (!already_registered)
                {
                    admin_o.setPassword(this.Password);
                    admin_o.setPh_Number(Requester_No);
                    db.Save_Password(admin_o);
                    SendSms("You've been Authenticated by Our System, no need for you to re enter Password, New Format of Command:SRC [Command]",Requester_No);
                }
            }


        }
        if(authenticated_user||already_registered)
        {
            if (ActionPerform.equalsIgnoreCase("vibrate")) {
                Log.d("Vibrate", "Method: onStartCommand,Class:ProcessService");
                CommandProcessor.Vibrate(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();
                SendSms("Phone is Vibrating!",Requester_No);
            }
            else if (ActionPerform.equalsIgnoreCase("lock")) {
                Log.d("Lock", "Method: onStartCommand,Class:ProcessService");
                CommandProcessor.Lock_Screen(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Phone is Locked", Toast.LENGTH_LONG).show();
                SendSms("Phone is Locked!",Requester_No);
            }
            else if (ActionPerform.equalsIgnoreCase("silent_mode")) {
                Log.d("Silent_Mode", "Method: onStartCommand, Class:ProcessService");
                CommandProcessor.Silent_Mode(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Phone is in Silent Mode", Toast.LENGTH_LONG).show();
                SendSms("Phone is in Silent Mode!",Requester_No);
            }
            else if (ActionPerform.equalsIgnoreCase("ringer_mode")) {
                Log.d("Ringer_Mode", "Method: onStartCommand, Class:ProcessService");
                CommandProcessor.Ringer_Mode(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Phone is in Ringer Mode", Toast.LENGTH_LONG).show();
                SendSms("Phone is in Ringer Mode!", Requester_No);
            }
            else if (ActionPerform.equalsIgnoreCase("vibrate_mode")) {
                Log.d("Vibrate_Mode", "Method: onStartCommand, Class:ProcessService");
                CommandProcessor.Vibrate_Mode(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Phone is in Vibrate Mode", Toast.LENGTH_LONG).show();
                SendSms("Phone is in Vibrate Mode!", Requester_No);
            }
            else if (ActionPerform.equalsIgnoreCase("gps_coordinate")) {
                Log.d("Gps_Coordinate", "Method: onStartCommand, Class:ProcessService");
                double[] gps_coordinate = CommandProcessor.Gps_Coordinate(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Location: Latitude = " + gps_coordinate[0] + ", Longitude = " + gps_coordinate[1], Toast.LENGTH_LONG).show();
                SendSms("Location: Latitude = " + gps_coordinate[0] + ", Longitude = " + gps_coordinate[1], Requester_No);
            }
            else if (ActionPerform.equalsIgnoreCase("call_forward")) {
                Log.d("Call_Forward", "Method: onStartCommand, Class:ProcessService");
                String split_msg[] = SMS.split(" ");
                try {
                    String forwarding_number = split_msg[3];
                    CommandProcessor.Call_Forward(getApplicationContext(), forwarding_number);
                    //Toast.makeText(getApplicationContext(), "Call Forwarding Active to " + forwarding_number, Toast.LENGTH_LONG).show();
                    SendSms("Call Forwarding Active to " + forwarding_number, Requester_No);
                }
                catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), "Incorrect Number Parameter", Toast.LENGTH_LONG).show();
                    SendSms("Incorrect Number Parameter!", Requester_No);
                }


            }
            else if (ActionPerform.equalsIgnoreCase("wifi_on")) {
                Log.d("Wifi", "Method: onStartCommand, Class:ProcessService ");
                CommandProcessor.Wifi_On(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Wifi Enabled", Toast.LENGTH_LONG).show();
                SendSms("Wifi Enabled", Requester_No);

            }
            else if (ActionPerform.equalsIgnoreCase("wifi_off")) {
                Log.d("Wifi", "Method: onStartCommand, Class:ProcessService ");
                CommandProcessor.Wifi_Off(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Wifi Disabled", Toast.LENGTH_LONG).show();
                SendSms("Wifi Disabled", Requester_No);

            }
            else if (ActionPerform.equalsIgnoreCase("recording_start")) {
                Log.d("SoundRecording", "Method: onStartCommand, Class: ProcessService");
                CommandProcessor.SoundRecording(getApplicationContext(), true);
                SendSms("Sound Recording Started!", Requester_No);
            }
            else if (ActionPerform.equalsIgnoreCase("recording_stop")) {
                Log.d("SoundRecording", "Method: onStartCommand, Class: ProcessService");
                CommandProcessor.SoundRecording(getApplicationContext(), false);
                SendSms("Sound Recording Stopped!", Requester_No);
            }
            else if(ActionPerform.equalsIgnoreCase("help"))
            {
                Log.d("Help", "Method: onStartCommand, Class: ProcessService");
                String help=CommandProcessor.Help(getApplicationContext());
                SendSms(help, Requester_No);
            }
            else if(ActionPerform.equalsIgnoreCase("contacts"))
            {
                Log.d("Contacts","Method: onStartCommand,Class:ProcessService");
                if(criteria!=null)
                {
                    Log.d("Contacts","Method: onStartCommand,Class:ProcessService, SearchString: "+criteria);
                    String contacts_details=CommandProcessor.fetchContacts(getApplicationContext(),criteria);
                    //Toast.makeText(getApplicationContext(),contacts_details,Toast.LENGTH_LONG).show();
                    if(contacts_details.length()<10)
                    {
                        SendSms("No Such Name Found!",Requester_No);
                    }
                    else {
                        SendSms(contacts_details, Requester_No);
                    }
                }
                else
                {
                    SendSms("Please Enter Some Search Criteria!", Requester_No);
                }

            }
            else if (ActionPerform.equalsIgnoreCase("call_logs"))
            {
                Log.d("Call_Logs", "Method: onStartCommand,Class:ProcessService");
                String call_logs=null;

                if(criteria!=null)
                {
                    Log.d("Call_Logs","Method: onStartCommand,Class:ProcessService, SearchString: "+criteria);
                    call_logs=CommandProcessor.getCallLogs(getApplicationContext(), Integer.parseInt(criteria));
                    //Toast.makeText(getApplicationContext(),contacts_details,Toast.LENGTH_LONG).show();
                    if(call_logs.length()<10)
                    {
                        SendSms("No Such Name Found!",Requester_No);
                    }
                    else {
                        SendSms(call_logs, Requester_No);
                    }
                }
                else
                {
                    SendSms("Please Enter Some Search Criteria!", Requester_No);
                }

                Log.d("Call_Logs","These are the Logs:\n " + call_logs);
                SendSms(call_logs, Requester_No);

            }
            else if(ActionPerform.equalsIgnoreCase("unread_sms"))
            {
                Log.d("Unread_SMS", "Method: onStartCommand, Class:ProcessService");
                String unreadsms=CommandProcessor.fetchUnreadSMS(getApplicationContext());
                SendSms(unreadsms,Requester_No);
                Log.d("Unread_SMS","Method: onStartCommand, Class:ProcessService, Result: "+unreadsms);
            }
            else
            {
                //Toast.makeText(getApplicationContext(), "Wrong Command!", Toast.LENGTH_LONG).show();
                SendSms("Wrong Command!", Requester_No);
            }

            //Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();
        }
        else
        {
            //Toast.makeText(getApplicationContext(), "Please Authenticate Your Self First!", Toast.LENGTH_LONG).show();
            //Password is incorrect!
            SendSms("Wrong Authentication Details, Authenticate Your Self First!", Requester_No);
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void SendSms(String message,String phone_no)
    {
        Log.d("TestSMS","Method: sendSms, Class:ProcessService");
        SmsManager smsManager = SmsManager. getDefault();
        ArrayList<String> parts = smsManager.divideMessage(message);
        for (int i=0;i<parts.size();i++)
        {
            Log.d("TestSMS", "Part no: " + (i + 1) + " is: " + parts.get(i));
            smsManager.sendTextMessage(phone_no, null, parts.get(i), null, null);
        }

    }
}

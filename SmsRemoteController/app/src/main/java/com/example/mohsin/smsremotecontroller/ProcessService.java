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

public class ProcessService extends Service {

    private String Password=null;
    private String ActionPerform=null;
    private String SMS=null;
    private String Requester_No=null;

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
            }
        }
        Database db=new Database(getApplicationContext());
        User_Admin admin_o=db.getUser_Admin("Admin");
        if(admin_o.getPassword().equals(this.Password))
        {
            if (ActionPerform.equalsIgnoreCase("vibrate")) {
                Log.d("Vibrate", "Method: onStartCommand,Class:ProcessService");
                CommandProcessor.Vibrate(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();
            } else if (ActionPerform.equalsIgnoreCase("lock")) {
                Log.d("Lock", "Method: onStartCommand,Class:ProcessService");
                Toast.makeText(getApplicationContext(), "Phone is Locked", Toast.LENGTH_LONG).show();
                CommandProcessor.Lock_Screen(getApplicationContext());
                //Toast.makeText(getApplicationContext(), "Phone is Locked", Toast.LENGTH_LONG).show();
            } else if (ActionPerform.equalsIgnoreCase("silent_mode")) {
                Log.d("Silent_Mode", "Method: onStartCommand, Class:ProcessService");
                Toast.makeText(getApplicationContext(), "Phone is in Silent Mode", Toast.LENGTH_LONG).show();
                CommandProcessor.Silent_Mode(getApplicationContext());

            } else if (ActionPerform.equalsIgnoreCase("ringer_mode")) {
                Log.d("Ringer_Mode", "Method: onStartCommand, Class:ProcessService");
                Toast.makeText(getApplicationContext(), "Phone is in Ringer Mode", Toast.LENGTH_LONG).show();
                CommandProcessor.Ringer_Mode(getApplicationContext());
            } else if (ActionPerform.equalsIgnoreCase("vibrate_mode")) {
                Log.d("Vibrate_Mode", "Method: onStartCommand, Class:ProcessService");
                Toast.makeText(getApplicationContext(), "Phone is in Vibrate Mode", Toast.LENGTH_LONG).show();
                CommandProcessor.Vibrate_Mode(getApplicationContext());
            } else if (ActionPerform.equalsIgnoreCase("gps_coordinate")) {
                Log.d("Gps_Coordinate", "Method: onStartCommand, Class:ProcessService");
                double[] gps_coordinate = CommandProcessor.Gps_Coordinate(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Location: Latitude = " + gps_coordinate[0] + ", Longitude = " + gps_coordinate[1], Toast.LENGTH_LONG).show();
            } else if (ActionPerform.equalsIgnoreCase("call_forward")) {
                Log.d("Call_Forward", "Method: onStartCommand, Class:ProcessService");
                String split_msg[] = SMS.split(" ");
                try {
                    String forwarding_number = split_msg[3];
                    CommandProcessor.Call_Forward(getApplicationContext(), forwarding_number);
                    Toast.makeText(getApplicationContext(), "Call Forwarding Active to " + forwarding_number, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Incorrect Number Parameter", Toast.LENGTH_LONG).show();
                }


            } else if (ActionPerform.equalsIgnoreCase("wifi_on")) {
                Log.d("Wifi", "Method: onStartCommand, Class:ProcessService ");
                CommandProcessor.Wifi_On(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Wifi Enabled", Toast.LENGTH_LONG).show();

            } else if (ActionPerform.equalsIgnoreCase("wifi_off")) {
                Log.d("Wifi", "Method: onStartCommand, Class:ProcessService ");
                CommandProcessor.Wifi_Off(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Wifi Disabled", Toast.LENGTH_LONG).show();

            } else if (ActionPerform.equalsIgnoreCase("recording_start")) {
                Log.d("SoundRecording", "Method: onStartCommand, Class: ProcessService");
                CommandProcessor.SoundRecording(getApplicationContext(), true);
            } else if (ActionPerform.equalsIgnoreCase("recording_stop")) {
                Log.d("SoundRecording", "Method: onStartCommand, Class: ProcessService");
                CommandProcessor.SoundRecording(getApplicationContext(), false);
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Command", Toast.LENGTH_LONG).show();
            }

            //Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();
        }
        else
        {
            //Password is incorrect!
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void SendSms(String message,String phone_no)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone_no,null,message,null,null);
    }
}

package com.example.mohsin.smsremotecontroller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

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

        if(ActionPerform.equalsIgnoreCase("vibrate"))
        {
            Log.d("Vibrate","Method: onStartCommand,Class:ProcessService");
            CommandProcessor.Vibrate(getApplicationContext());
            Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();
        }
        else if(ActionPerform.equalsIgnoreCase("lock"))
        {
            Log.d("Lock", "Method: onStartCommand,Class:ProcessService");
            Toast.makeText(getApplicationContext(), "Phone is Locked", Toast.LENGTH_LONG).show();
            CommandProcessor.Lock_Screen(getApplicationContext());
            //Toast.makeText(getApplicationContext(), "Phone is Locked", Toast.LENGTH_LONG).show();
        }
        else if(ActionPerform.equalsIgnoreCase("silent_mode"))
        {
            Log.d("Silent_Mode", "Method: onStartCommand, Class:ProcessService");
            Toast.makeText(getApplicationContext(), "Phone is in Silent Mode", Toast.LENGTH_LONG).show();
            CommandProcessor.Silent_Mode(getApplicationContext());

        }
        else if(ActionPerform.equalsIgnoreCase("ringer_mode"))
        {
            Log.d("Ringer_Mode","Method: onStartCommand, Class:ProcessService");
            Toast.makeText(getApplicationContext(), "Phone is in Ringer Mode", Toast.LENGTH_LONG).show();
            CommandProcessor.Ringer_Mode(getApplicationContext());
        }
        else if(ActionPerform.equalsIgnoreCase("vibrate_mode"))
        {
            Log.d("Vibrate_Mode","Mode: onStartCommand, Class:ProcessService");
            Toast.makeText(getApplicationContext(), "Phone is in Vibrate Mode", Toast.LENGTH_LONG).show();
            CommandProcessor.Vibrate_Mode(getApplicationContext());
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Wrong Command", Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();



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

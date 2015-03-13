package com.example.mohsin.smsremotecontroller;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ProcessService extends Service {

    private String Password=null;
    private String ActionPerform=null;
    private String SMS=null;

    public ProcessService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            this.SMS = extras.getString("SMS");
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
            Log.d("Lock","Method: onStartCommand,Class:ProcessService");
            Toast.makeText(getApplicationContext(), "Phone is Locked", Toast.LENGTH_LONG).show();
            CommandProcessor.Lock_Screen(getApplicationContext());
            //Toast.makeText(getApplicationContext(), "Phone is Locked", Toast.LENGTH_LONG).show();
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
}

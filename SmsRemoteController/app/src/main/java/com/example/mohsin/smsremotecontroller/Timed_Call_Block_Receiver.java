package com.example.mohsin.smsremotecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

public class Timed_Call_Block_Receiver extends BroadcastReceiver {
    public Timed_Call_Block_Receiver() {
    }

static Boolean block;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b= intent.getExtras();
            if(intent.getAction().equals("Block_Call"))
            {           // it is my custom intent
                block=b.getBoolean("block");
      //  Toast.makeText(context,"value of block is "+block ,Toast.LENGTH_SHORT).show();
            }

        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(tm.getClass().getName());

            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
            c = Class.forName(telephonyService.
                              getClass().getName()); // Get its class


            if(block)
            {
                m = c.getDeclaredMethod("endCall"); // Get the "endCall()" method
                Log.i("Call_Status","Blocking Call");
                Toast.makeText(context,"Blocking Call ",Toast.LENGTH_SHORT).show();
                m.setAccessible(true); // Make it accessible
            }
            else
            {
                Log.i("Call_Status","UnBlocking Call");
                Toast.makeText(context,"UnBlocking Call ",Toast.LENGTH_SHORT).show();
                m.setAccessible(false); // Make it unaccessible
            }
            m.invoke(telephonyService); // invoke endCall()

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

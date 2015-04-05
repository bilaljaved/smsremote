package com.example.mohsin.smsremotecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Time_block_Receiver extends BroadcastReceiver {
    public Time_block_Receiver() {
    }
static Boolean block=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle b= intent.getExtras();
        String block_sms="Block_SMS";

        if(intent.getAction().equals(block_sms))
        {
            block=b.getBoolean("block");
    //        Toast.makeText(context,"In block function and value is "+block,Toast.LENGTH_SHORT).show();
        }
        if(block)
        {
            abortBroadcast();
            Log.i("SMS_Block","SMS Is Blocked");
            Toast.makeText(context,"blocking this sms ",Toast.LENGTH_SHORT).show();

        }
        else
        {
            this.getAbortBroadcast();
            Toast.makeText(context,"Unblock and get sms  ",Toast.LENGTH_SHORT).show();

        }
    }
}

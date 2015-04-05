package com.example.mohsin.smsremotecontroller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.format.Time;
import android.widget.Toast;
import  android.app.Notification.Builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fake_Sms_Receiver extends BroadcastReceiver {
    public Fake_Sms_Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //changing by waqar

        Toast.makeText(context, "Broadcast Intent Detected.",
                Toast.LENGTH_LONG).show();
        Time t=new Time();
        t.setToNow();
        //----------
        ContentValues values = new ContentValues();
        values.put("address", intent.getStringExtra("Phone"));
        values.put("body", intent.getStringExtra("Fake_SMS"));

        values.put("date", String.valueOf(t));              // time is not working

        context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
//------------------------------------------------------
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();

        //------------------------------------------------------
    }
}
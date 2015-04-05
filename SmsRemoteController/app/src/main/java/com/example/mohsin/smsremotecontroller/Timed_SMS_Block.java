package com.example.mohsin.smsremotecontroller;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Timed_SMS_Block extends Activity {
Timer timer;
    //TimerTask blocksms;
    TimerTask Unblocksms;

    long unblock_time;

    final Handler handler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed__sms__block);
    }

    private void start_timer() {
        timer=new Timer();
        unBlock_Sms();
        timer.schedule(Unblocksms,unblock_time);

    }

    private void unBlock_Sms() {

        Unblocksms=new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent("Block_SMS");
                        i.putExtra("block",false);
                        sendBroadcast(i);

                    }
                });

            }
        };

    }
    public void TimePicker(View v)
    {
            block();            // sms are blocked

        //----------------- geting time and unblock after this---------------------------
        TimePicker tp= (TimePicker)findViewById(R.id.timePicker2);
        android.text.format.Time t= new android.text.format.Time();
        t.setToNow();
        int hour=tp.getCurrentHour()- t.hour;
        int min= tp.getCurrentMinute()-t.minute;
        long hourmillisecond= TimeUnit.HOURS.toMillis(hour);
    //    Toast.makeText(this,"hour => "+hourmillisecond,Toast.LENGTH_SHORT ).show();
        long minmillisecond= TimeUnit.MINUTES.toMillis(min);
   //     Toast.makeText(this,"min => "+minmillisecond,Toast.LENGTH_SHORT ).show();
        //=-======--======================--=============================-=================
        unblock_time=hourmillisecond+minmillisecond;

        if(unblock_time<=0)
        {
            Toast.makeText(getApplicationContext(),"Value cannot be zero or lesser than zero ",
                    Toast.LENGTH_SHORT).show();
            unblock_time=5000  ;    // bu defualt it is 5 sec
        }
        start_timer();


    }

    void block()
    {
        Intent i = new Intent("Block_SMS");
        i.putExtra("block",true);
        sendBroadcast(i);
        Toast.makeText(this, "SMS are unblocked after : "+unblock_time/1000+" sec" , Toast.LENGTH_SHORT).show();
    }
    public void block(View view)
    {
       // start_timer();
            block();

        Toast.makeText(this, "SMS are blocked" , Toast.LENGTH_SHORT).show();

    }
    public void unblock(View view)
    {
        Intent i = new Intent("Block_SMS");
        i.putExtra("block",false);
        sendBroadcast(i);
    }
    public void Thirty(View v)
    {
        unblock_time=30000;
        block();
        start_timer();
    }

    public void Fourtyfive(View v)
    {
        unblock_time=45000;
        block();
        start_timer();
    }

    public void Hour(View v)
    {
        unblock_time=60000;
        block();
        start_timer();

    }

    public void Fifteen(View v)
    {
        unblock_time=15000;
        block();
        start_timer();

    }

}


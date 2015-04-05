package com.example.mohsin.smsremotecontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Timed_Call_Block extends Activity {
    Timer timer;
    TimerTask unblockcall;
    long unblock_time;
    final Handler handler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed__call__block);
    }


    public void TimePicker(View v)
    {
        block();            // sms are blocked

        //----------------- geting time and unblock after this---------------------------
        TimePicker tp= (TimePicker)findViewById(R.id.timePicker3);
        android.text.format.Time t= new android.text.format.Time();
        t.setToNow();
        int hour=tp.getCurrentHour()- t.hour;
        int min= tp.getCurrentMinute()-t.minute;
        long hourmillisecond= TimeUnit.HOURS.toMillis(hour);
       // Toast.makeText(this,"hour => "+hourmillisecond,Toast.LENGTH_SHORT ).show();
        long minmillisecond= TimeUnit.MINUTES.toMillis(min);
       // Toast.makeText(this,"min => "+minmillisecond,Toast.LENGTH_SHORT ).show();
        //=-======--======================--=============================-=================
        unblock_time=hourmillisecond+minmillisecond;

        Toast.makeText(this, "Call are unblocked after : "+unblock_time/1000 +" sec" , Toast.LENGTH_SHORT).show();
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

        Intent i = new Intent("Block_Call");
        i.putExtra("block", true);
        sendBroadcast(i);

    }
    public void block(View view)
    {
        block();

        Toast.makeText(this, "Call are blocked ", Toast.LENGTH_SHORT).show();

    }
    public void unblock(View view)
    {
        Intent i = new Intent("Block_Call");
        i.putExtra("block", false);
        sendBroadcast(i);
    }


    private void start_timer() {
        timer=new Timer();
        unBlock_Call();
        timer.schedule(unblockcall,unblock_time);

    }

    private void unBlock_Call() {

        unblockcall=new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {


                        Intent i = new Intent("Block_Call");
                        i.putExtra("block", false);
                        sendBroadcast(i);
                        //Toast.makeText(getApplicationContext(), "Call are unblocked ", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

    }



}

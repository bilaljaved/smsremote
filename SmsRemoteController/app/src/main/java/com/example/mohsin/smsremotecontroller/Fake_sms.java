package com.example.mohsin.smsremotecontroller;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


public class Fake_sms extends Activity {
    Timer timer;
    long Time;
    TimerTask sendsms;
    String Name="Sample Name";
    String Fake_SMS="Sample Fake SMS";
    String Phone="0333-1234567";

    final Handler Handler= new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_sms);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void start_timer() {
        timer=new Timer();
        Send_Fake_Sms();
        timer.schedule(sendsms,Time);

    }

    private void Send_Fake_Sms() {

        sendsms=new TimerTask() {
            @Override
            public void run() {

                Handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent("Fake_sms_broadcast");
                     //   i.putExtra("Name",Name);
                        i.putExtra("Phone",Phone);
                        i.putExtra("Fake_SMS",Fake_SMS);
                        //Toast.makeText(getApplicationContext(),Name+" "+Phone+" "+Fake_SMS,Toast.LENGTH_SHORT).show();
                        sendBroadcast(i);

                    }
                });

            }
        };

    }

    void getData()
    {
        if(Time<=0)
        {
            Toast.makeText(getApplicationContext(),"Value cannot be zero or lesser than zero ",
                    Toast.LENGTH_SHORT).show();
            Time=5000  ;    // bu defualt it is 5 sec
        }
       // Name= ( (EditText)findViewById(R.id.Fake_SMS_Name)).getText().toString();
        Phone= ( (EditText)findViewById(R.id.Fake_SMS_Num)).getText().toString();
        Fake_SMS= ( (EditText)findViewById(R.id.Fake_SMS_MSG)).getText().toString();
        start_timer();
        Toast.makeText(getApplicationContext(),"SMS will be recievd after "+Time/1000 +" sec",
                        Toast.LENGTH_SHORT).show();
    }
    public void Three(View v)
    {
        Time=3000;
        getData();
    }

    public void Five(View v)
    {
        Time=5000;
        getData();
    }

    public void Ten(View v)
    {
        Time=10000;
        getData();

    }

    public void Fifteen(View v)
    {
        Time=15000;
        getData();

    } public void TimePicker(View v)
    {
        TimePicker tp= (TimePicker)findViewById(R.id.Fake_Sms_timePicker);
        android.text.format.Time t= new Time();
        t.setToNow();

        int hour=tp.getCurrentHour()- t.hour;
        int min= tp.getCurrentMinute()-t.minute;

           long hourmillisecond= TimeUnit.HOURS.toMillis(hour);
       // Toast.makeText(this,"hour => "+hourmillisecond,Toast.LENGTH_SHORT ).show();
           long minmillisecond= TimeUnit.MINUTES.toMillis(min);
      //  Toast.makeText(this,"min => "+minmillisecond,Toast.LENGTH_SHORT ).show();

        Time=hourmillisecond+minmillisecond;


        getData();

    }



    public void Cancel(View v)
    {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);

    }
}


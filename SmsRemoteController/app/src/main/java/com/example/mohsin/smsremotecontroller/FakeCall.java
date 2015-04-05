package com.example.mohsin.smsremotecontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class FakeCall extends Activity {

    Timer time;
    TimerTask mytimertask;
    public final Handler handler= new Handler();
    String Name="Sample Name";
    String Phone="0333-8371390";
    long Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialize_fake_call);
    }

    void getData()
    {
        if(Time<=0)
        {
            Toast.makeText(getApplicationContext(),"Value cannot be zero or lesser than zero ",
                    Toast.LENGTH_SHORT).show();
            Time=5000  ;    // bu defualt it is 5 sec
        }
        Name=((EditText) findViewById( R.id.Fake_call_Name) ).getText().toString();
        Phone = ((EditText)findViewById(R.id.Fake_call_Phone) ).getText().toString();
        StartTime();
        Toast.makeText(getApplicationContext(),"Fake Call will be recieved after "+Time/1000 +" sec",
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

    }
    public void Main_Activity(View v)
    {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);

    }
    public void TimePicker(View v)
    {
        TimePicker tp= (TimePicker)findViewById(R.id.timePicker);
        android.text.format.Time t= new android.text.format.Time();
        t.setToNow();

        int hour=tp.getCurrentHour()- t.hour;
        int min= tp.getCurrentMinute()-t.minute;

        long hourmillisecond= TimeUnit.HOURS.toMillis(hour);
     //   Toast.makeText(this,"hour => "+hourmillisecond,Toast.LENGTH_SHORT ).show();
        long minmillisecond= TimeUnit.MINUTES.toMillis(min);
     //   Toast.makeText(this,"min => "+minmillisecond,Toast.LENGTH_SHORT ).show();

        Time=hourmillisecond+minmillisecond;


        getData();

    }

    public void StartTime() {
        time = new Timer();
        Task_To_perform();
        time.schedule(mytimertask, Time);
    }

public void Task_To_perform()
    {
        mytimertask=new TimerTask()
        {

            Context context=getApplicationContext();

            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(context,Recieve_Fake_Call.class);
                        i.putExtra("Fake_Call_Name",Name);
                        i.putExtra("Fake_Call_Num",Phone);
                        startActivity(i);


                    }
                });

            }
        };


    }



}

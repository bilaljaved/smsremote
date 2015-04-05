package com.example.mohsin.smsremotecontroller;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Recieve_Fake_Call extends Activity {

    Ringtone r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call);

        String Name = getIntent().getExtras().getString("Fake_Call_Name");
        String Phone = getIntent().getExtras().getString("Fake_Call_Num");
        TextView C_name = (TextView) findViewById(R.id.Contact_Name);
        TextView C_num = (TextView) findViewById(R.id.Contact_Number);
        C_name.setText(Name);
        C_num.setText(Phone);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
         r= RingtoneManager.getRingtone(this, notification);
        r.play();
    }
    public void Accept_Call(View view)
    {
        r.stop();
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);

    }

    public void Reject_Call(View view)
    {
        r.stop();
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }


}

package com.example.mohsin.smsremotecontroller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by Mohsin on 3/13/2015.
 */
public class VibrateService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        v.vibrate(2000);

        // If you want to vibrate  in a pattern
        //  long pattern[]={0,800,200,1200,300,2000,400,4000};
        // 2nd argument is for repetition pass -1 if you do not want to repeat the Vibrate
        // v.vibrate(pattern,-1);

        Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

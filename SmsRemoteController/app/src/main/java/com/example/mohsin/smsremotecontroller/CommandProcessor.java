package com.example.mohsin.smsremotecontroller;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Mohsin on 3/13/2015.
 */
public class CommandProcessor {

    private static final int ADMIN_INTENT = 15;
    private static final String description = "Administrator description";
    private static DevicePolicyManager mDevicePolicyManager;
    private static ComponentName mComponentName;

    static void Vibrate(Context context)
    {
        Log.d("Vibrate","Method: Vibrate, Class: CommandProcessor");
        Intent intentVibrate =new Intent(context,VibrateService.class);
        context.startService(intentVibrate);
    }
    static void Lock_Screen(Context context)
    {
        Log.d("Lock","Method: Lock_Screen, Class: CommandProcessor");
        mDevicePolicyManager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(context, AdminReceiver.class);

        try {
            mDevicePolicyManager.lockNow();
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Not Registered as admin", Toast.LENGTH_SHORT).show();
        }

        //}else {
          //  Toast.makeText(context, "Not Registered as admin", Toast.LENGTH_SHORT).show();
        //}
    }
    static void Silent_Mode(Context context)
    {
        Log.d("Silent_Mode","Method: Silent_Mode, Class: CommandProcessor");
        AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
    static void Ringer_Mode(Context context)
    {
        Log.d("Ringer_Mode","Method: Ringer_Mode, Class: CommandProcessor");
        AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
    static void Vibrate_Mode(Context context)
    {
        Log.d("Vibrate_Mode","Method: Vibrate_Mode, Class: CommandProcessor");
        AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

}

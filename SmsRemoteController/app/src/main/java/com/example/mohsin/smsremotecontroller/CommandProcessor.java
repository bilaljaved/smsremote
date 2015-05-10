package com.example.mohsin.smsremotecontroller;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.ContentHandler;
import java.util.List;


/**
 * Created by Mohsin on 3/13/2015.
 */
public class CommandProcessor {

    private static final int ADMIN_INTENT = 15;
    private static final String description = "Administrator description";
    private static DevicePolicyManager mDevicePolicyManager;
    private static ComponentName mComponentName;
    private static MediaRecorder myAudioRecorder;

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
    static double[] Gps_Coordinate(Context context)
    {
        Log.d("Location","Method: Gps_Coordinate, Class: CommandProcessor");

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
        Location l = null;

        for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }

        double[] gps = new double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;
    }
    static void Call_Forward(Context context, String number)
    {
        //Not Working...!!!!
        Log.d("Call_Forward","Method: Call_Forward, Class: CommandProcessor");

        String callForwardString = "**21*"+number+"#";

        Log.d("Call_Forward","Method: Call_Forward, Class: CommandProcessor");
        Intent intentCallForward = new Intent(Intent.ACTION_DIAL); // ACTION_CALL

        Log.d("Call_Forward","Method: Call_Forward, Class: CommandProcessor");
        Uri uri2 = Uri.fromParts("tel", callForwardString, "#");

        Log.d("Call_Forward","Method: Call_Forward, Class: CommandProcessor");
        intentCallForward.setData(uri2);

        Log.d("Call_Forward","Method: Call_Forward, Class: CommandProcessor");
        context.startActivity(intentCallForward);

        Log.d("Call_Forward","Method: Call_Forward, Class: CommandProcessor");
    }
    static void Wifi_On(Context context)
    {
        Log.d("Wifi","Method: Wifi_On, Class: CommandProcessor");

        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        mainWifiObj.setWifiEnabled(true);
    }
    static void Wifi_Off(Context context)
    {
        Log.d("Wifi","Method: Wifi_Off, Class: CommandProcessor");

        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        mainWifiObj.setWifiEnabled(false);
    }
    static void Gps_On(Context context)
    {
        Log.d("Location", "Method: Gps_Access, Class: CommandProcessor");

        LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }

    static void SoundRecording(Context context, boolean recording_status)
    {
        Log.d("AudioRecording","Method:SoundRecording, Class: CommandProcessor, Recording_Status = "+recording_status);

        String outputFile =null;

        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/SRC_recording.3gp";


        if(recording_status==true) //Start Recording
        {
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(outputFile);

            try {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Toast.makeText(context, "Recording started", Toast.LENGTH_LONG).show();
        }
        else    // Stop Recording
        {
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder  = null;
            Toast.makeText(context, "Audio recorded successfully",Toast.LENGTH_LONG).show();
        }
    }
    static String Help(Context context)
    {
        Log.d("Help", "Method: Help, Class: CommandProcessor");
        String help="Command List:\n" +
                "1. vibrate\n" +
                "2. lock\n" +
                "3. silent_mode\n" +
                "4. vibrate_mode\n" +
                "5. ringer_mode\n" +
                "6. gps_coordinate\n" +
                "7. call_forward [number]\n" +
                "8. wifi_on\n" +
                "9. wifi_off\n" +
                "10. recording_start\n" +
                "11. recording_stop\n" +
                "12. help";
        return help;
    }
}

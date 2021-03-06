package com.example.mohsin.smsremotecontroller;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.ContentHandler;
import java.sql.Date;
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

    static void Vibrate(Context context) {
        Log.d("Vibrate", "Method: Vibrate, Class: CommandProcessor");
        Intent intentVibrate = new Intent(context, VibrateService.class);
        context.startService(intentVibrate);
    }

    static void Lock_Screen(Context context) {
        Log.d("Lock", "Method: Lock_Screen, Class: CommandProcessor");
        mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(context, AdminReceiver.class);

        try {
            mDevicePolicyManager.lockNow();
        } catch (Exception e) {
            Toast.makeText(context, "Not Registered as admin", Toast.LENGTH_SHORT).show();
        }

        //}else {
        //  Toast.makeText(context, "Not Registered as admin", Toast.LENGTH_SHORT).show();
        //}
    }

    static void Silent_Mode(Context context) {
        Log.d("Silent_Mode", "Method: Silent_Mode, Class: CommandProcessor");
        AudioManager audiomanage = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    static void Ringer_Mode(Context context) {
        Log.d("Ringer_Mode", "Method: Ringer_Mode, Class: CommandProcessor");
        AudioManager audiomanage = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    static void Vibrate_Mode(Context context) {
        Log.d("Vibrate_Mode", "Method: Vibrate_Mode, Class: CommandProcessor");
        AudioManager audiomanage = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    static double[] Gps_Coordinate(Context context) {
        Log.d("Location", "Method: Gps_Coordinate, Class: CommandProcessor");

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
        Location l = null;

        for (int i = providers.size() - 1; i >= 0; i--) {
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

    static void Call_Forward(Context context, String number) {
        //Not Working...!!!!
        Log.d("Call_Forward", "Method: Call_Forward, Class: CommandProcessor");

        String callForwardString = "**21*" + number + "#";

        Log.d("Call_Forward", "Method: Call_Forward, Class: CommandProcessor");
        Intent intentCallForward = new Intent(Intent.ACTION_DIAL); // ACTION_CALL

        Log.d("Call_Forward", "Method: Call_Forward, Class: CommandProcessor");
        Uri uri2 = Uri.fromParts("tel", callForwardString, "#");

        Log.d("Call_Forward", "Method: Call_Forward, Class: CommandProcessor");
        intentCallForward.setData(uri2);

        Log.d("Call_Forward", "Method: Call_Forward, Class: CommandProcessor");
        context.startActivity(intentCallForward);

        Log.d("Call_Forward", "Method: Call_Forward, Class: CommandProcessor");
    }

    static void Wifi_On(Context context) {
        Log.d("Wifi", "Method: Wifi_On, Class: CommandProcessor");

        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        mainWifiObj.setWifiEnabled(true);
    }

    static void Wifi_Off(Context context) {
        Log.d("Wifi", "Method: Wifi_Off, Class: CommandProcessor");

        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        mainWifiObj.setWifiEnabled(false);
    }

    static void Gps_On(Context context) {
        Log.d("Location", "Method: Gps_Access, Class: CommandProcessor");

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    static void SoundRecording(Context context, boolean recording_status) {
        Log.d("AudioRecording", "Method:SoundRecording, Class: CommandProcessor, Recording_Status = " + recording_status);

        String outputFile = null;

        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/SRC_recording.3gp";


        if (recording_status == true) //Start Recording
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
        } else    // Stop Recording
        {
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
            Toast.makeText(context, "Audio recorded successfully", Toast.LENGTH_LONG).show();
        }
    }

    static String Help(Context context) {
        Log.d("Help", "Method: Help, Class: CommandProcessor");
        String help = "Command List: \n" +
                "1. vibrate\n" +
                "2. lock\n" +
                "3. silent_mode\n" +
                "4. vibrate_mode\n" +
                "5. ringer_mode\n" +
                "6. gps_coordinate\n" +
                "7. call_forward:number\n" +
                "8. wifi_on\n" +
                "9. wifi_off\n" +
                "10. recording_start\n" +
                "11. recording_stop\n" +
                "12. contacts:name\n" +
                "13. call_logs:number_of_records\n" +
                "14. unread_sms\n" +
                "15. help\n";
        return help;
    }

    public static String fetchContacts(Context context, String wildcard)
    {
        Log.d("Contact","Method: fetchContacts, Class: CommandProcessor, Wildcard: "+wildcard);
        String phoneNumber = null;
        String email = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    //Log.d("Contact","Name of Contact is: "+name);
                    if(name.toLowerCase().contains(wildcard.toLowerCase())) {
                        output.append("\n First Name:" + name);

                        // Query and loop for every phone number of the contact
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            output.append("\n Phone number:" + phoneNumber);
                        }
                        phoneCursor.close();
                    }

                    // Query and loop for every email of the contact
                    /*Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        output.append("\nEmail:" + email);
                    }
                    emailCursor.close();*/
                }
            }
            output.append("\n");
        }
        //Log.d("Contacts","Contact String at the end is:"+output.toString()+"And the length of msg is: "+output.length() );
        return output.toString();
    }
    public static String getCallLogs(Context context,int no_of_records)
    {
        @SuppressWarnings("deprecation")
        //Cursor managedCursor = contextmanagedQuery(CallLog.Calls.CONTENT_URI, null,null, null, null);
        StringBuffer sb = new StringBuffer();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Uri CONTENT_URI = CallLog.Calls.CONTENT_URI;


        int counter=0;

        Log.d("Call_Log_Test","Before resolver!");

        ContentResolver contentResolver = context.getContentResolver();
        Log.d("Call_Log_Test","After resolver!");
        Cursor managedCursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        Log.d("Call_Log_Test","After cursor resolver!");

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        sb.append("Call Log :");
        while (managedCursor.moveToNext() && counter<no_of_records) {
            String phNum = managedCursor.getString(number);
            String callTypeCode = managedCursor.getString(type);
            String strcallDate = managedCursor.getString(date);
            Date callDate = new Date(Long.valueOf(strcallDate));
            String callDuration = managedCursor.getString(duration);
            String callType = null;
            int callcode = Integer.parseInt(callTypeCode);
            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }
            sb.append("\nPhone Number:--- " + phNum + " \nCall Type:--- "
                    + callType + " \nCall Date:--- " + callDate
                    + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
        }
        managedCursor.close();

        return sb.toString();

    }
    public static String fetchUnreadSMS(Context context)
    {
        Uri sms_content = Uri.parse("content://sms/inbox");
        String where = "read = 0";
        StringBuffer result=new StringBuffer();
        Cursor c = context.getContentResolver().query(sms_content, null, where, null, null);
        c.moveToFirst();
        int counter = 1;
        while(c.moveToNext())
        {
            //result.append("First Column: "+c.getString(0)+", ");
            //result.append("Second Column: "+c.getString(1)+", ");
            result.append(counter+" : \n");
            result.append("Contact Number: "+c.getString(2)+", ");
            result.append("Content: "+c.getString(12)+"\n" );
            counter++;
            //result.append("3rd column: "+c.getString(13)+", " );

        }
        int Value = c.getCount();
        c.close();
        return result.toString();
    }
}

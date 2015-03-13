package com.example.mohsin.smsremotecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{

    final SmsManager sms= SmsManager.getDefault();
    String SMS;
    final String App_Key="SRC";

    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        final Bundle bundle = intent.getExtras();

        try{
            if (bundle!=null){

                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage currentMessage=null;

                for(int i=0;i<pdusObj.length;i++) {

                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                }
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    SMS = currentMessage.getDisplayMessageBody();

                    Log.i("SMSReceiver","SenderNum = "+senderNum+", Message = "+SMS);


                    String message_parts[]=SMS.split(" ");

                    if(message_parts[0].equals("SRC"))
                    {
                        Intent intent_for_service = new Intent(context,ProcessService.class);
                        intent_for_service.putExtra("SMS",SMS);
                        context.startService(intent_for_service);
                    }

                   /* //Toast toast = Toast.makeText(context,"SenderNum = "+senderNum+", Message = "+message,Toast.LENGTH_LONG);
                    //toast.show();
                    Toast.makeText(context, "Phone is Vibrating", Toast.LENGTH_LONG).show();*/

            }
        }
        catch (Exception e){
            Log.e("SMSReceiver","Exception SMS"+e);
        }
    }
}

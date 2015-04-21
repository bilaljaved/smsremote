package com.example.mohsin.smsremotecontroller.SharedPreferenceAccess;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Mohsin on 4/21/2015.
 */
public class AppStart {
    final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;

    public boolean AppRunStatus(Context context)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
            return true;
        }
        else
        {
            return false;
        }
    }
}

package com.example.mohsin.smsremotecontroller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohsin.smsremotecontroller.SQLite.Database;
import com.example.mohsin.smsremotecontroller.SQLite.User_Admin;
import com.example.mohsin.smsremotecontroller.SharedPreferenceAccess.AppStart;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppStart s=new AppStart();
        if(s.AppRunStatus(this))
        {
            setContentView(R.layout.initial_password);
            Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show();
        }
        else
        {
            setContentView(R.layout.activity_main);
            Toast.makeText(this,"Hello1",Toast.LENGTH_LONG).show();
        }

    }
    public void InitialPasswordSubmit(View view)
    {
        EditText password = (EditText)findViewById(R.id.password);
        EditText confirm_password=(EditText)findViewById(R.id.confirm_password);
        String pass=password.getText().toString();
        String c_pass=confirm_password.getText().toString();

        if(pass.equals(c_pass))
        {
            Toast.makeText(this,"Passwords are equal",Toast.LENGTH_LONG).show();
            User_Admin admin_object=new User_Admin("Admin",pass);
            Database db=new Database(this);
            db.Save_Password(admin_object);
            Toast.makeText(this,"Password Saved Successfully!",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main);
        }
        else
        {
            Toast.makeText(this,"Password and Confirm Password Fields do not Match!",Toast.LENGTH_LONG).show();
        }

    }
    public void LockScreenSetting(View view)
    {
        Intent intent =new Intent(this,LockScreenSetting.class);
        startActivity(intent);
    }
    public void FakeCall(View view)
    {
        Intent intent =new Intent(this,FakeCall.class);
        startActivity(intent);
    }
    public void FakeSMS(View view)
    {
        Intent intent =new Intent(this,Fake_sms.class);
        startActivity(intent);
    }
    public void TimedCallBlock(View view)
    {
        Intent intent =new Intent(this,Timed_Call_Block.class);
        startActivity(intent);
    }
    public void TimedSMSBlock(View view)
    {
        Intent intent =new Intent(this,Timed_SMS_Block.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.mohsin.smsremotecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohsin.smsremotecontroller.SQLite.Database;
import com.example.mohsin.smsremotecontroller.SQLite.User_Admin;


public class ChangePassword extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }
    public void Change_Password_Submit(View view)
    {
        EditText current_pass=(EditText) findViewById(R.id.current_password);
        EditText new_pass=(EditText)findViewById(R.id.new_password);
        EditText c_new_pass=(EditText)findViewById(R.id.c_new_password);

        //Getting Strings from EditTexts

        String s_current_pass=current_pass.getText().toString();
        String s_new_pass=new_pass.getText().toString();
        String s_c_new_pass=c_new_pass.getText().toString();

        Database db=new Database(this);

        User_Admin user_admin= db.getUser_Admin("Admin");
        if(user_admin.getPassword().equals(s_current_pass) && s_new_pass.equals(s_c_new_pass))
        {
            user_admin.setPassword(s_new_pass);
            db.UpdatePassword(user_admin);
            Toast.makeText(this,"Password Changed Successfully!",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Current Password is not currect or New Password Fields do not Match!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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

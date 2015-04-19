package com.example.mohsin.smsremotecontroller.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mohsin on 4/19/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="User_AdminDB";

    public Database(Context context) {
        // TODO Auto-generated constructor stub
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_ADMIN_TABLE = "CREATE TABLE User_Admin ( " +
                "Ph_Number Text PRIMARY KEY, " +
                "Password TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_ADMIN_TABLE);

        Log.d("SQLite","Class:Database, Method:onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        // TODO Auto-generated method stub
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User_Admin");
        this.onCreate(sqLiteDatabase);
        Log.d("SQLite","Class:Database, Method:onUpdate");

    }

    //Scores table name
    private static final String TABLE_User_Admin ="User_Admin";

    //Scores Table Columns names
    private static final String KEY_PH_NUMBER="Ph_Number";
    private static final String KEY_PASSWORD="Password";

    private static final String[] COLUMNS = {KEY_PH_NUMBER,KEY_PASSWORD};

    public void Save_Password (User_Admin user_admin)
    {
        Log.d("SQLite","Class:Database, Method:SavePassword");
        //1.get reference to writable Db
        SQLiteDatabase db=this.getReadableDatabase();

        //2.create ContentValues to add key "column" /value
        ContentValues values=new ContentValues();
        values.put(KEY_PH_NUMBER,user_admin.getPh_Number());
        values.put(KEY_PASSWORD,user_admin.getPassword());

        db.insert(TABLE_User_Admin, null, values);
        db.close();
    }

	public User_Admin getUser_Admin(String Ph_Number)
	{
        Log.d("SQLite","Class:Database, Method:getUser_Admin");
		//1.get reference to writable Db
		SQLiteDatabase db=this.getReadableDatabase();

		//2.build query
		Cursor cursor=db.query(TABLE_User_Admin, COLUMNS,"Ph_Number=?",  new String[]{String.valueOf(Ph_Number)}, null, null, null, null);

		//3. if we got result get the first one
		if(cursor!=null)
			cursor.moveToFirst();

		//4.build User_Admin object
		User_Admin user_admin =new User_Admin();
		user_admin.setPh_Number(cursor.getString(0));
	    user_admin.setPassword(cursor.getString(1));

		return user_admin;
	}
    public boolean Verify(String Ph_Number)
    {
        Log.d("SQLite","Class:Database, Method:Verify");
        if(getUser_Admin(Ph_Number)!=null)
        {
            return true;
        }
        else
            return false;

    }
    // Get All User_Admin
    public List<User_Admin> getAllUser_Admin()
    {
        Log.d("SQLite","Class:Database, Method:getAllUser_Admin");
        List<User_Admin> user_admins = new LinkedList<User_Admin>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_User_Admin;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
                   User_Admin user_admin = null;
        if (cursor.moveToFirst()) {
            do {
                user_admin = new User_Admin();
                user_admin.setPh_Number(cursor.getString(0));
                user_admin.setPassword(cursor.getString(1));

                // Add user_admin to user_admins
                user_admins.add(user_admin);
            } while (cursor.moveToNext());
        }

        // return user_admins
        return user_admins;
    }

    public int UpdatePassword(User_Admin user_admin)
    {
        Log.d("SQLite","Class:Database, Method:UpdatePassword");

        //1.get reference to writable DB
        SQLiteDatabase db=this.getReadableDatabase();

        //2.create ContentValues to add key "column"/value
        ContentValues values=new ContentValues();
        values.put("Ph_Number",user_admin.getPh_Number());
        values.put("Password",user_admin.getPassword());

        //3.Updating Password
        int i=db.update(TABLE_User_Admin,values,KEY_PH_NUMBER+" = ?",new String[]{String.valueOf(user_admin.getPh_Number())});
        db.close();
        return i;
    }
    //Deleting single User_Admin
    public void deleteUser_Admin(User_Admin user_admin)
    {
        Log.d("SQLite","Class:Database, Method:deleteUser_Admin");

        //1.get reference to writable DB
        SQLiteDatabase db=this.getWritableDatabase();

        //2.delete
        db.delete(TABLE_User_Admin,KEY_PH_NUMBER+" = ?",new String[]{String.valueOf(user_admin.getPh_Number())});

        db.close();

    }

}
package com.example.mohsin.smsremotecontroller.SQLite;

/**
 * Created by Mohsin on 4/19/2015.
 */
public class User_Admin {
    private String Ph_Number;
    private String Password;

    public User_Admin(){}
    public User_Admin(String Ph_Number,String Password)
    {
        this.Ph_Number=Ph_Number;
        this.Password=Password;
    }

    public String getPh_Number() {
        return Ph_Number;
    }

    public void setPh_Number(String ph_Number) {
        Ph_Number = ph_Number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "User_Admin{" +
                "Ph_Number='" + Ph_Number + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}

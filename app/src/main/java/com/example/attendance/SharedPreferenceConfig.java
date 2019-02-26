package com.example.attendance;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public static String PREFERENCE = "APP_PREF";
    //public static String LOGIN_CHECK = "LOGIN_CHECK";
    public static String Token = "Token";
    public static String Userid = "Userid";
    public static String UserName = "UserName";


    public SharedPreferenceConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setStringPref(String type, String value) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(type, value);
        editor.commit();
        editor.apply();
    }

    public String getStringPref(String type) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(type, null);
    }

    /*public void logOut() {
        // Launching News Feed Screen

        sharedPreferences= context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getEmail() {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email",null);

    }
 public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(LOGIN_CHECK, false);
    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_CHECK, status);
        editor.commit();
        editor.apply();
    }*/

}

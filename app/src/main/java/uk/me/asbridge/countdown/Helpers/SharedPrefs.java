package uk.me.asbridge.countdown.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AsbridgeD on 14-Dec-17.
 */

public class SharedPrefs {
    private final static String PREFS_LOCATION = "asbridge.me.uk.countdown";

    private static String getIDString(int id) {
        return "_"+Integer.toString(id);
    }

    public static String getMessageBefore(Context context, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        return sharedPref.getString("MESSAGE_BEFORE"+getIDString(widgetID), "days to go");
    }

    public static void setMessageBefore(Context context, String message, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = sharedPref.edit();
        settingsEditor.putString("MESSAGE_BEFORE"+getIDString(widgetID), message);
        settingsEditor.commit();
    }

    public static String getMessageAfter(Context context, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        return sharedPref.getString("MESSAGE_AFTER"+getIDString(widgetID), "days since");
    }

    public static void setMessageAfter(Context context, String message, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = sharedPref.edit();
        settingsEditor.putString("MESSAGE_AFTER"+getIDString(widgetID), message);
        settingsEditor.commit();
    }


    public static int getYear(Context context, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        return sharedPref.getInt("YEAR"+getIDString(widgetID), 2018);
    }

    public static void setYear(Context context, int year, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = sharedPref.edit();
        settingsEditor.putInt("YEAR"+getIDString(widgetID), year);
        settingsEditor.commit();
    }

    public static int getMonth(Context context, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        return sharedPref.getInt("MONTH"+getIDString(widgetID), 8);
    }

    public static void setMonth(Context context, int year, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = sharedPref.edit();
        settingsEditor.putInt("MONTH"+getIDString(widgetID), year);
        settingsEditor.commit();
    }

    public static int getDay(Context context, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        return sharedPref.getInt("DAY"+getIDString(widgetID), 6);
    }

    public static void setDay(Context context, int year, int widgetID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_LOCATION,Context.MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = sharedPref.edit();
        settingsEditor.putInt("DAY"+getIDString(widgetID), year);
        settingsEditor.commit();
    }

}

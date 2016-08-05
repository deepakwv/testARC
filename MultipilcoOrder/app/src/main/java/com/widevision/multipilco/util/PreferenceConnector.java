package com.widevision.multipilco.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceConnector {
    public static final String PREF_NAME = "DAGGLEDROP";
    public static final int MODE = Context.MODE_PRIVATE;
   public static String NO1="NO1";
   public static String NO2="NO2";
   public static String NO3="NO3";
   public static String NO4="NO4";
   public static String NO5="NO5";
   public static String NO6="NO6";
   public static String RELOAD="RELOAD";
   public static String SEQUENCE="SEQUENCE";

   public static String REPLAY_TAG="false";

   /* //>>>>in app purches

    public static final String PRODUCT_ARRAY = "PRODUCT_ARRAY";

    public static final String STATUS_PRODUCT = "STATUS_PRODUCT";
    public static final String FREE = "FREE";
    public static final String PAID = "PAID";
    public static String items[] = {"com.com.widevision.math_fan.set1"};*/
 //   public static String items[] = {"android.test.purchased"};

   

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeBoolean(Context context, String key, Boolean value) {
        getEditor(context).putBoolean(key, value).commit();

    }

    public static Boolean readBoolean(Context context, String key,Boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void clear(Context context) {
        getEditor(context).clear().commit();
    }


}

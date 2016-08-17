package com.angluswang.newsclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AnglusWang on 2016/8/17.
 * SharePreference 封装
 */

public class PrefUtils {

    private static final String PREF_NAME = "config";

    public static boolean getBoolean(Context context,
                                     String key, boolean defaultValue) {
        SharedPreferences sp =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }
}

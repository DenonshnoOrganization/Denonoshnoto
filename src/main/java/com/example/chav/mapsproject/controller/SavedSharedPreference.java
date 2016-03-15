package com.example.chav.mapsproject.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.chav.mapsproject.Message;

/**
 * Created by Chav on 3/6/2016.
 */
public class SavedSharedPreference {

    static final String PREF_USER_NAME = "username";

    static SharedPreferences getSharedPreference(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String username) {
        SharedPreferences.Editor editor = getSharedPreference(ctx).edit();
        editor.putString(PREF_USER_NAME, username);
        editor.commit();
    }

    public static String getUsername(Context ctx) {
        return getSharedPreference(ctx).getString(PREF_USER_NAME, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreference(ctx).edit();
        editor.clear();
        Message.message(ctx, "Cleared shared Preferences");
        editor.commit();
    }
}

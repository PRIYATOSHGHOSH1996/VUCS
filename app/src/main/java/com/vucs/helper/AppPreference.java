package com.vucs.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Aditya on 12-Jan-2018.
 */
public class AppPreference {

    private SharedPreferences prefs;

    public AppPreference(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public String getUserId() {
        return prefs.getString("uid", "");
    }

    public void setUserId(String uid) {
        prefs.edit().putString("uid", uid).apply();
    }

    public String getUserName() {
        return prefs.getString("uname", "");
    }

    public void setUserName(String uname) {
        prefs.edit().putString("uname", uname).apply();
    }

    public boolean isTokenGenerated() {
        return prefs.getBoolean("utoken", false);
    }

    public void setTokenGenerated(boolean tokenGenerated) {
        prefs.edit().putBoolean("utoken", tokenGenerated).apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }

}
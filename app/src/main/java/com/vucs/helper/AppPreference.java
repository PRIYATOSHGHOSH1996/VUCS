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

    public int getUserId() {
        return prefs.getInt("uid", -1);
    }

    public void setUserId(int uid) {
        prefs.edit().putInt("uid", uid).apply();
    }

    public void setUserName(String userName) {
        prefs.edit().putString("uname", userName).apply();
    }

    public String getUserName() {
        return prefs.getString("uname", "");
    }

    public void setUserEmail(String userName) {
        prefs.edit().putString("umail", userName).apply();
    }

    public String getUserEmail() {
        return prefs.getString("umail", "");
    }

    public void setUserPhoneNo(String userName) {
        prefs.edit().putString("upno", userName).apply();
    }

    public String getUserPhoneNo() {
        return prefs.getString("upno", "");
    }


    public void setUserImageUrl(String userName) {
        prefs.edit().putString("uimurl", userName).apply();
    }
    public String getUserImageUrl() {
        return prefs.getString("uimurl", "");
    }

    public String getUserAddress() {
        return prefs.getString("uaddr", "");
    }

    public void setUserAddress(String userName) {
        prefs.edit().putString("uaddr", userName).apply();
    }


    public String getUserDob() {
        return prefs.getString("udob", "");
    }

    public void setUserDob(String userName) {
        prefs.edit().putString("udob", userName).apply();
    }

    public String getUserCourse() {
        return prefs.getString("ucourse", "");
    }

    public void setUserCourse(String userName) {
        prefs.edit().putString("ucourse", userName).apply();
    }

    public String getUserBatch() {
        return prefs.getString("ubatch", "");
    }

    public void setUserBatch(String userName) {
        prefs.edit().putString("ubatch", userName).apply();
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
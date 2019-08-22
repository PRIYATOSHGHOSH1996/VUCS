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


    public void setUserSem(int uid) {
        prefs.edit().putInt("usemm", uid).apply();
    }

    public int getUserSem() {
        return prefs.getInt("usemm", -1);
    }



    public int getUserType() {
        return prefs.getInt("utype", -1);
    }

    public void setUserType(int uid) {
        prefs.edit().putInt("utype", uid).apply();
    }

    public void setUserName(String userName) {
        prefs.edit().putString("uname", userName).apply();
    }

    public String getUserName() {
        return prefs.getString("uname", "");
    }


    public void setUserPassword(String userName) {
        prefs.edit().putString("upass", userName).apply();
    }

    public String getPassword() {
        return prefs.getString("upass", "");
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
    public int getUserCourseCode() {
        return prefs.getInt("ucoursecode", 0);
    }

    public void setUserCourseCode(int userName) {
        prefs.edit().putInt("ucoursecode", userName).apply();
    }

    public String getUserBatch() {
        return prefs.getString("ubatch", "");
    }

    public void setUserBatch(String userName) {
        prefs.edit().putString("ubatch", userName).apply();
    }


    public void setForceLogout(boolean value){
        prefs.edit().putBoolean("force_logout", value).apply();
    }

    public boolean isForceLogout(){
        return prefs.getBoolean("force_logout", false);
    }

    public void setIsFirebaseTokenUpdated(boolean isSynced) {
        prefs.edit().putBoolean("is_firebase_token_synced", isSynced).apply();
    }
    public boolean getIsFirebaseTokenUpdated() {
        return prefs.getBoolean("is_firebase_token_synced", false);
    }

    public int getNotificationCount() {
        return prefs.getInt("uncount", 0);
    }

    public void setNotificationCount(int uid) {
        prefs.edit().putInt("uncount", uid).apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }

}
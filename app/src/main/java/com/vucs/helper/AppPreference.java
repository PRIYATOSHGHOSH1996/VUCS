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


    public void setUserFirstName(String userName) {
        prefs.edit().putString("ufrname", userName).apply();
    }

    public String getUserFirstName() {
        return prefs.getString("ufrname", "");
    }

    public void setUserLastName(String userName) {
        prefs.edit().putString("ulsname", userName).apply();
    }

    public String getUserLastName() {
        return prefs.getString("ulsname", "");
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

    public int getUserStartBatch() {
        return prefs.getInt("ustbatch", 0);
    }

    public void setUserStartBatch(int userName) {
        prefs.edit().putInt("ustbatch", userName).apply();
    }

    public int getUserEndBatch() {
        return prefs.getInt("uendbatch", 0);
    }

    public void setUserEndBatch(int userName) {
        prefs.edit().putInt("uendbatch", userName).apply();
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

    public boolean isFirebaseTopicSynced() {
        return prefs.getBoolean("firetopic", false);
    }
    public void setFirebaseTopicSynced(boolean isSynced) {
        prefs.edit().putBoolean("firetopic", isSynced).apply();
    }
    public boolean isFirebaseAllTopicSynced() {
        return prefs.getBoolean("firealltopic", false);
    }
    public void setFirebaseAllTopicSynced(boolean isSynced) {
        prefs.edit().putBoolean("firealltopic", isSynced).apply();
    }
    public boolean isFirebaseCurrentTopicSynced() {
        return prefs.getBoolean("firecurrenttopic", false);
    }
    public void setFirebaseCurrentTopicSynced(boolean isSynced) {
        prefs.edit().putBoolean("firecurrenttopic", isSynced).apply();
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
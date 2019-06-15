package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiClassNoticeModel {

    private ApiCredential apiCredential;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_name")
    private String username;

    @SerializedName("course")
    private int course;

    @SerializedName("sem")
    private int sem;

    @SerializedName("message")
    private String message;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;

    @SuppressLint("HardwareIds")
    public ApiClassNoticeModel(int userId, String username, int course, int sem, String message) {
        this.userId = userId;
        this.username = username;
        this.course = course;
        this.sem = sem;
        this.message = message;
        this.apiCredential = new ApiCredential();
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "ApiClassNoticeModel{" +
                "apiCredential=" + apiCredential +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", course=" + course +
                ", sem=" + sem +
                ", message='" + message + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

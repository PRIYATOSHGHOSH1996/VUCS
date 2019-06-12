package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiUploadFirebaseTokenModel {

    private ApiCredential apiCredential;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("token")
    private String token;

    @SerializedName("sem")
    private int sem;

    @SerializedName("category")
    private int category;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;

    @SuppressLint("HardwareIds")
    public ApiUploadFirebaseTokenModel(int userId, String token, int sem, int category) {
        this.userId = userId;
        this.token = token;
        this.sem = sem;
        this.category = category;
        this.apiCredential = new ApiCredential();
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public String toString() {
        return "ApiUploadFirebaseTokenModel{" +
                "apiCredential=" + apiCredential +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", sem=" + sem +
                ", category='" + category + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }
}

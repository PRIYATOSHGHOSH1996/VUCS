package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiForgotPasswordModel {

    private ApiCredential apiCredential;

    @SerializedName("user_name")
    private String username;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;

    @SuppressLint("HardwareIds")
    public ApiForgotPasswordModel(String username) {
        this.apiCredential = new ApiCredential();
        this.username = username;
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public String toString() {
        return "ApiLoginModel{" +
                "apiCredential=" + apiCredential +
                ", username='" + username + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

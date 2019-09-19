package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiLoginModel {

    private ApiCredential apiCredential;

    @SerializedName("user_name")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;

    @SuppressLint("HardwareIds")
    public ApiLoginModel(String username, String password) {
        this.apiCredential = new ApiCredential();
        this.username = username;
        this.password = password;
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public String toString() {
        return "ApiLoginModel{" +
                "apiCredential=" + apiCredential +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

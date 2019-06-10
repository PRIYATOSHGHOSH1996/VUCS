package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiChangePasswordModel {

    private ApiCredential apiCredential;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("old_password")
    private String oldPassword;

    @SerializedName("new_password")
    private String newPassword;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;

    @SuppressLint("HardwareIds")
    public ApiChangePasswordModel(int userId, String oldPassword,String newPassword) {
        this.apiCredential = new ApiCredential();
        this.userId = userId;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public String toString() {
        return "ApiChangePasswordModel{" +
                "apiCredential=" + apiCredential +
                ", userId=" + userId +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPpassword='" + newPassword + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

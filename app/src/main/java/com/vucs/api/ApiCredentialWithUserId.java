package com.vucs.api;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;
import com.vucs.helper.AppPreference;

import static com.vucs.App.getContext;

public class ApiCredentialWithUserId {

    private ApiCredential apiCredential;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;



    public ApiCredentialWithUserId() {
        Context context = getContext();
        AppPreference appPreference=new AppPreference(context);
        this.apiCredential = new ApiCredential();
        this.userId = appPreference.getUserId();
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public String toString() {
        return "ApiCredentialWithUserId{" +
                "apiCredential=" + apiCredential +
                ", userId=" + userId +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

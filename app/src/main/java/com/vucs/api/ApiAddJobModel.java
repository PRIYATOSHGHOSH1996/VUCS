package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiAddJobModel {

    private ApiCredential apiCredential;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("job_title")
    private String jobTitle;

    @SerializedName("job_description")
    private String jobDescription;



    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;


    @SuppressLint("HardwareIds")
    public ApiAddJobModel(String user_id, String jobTitle, String jobDescription) {
        this.user_id = user_id;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.apiCredential = new ApiCredential();
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public ApiCredential getApiCredential() {
        return apiCredential;
    }

    public void setApiCredential(ApiCredential apiCredential) {
        this.apiCredential = apiCredential;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ApiAddJobModel{" +
                "apiCredential=" + apiCredential +
                ", user_id=" + user_id +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

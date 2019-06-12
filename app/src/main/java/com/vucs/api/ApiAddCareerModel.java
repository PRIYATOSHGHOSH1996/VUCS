package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiAddCareerModel {

    private ApiCredential apiCredential;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("start_year")
    private int startYear;

    @SerializedName("end_year")
    private int EndYear;

    @SerializedName("occupation")
    private String occupation;


    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("device_id")
    private String deviceId;

    @SuppressLint("HardwareIds")
    public ApiAddCareerModel(int user_id, String companyName, int startYear, int endYear, String occupation) {
        this.user_id = user_id;
        this.companyName = companyName;
        this.startYear = startYear;
        EndYear = endYear;
        this.occupation = occupation;
        this.apiCredential = new ApiCredential();
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ApiAddCareerModel{" +
                "apiCredential=" + apiCredential +
                ", user_id=" + user_id +
                ", companyName='" + companyName + '\'' +
                ", startYear=" + startYear +
                ", EndYear=" + EndYear +
                ", occupation='" + occupation + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

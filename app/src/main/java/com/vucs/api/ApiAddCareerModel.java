package com.vucs.api;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.google.gson.annotations.SerializedName;
import com.vucs.BuildConfig;

import static com.vucs.App.getContext;

public class ApiAddCareerModel {

    private ApiCredential apiCredential;

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
    public ApiAddCareerModel(String companyName, int startYear, int endYear, String occupation) {
        this.companyName = companyName;
        this.startYear = startYear;
        EndYear = endYear;
        this.occupation = occupation;
        this.apiCredential = new ApiCredential();
        this.appVersion = BuildConfig.VERSION_NAME;
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public String toString() {
        return "ApiAddCareerModel{" +
                "apiCredential=" + apiCredential +
                ", companyName='" + companyName + '\'' +
                ", startYear=" + startYear +
                ", EndYear=" + EndYear +
                ", occupation='" + occupation + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

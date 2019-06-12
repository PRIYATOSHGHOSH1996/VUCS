package com.vucs.api;

import com.google.gson.annotations.SerializedName;

public class ApiAddJobResponseModel {
    private Integer code;
    private String message;

    @SerializedName("job_id")
    private int jobId;

    public ApiAddJobResponseModel(Integer code, String message, int jobId) {
        this.code = code;
        this.message = message;
        this.jobId = jobId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "ApiAddCareerResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", jobId=" + jobId +
                '}';
    }
}

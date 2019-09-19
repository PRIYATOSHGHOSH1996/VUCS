package com.vucs.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiAddJobFileWithResponseModel {
    private Integer code;
    private String message;

    @SerializedName("job_id")
    private int jobId;

    @SerializedName("file_url")
    private List<String> fileUrl;

    public ApiAddJobFileWithResponseModel(Integer code, String message, int jobId, List<String> fileUrl) {
        this.code = code;
        this.message = message;
        this.jobId = jobId;
        this.fileUrl = fileUrl;
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

    public List<String> getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(List<String> fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "ApiAddJobFileWithResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", jobId=" + jobId +
                ", fileUrl=" + fileUrl +
                '}';
    }
}

package com.vucs.api;

import com.google.gson.annotations.SerializedName;

public class ApiAddJobFileResponseModel {
    private Integer code;
    private String message;

    @SerializedName("file_url")
    private String fileUrl;

    public ApiAddJobFileResponseModel(Integer code, String message, String fileUrl) {
        this.code = code;
        this.message = message;
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


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "ApiAddCareerResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", fileUrl=" + fileUrl +
                '}';
    }
}

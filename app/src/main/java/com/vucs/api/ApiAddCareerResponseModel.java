package com.vucs.api;

import com.google.gson.annotations.SerializedName;

public class ApiAddCareerResponseModel {
    private Integer code;
    private String message;

    @SerializedName("career_id")
    private int careerId;

    public ApiAddCareerResponseModel(Integer code, String message, int careerId) {
        this.code = code;
        this.message = message;
        this.careerId = careerId;
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

    public int getCareerId() {
        return careerId;
    }

    public void setCareerId(int careerId) {
        this.careerId = careerId;
    }

    @Override
    public String toString() {
        return "ApiAddCareerResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", careerId=" + careerId +
                '}';
    }
}

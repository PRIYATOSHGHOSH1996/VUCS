package com.vucs.api;

import com.google.gson.annotations.SerializedName;

public class ApiLoginResponseModel {
    private Integer code;
    private String  message;

    @SerializedName("user_id")
    private Integer userId;


    @SerializedName("name")
    private String fullName;

    @SerializedName("category")
    private String userCategory;


    public ApiLoginResponseModel(Integer code, String message, Integer userId,String fullName, String userCategory) {
        this.code = code;
        this.message = message;
        this.userId = userId;
        this.fullName = fullName;
        this.userCategory = userCategory;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCode() {
        return code;
    }

    public void seuser_idtCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    @Override
    public String toString() {
        return "ApiLoginResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", userCategory='" + userCategory + '\'' +
                '}';
    }
}

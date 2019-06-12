package com.vucs.api;

import com.google.gson.annotations.SerializedName;

public class ApiLoginResponseModel {
    private Integer code;
    private String  message;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("category")
    private int type;

    @SerializedName("name")
    private String name;

    @SerializedName("mail")
    private String mail;

    @SerializedName("phone_number")
    private String phoneNo;

    @SerializedName("address")
    private String address;

    @SerializedName("image")
    private String image;

    @SerializedName("dob")
    private String dob;

    @SerializedName("course")
    private String course;

    @SerializedName("batch")
    private String batch;

    @SerializedName("sem")
    private int sem;


    public ApiLoginResponseModel(Integer code, String message, Integer userId, int type, String name, String mail, String phoneNo, String address, String image, String dob, String course, String batch, int sem) {
        this.code = code;
        this.message = message;
        this.userId = userId;
        this.type = type;
        this.name = name;
        this.mail = mail;
        this.phoneNo = phoneNo;
        this.address = address;
        this.image = image;
        this.dob = dob;
        this.course = course;
        this.batch = batch;
        this.sem = sem;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    @Override
    public String toString() {
        return "ApiLoginResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", dob='" + dob + '\'' +
                ", course='" + course + '\'' +
                ", batch='" + batch + '\'' +
                ", sem='" + sem + '\'' +
                '}';
    }
}

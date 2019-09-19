package com.vucs.api;

import com.google.gson.annotations.SerializedName;

public class ApiLoginResponseModel {
    private Integer code;
    private String  message;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("category")
    private int type;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

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

    @SerializedName("course_code")
    private int courseCode;

    @SerializedName("sem")
    private int sem;

    @SerializedName("start_batch")
    private int startBatch;

    @SerializedName("end_batch")
    private int endBatch;

    public ApiLoginResponseModel(Integer code, String message, String userId, int type, String firstName, String lastName, String mail, String phoneNo, String address, String image, String dob, String course, int courseCode, int sem, int startBatch, int endBatch) {
        this.code = code;
        this.message = message;
        this.userId = userId;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNo = phoneNo;
        this.address = address;
        this.image = image;
        this.dob = dob;
        this.course = course;
        this.courseCode = courseCode;
        this.sem = sem;
        this.startBatch = startBatch;
        this.endBatch = endBatch;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }


    public int getStartBatch() {
        return startBatch;
    }

    public void setStartBatch(int startBatch) {
        this.startBatch = startBatch;
    }

    public int getEndBatch() {
        return endBatch;
    }

    public void setEndBatch(int endBatch) {
        this.endBatch = endBatch;
    }

    @Override
    public String toString() {
        return "ApiLoginResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                ", type=" + type +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", dob='" + dob + '\'' +
                ", course='" + course + '\'' +
                ", courseCode=" + courseCode +
                ", sem=" + sem +
                ", startBatch=" + startBatch +
                ", endBatch=" + endBatch +
                '}';
    }
}

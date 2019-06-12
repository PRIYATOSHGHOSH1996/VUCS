package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "dt_users")
public class UserModel {

    @SerializedName("id")
    @PrimaryKey()
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    private String firstName;

    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    private String lastName;

    @SerializedName("mail")
    @ColumnInfo(name = "mail")
    private String mail;

    @SerializedName("phone_no")
    @ColumnInfo(name = "phone_no")
    private String phoneNo;

    @SerializedName("address")
    @ColumnInfo(name = "address")
    private String address;

    @SerializedName("batch_start_date")
    @ColumnInfo(name = "batch_start_date")
    private int batchStartDate;

    @SerializedName("batch_end_date")
    @ColumnInfo(name = "batch_end_date")
    private int batchEndDate;

    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @SerializedName("course")
    @ColumnInfo(name = "course")
    private String course;

    @SerializedName("dob")
    @ColumnInfo(name = "dob")
    private Date dob;



    public UserModel(int id, String firstName, String lastName, String mail, String phoneNo, String address, int batchStartDate, int batchEndDate, String imageUrl, String course, Date dob) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNo = phoneNo;
        this.address = address;
        this.batchStartDate = batchStartDate;
        this.batchEndDate = batchEndDate;
        this.imageUrl = imageUrl;
        this.course = course;
        this.dob = dob;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBatchStartDate() {
        return batchStartDate;
    }

    public void setBatchStartDate(int batchStartDate) {
        this.batchStartDate = batchStartDate;
    }

    public int getBatchEndDate() {
        return batchEndDate;
    }

    public void setBatchEndDate(int batchEndDate) {
        this.batchEndDate = batchEndDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", batchStartDate=" + batchStartDate +
                ", batchEndDate=" + batchEndDate +
                ", imageUrl='" + imageUrl + '\'' +
                ", course='" + course + '\'' +
                ", dob=" + dob +
                '}';
    }
}

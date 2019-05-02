package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;


public class PhirePawaModel implements Serializable {




    private String firstName;
    private String lastName;
    private Date batch;
    private String company;
    private String userImageURL;


    @Ignore
    public PhirePawaModel() {

    }

    public PhirePawaModel(String firstName, String lastName, Date batch, String company, String userImageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.batch = batch;
        this.company = company;
        this.userImageURL = userImageURL;
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

    public Date getBatch() {
        return batch;
    }

    public void setBatch(Date batch) {
        this.batch = batch;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    @Override
    public String toString() {
        return "PhirePawaModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", batch='" + batch + '\'' +
                ", company='" + company + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                '}';
    }
}

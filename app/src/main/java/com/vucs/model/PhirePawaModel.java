package com.vucs.model;


import androidx.room.Ignore;

import java.io.Serializable;
import java.util.Date;


public class PhirePawaModel implements Serializable {

    private int userId;
    private String firstName;
    private String lastName;
    private int batch;
    private String company;
    private String userImageURL;


    @Ignore
    public PhirePawaModel() {

    }

    public PhirePawaModel(int userId, String firstName, String lastName, int batch, String company, String userImageURL) {
        this.userId = userId;
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

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
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


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PhirePawaModel{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", batch=" + batch +
                ", company='" + company + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                '}';
    }
}

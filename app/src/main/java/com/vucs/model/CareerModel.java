package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "dt_career")
public class CareerModel {

    @SerializedName("id")
    @PrimaryKey()
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    private int userId;

    @SerializedName("start_date")
    @ColumnInfo(name = "start_date")
    private int startDate;

    @SerializedName("end_date")
    @ColumnInfo(name = "end_date")
    private int endDate;

    @SerializedName("company")
    @ColumnInfo(name = "company")
    private String company;

    @SerializedName("occupation")
    @ColumnInfo(name = "occupation")
    private String occupation;



    public CareerModel(int id, int userId, int startDate, int endDate, String company, String occupation) {
        this.id = id;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
        this.occupation = occupation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }



    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "CareerModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", company='" + company + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}

package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dt_career")
public class CareerModel {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "position")
    private String position;

    @ColumnInfo(name = "start_date")
    private int startDate;

    @ColumnInfo(name = "end_date")
    private int endDate;


    @ColumnInfo(name = "company")
    private String company;



    public CareerModel(int id, int userId, String position, int startDate, int endDate, String company) {
        this.id = id;
        this.userId = userId;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    @Override
    public String toString() {
        return "CareerModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", position='" + position + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", company='" + company + '\'' +
                '}';
    }
}

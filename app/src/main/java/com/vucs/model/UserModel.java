package com.vucs.model;


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "dt_user")
public class UserModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_name")
    private String name;

    @ColumnInfo(name = "user_batch")
    private Integer batch;

    @ColumnInfo(name = "user_company")
    private String company;


    @ColumnInfo(name = "user_image_url")
    private String userImageURL;



    @Ignore
    public UserModel() {

    }

    public UserModel(String name, Integer batch, String company,String userImageURL) {
        this.name = name;
        this.batch = batch;
        this.company = company;
        this.userImageURL = userImageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
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
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", batch=" + batch +
                ", company='" + company + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                '}';
    }
}

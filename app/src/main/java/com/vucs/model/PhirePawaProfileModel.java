package com.vucs.model;


import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "dt_phire_pawa")
public class PhirePawaProfileModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_name")
    private String name;

    @ColumnInfo(name = "user_batch")
    private String batch;

    @ColumnInfo(name = "user_company")
    private String company;


    @ColumnInfo(name = "user_image_url")
    private String userImageURL;


    @Ignore
    public PhirePawaProfileModel() {

    }

    public PhirePawaProfileModel(String name, String batch, String company, String userImageURL) {
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
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
        return "PhirePawaProfileModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", batch=" + batch +
                ", company='" + company + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                '}';
    }
}

package com.vucs.model;


import java.io.Serializable;



public class PhirePawaProfileModel implements Serializable {


    private int id;


    private String name;


    private Integer batch;


    private String company;



    private String userImageURL;




    public PhirePawaProfileModel() {

    }

    public PhirePawaProfileModel(int id, String name, Integer batch, String company, String userImageURL) {
        this.id = id;
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
        return "PhirePawaProfileModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", batch=" + batch +
                ", company='" + company + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                '}';
    }
}

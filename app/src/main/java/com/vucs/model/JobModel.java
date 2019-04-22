package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dt_job")
public class JobModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;

    @ColumnInfo(name = "job_id")
    private int jobId;

    @ColumnInfo(name = "job_title")
    private String jobTitle;

    @ColumnInfo(name = "job_by")
    private String jobBy;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "content")
    private String content;


    @Ignore
    public JobModel() {

    }

    public JobModel(int jobId, String jobTitle, String jobBy, Date date, String content) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobBy = jobBy;
        this.date = date;
        this.content = content;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobBy() {
        return jobBy;
    }

    public void setJobBy(String jobBy) {
        this.jobBy = jobBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "JobModel{" +
                "Id=" + Id +
                ", jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobBy='" + jobBy + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}

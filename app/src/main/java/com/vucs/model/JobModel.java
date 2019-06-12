package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "dt_job")
public class JobModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;

    @SerializedName("job_id")
    @ColumnInfo(name = "job_id")
    private int jobId;

    @SerializedName("job_title")
    @ColumnInfo(name = "job_title")
    private String jobTitle;

    @SerializedName("job_by_id")
    @ColumnInfo(name = "job_by_id")
    private int jobById;

    @SerializedName("job_by")
    @ColumnInfo(name = "job_by")
    private String jobBy;

    @SerializedName("date")
    @ColumnInfo(name = "date")
    private Date date;

    @SerializedName("content")
    @ColumnInfo(name = "content")
    private String content;

    @SerializedName("status")
    @ColumnInfo(name = "status")
    private int status;

    @Ignore
    public JobModel() {

    }

    public JobModel(int jobId, String jobTitle, int jobById, String jobBy, Date date, String content, int status) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobById = jobById;
        this.jobBy = jobBy;
        this.date = date;
        this.content = content;

        this.status = status;
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

    public int getJobById() {
        return jobById;
    }

    public void setJobById(int jobById) {
        this.jobById = jobById;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JobModel{" +
                "Id=" + Id +
                ", jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobById=" + jobById +
                ", jobBy='" + jobBy + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}

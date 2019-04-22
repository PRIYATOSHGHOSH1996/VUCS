package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dt_job")
public class JobModel {

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

    @ColumnInfo(name = "job_file_url_ids")
    private String jobFileURLIds;


    @Ignore
    public JobModel() {

    }

    public JobModel(int jobId, String jobTitle, String jobBy, Date date, String content, String jobFileURLIds) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobBy = jobBy;
        this.date = date;
        this.content = content;
        this.jobFileURLIds = jobFileURLIds;
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

    public String getJobFileURLIds() {
        return jobFileURLIds;
    }

    public void setJobFileURLIds(String jobFileURLIds) {
        this.jobFileURLIds = jobFileURLIds;
    }

    @Override
    public String toString() {
        return "JobModel{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobBy='" + jobBy + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", jobFileURLIds='" + jobFileURLIds + '\'' +
                '}';
    }
}

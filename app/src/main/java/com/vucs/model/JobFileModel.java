package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "dt_job_file")
public class JobFileModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;

    @ColumnInfo(name = "job_id")
    private int jobId;


    @ColumnInfo(name = "job_file_url")
    private String jobFileURL;


    @Ignore
    public JobFileModel() {

    }

    public JobFileModel(int jobId, String jobFileURL) {
        this.jobId = jobId;
        this.jobFileURL = jobFileURL;
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

    public String getJobFileURL() {
        return jobFileURL;
    }

    public void setJobFileURL(String jobFileURL) {
        this.jobFileURL = jobFileURL;
    }

    @Override
    public String toString() {
        return "JobFileModel{" +
                "Id=" + Id +
                ", jobId=" + jobId +
                ", jobFileURL='" + jobFileURL + '\'' +
                '}';
    }
}

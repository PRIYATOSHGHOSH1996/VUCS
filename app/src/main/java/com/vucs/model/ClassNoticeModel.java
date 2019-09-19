package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "dt_class_notice")
public class ClassNoticeModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int noticeId;

    @SerializedName("notice_title")
    @ColumnInfo(name = "notice_title")
    private String noticeTitle;

    @SerializedName("date")
    @ColumnInfo(name = "date")
    private Date date;

    @SerializedName("notice_by")
    @ColumnInfo(name = "notice_by")
    private String noticeBy;

    @SerializedName("sem")
    @ColumnInfo(name = "sem")
    private String sem;

    @SerializedName("file_url")
    @ColumnInfo(name = "file_url")
    private String fileUrl;


    @Ignore
    public ClassNoticeModel() {

    }

    public ClassNoticeModel(String noticeTitle, Date date, String noticeBy, String sem, String fileUrl) {
        this.noticeTitle = noticeTitle;
        this.date = date;
        this.noticeBy = noticeBy;
        this.sem = sem;
        this.fileUrl = fileUrl;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNoticeBy() {
        return noticeBy;
    }

    public void setNoticeBy(String noticeBy) {
        this.noticeBy = noticeBy;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "ClassNoticeModel{" +
                "noticeId=" + noticeId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", date=" + date +
                ", noticeBy='" + noticeBy + '\'' +
                ", sem='" + sem + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}

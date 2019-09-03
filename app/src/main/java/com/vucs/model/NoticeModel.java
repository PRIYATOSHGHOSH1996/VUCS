package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "dt_notice")
public class NoticeModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notice_id")
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

    @SerializedName("download_url")
    @ColumnInfo(name = "download_url")
    private String downloadURL;


    @ColumnInfo(name = "file_path")
    private String filePath="";

    @Ignore
    public NoticeModel() {

    }

    public NoticeModel(String noticeTitle, Date date, String noticeBy, String downloadURL) {
        this.noticeTitle = noticeTitle;
        this.date = date;
        this.noticeBy = noticeBy;
        this.downloadURL = downloadURL;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }


    public String getNoticeBy() {
        return noticeBy;
    }

    public void setNoticeBy(String noticeBy) {
        this.noticeBy = noticeBy;
    }

    @Override
    public String toString() {
        return "NoticeModel{" +
                "noticeId=" + noticeId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", date=" + date +
                ", noticeBy='" + noticeBy + '\'' +
                ", downloadURL='" + downloadURL + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}

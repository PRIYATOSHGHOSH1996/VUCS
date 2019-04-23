package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dt_class_notice")
public class ClassNoticeModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int noticeId;

    @ColumnInfo(name = "notice_title")
    private String noticeTitle;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "notice_by")
    private String noticeBy;


    @Ignore
    public ClassNoticeModel() {

    }

    public ClassNoticeModel(String noticeTitle, Date date, String noticeBy) {
        this.noticeTitle = noticeTitle;
        this.date = date;
        this.noticeBy = noticeBy;
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

    @Override
    public String toString() {
        return "NoticeModel{" +
                "noticeId=" + noticeId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", date=" + date +
                ", noticeBy='" + noticeBy + '\'' +
                '}';
    }
}

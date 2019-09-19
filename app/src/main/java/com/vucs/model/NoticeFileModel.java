package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "dt_notice_file")
public class NoticeFileModel {


    @SerializedName("notice_id")
    @PrimaryKey()
    @ColumnInfo(name = "notice_id")
    private int noticeId;


    @SerializedName("notice_file_uri")
    @ColumnInfo(name = "notice_file_uri")
    private String noticeFileURI;


    @Ignore
    public NoticeFileModel() {

    }

    public NoticeFileModel(int noticeId, String noticeFileURI) {
        this.noticeId = noticeId;
        this.noticeFileURI = noticeFileURI;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeFileURI() {
        return noticeFileURI;
    }

    public void setNoticeFileURI(String noticeFileURI) {
        this.noticeFileURI = noticeFileURI;
    }

    @Override
    public String toString() {
        return "NoticeFileModel{" +
                "noticeId=" + noticeId +
                ", noticeFileURI='" + noticeFileURI + '\'' +
                '}';
    }
}

package com.vucs.model;


import java.util.Date;




public class NoticeModel {


    private int noticeId;


    private String noticeTitle;


    private Long date;


    private String noticeBy;

    private String downloadURL;


    public NoticeModel() {

    }

    public NoticeModel(int noticeId, String noticeTitle, Long date, String noticeBy, String downloadURL) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.date = date;
        this.noticeBy = noticeBy;
        this.downloadURL = downloadURL;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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
                '}';
    }
}

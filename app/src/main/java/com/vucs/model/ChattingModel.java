package com.vucs.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "dt_chatting")
public class ChattingModel {

    @SerializedName("chatting_id")
    @PrimaryKey()
    @ColumnInfo(name = "chatting_id")
    private int chattingId;

    @SerializedName("message")
    @ColumnInfo(name = "message")
    private String message;

    @SerializedName("file_url")
    @ColumnInfo(name = "file_url")
    private String fileURL;

    @SerializedName("chatting_category")
    @ColumnInfo(name = "chatting_category")
    private int chattingCategory;

    @SerializedName("date")
    @ColumnInfo(name = "date")
    private Date date;

    @SerializedName("sender_id")
    @ColumnInfo(name = "sender_id")
    private String senderId;

    @SerializedName("teacher_id")
    @ColumnInfo(name = "teacher_id")
    private String teacherId;

    @SerializedName("sender_name")
    @ColumnInfo(name = "sender_name")
    private String senderName;

    @SerializedName("course")
    @ColumnInfo(name = "course")
    private int course;

    @SerializedName("sem")
    @ColumnInfo(name = "sem")
    private int sem;

@Ignore
    public ChattingModel() {
    }

    public ChattingModel(int chattingId, String message, String fileURL, int chattingCategory, Date date, String senderId, String teacherId, String senderName, int course, int sem) {
        this.chattingId = chattingId;
        this.message = message;
        this.fileURL = fileURL;
        this.chattingCategory = chattingCategory;
        this.date = date;
        this.senderId = senderId;
        this.teacherId = teacherId;
        this.senderName = senderName;
        this.course = course;
        this.sem = sem;
    }

    public int getChattingId() {
        return chattingId;
    }

    public void setChattingId(int chattingId) {
        this.chattingId = chattingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public int getChattingCategory() {
        return chattingCategory;
    }

    public void setChattingCategory(int chattingCategory) {
        this.chattingCategory = chattingCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    @Override
    public String toString() {
        return "ChattingModel{" +
                "chattingId=" + chattingId +
                ", message='" + message + '\'' +
                ", fileURL='" + fileURL + '\'' +
                ", chattingCategory=" + chattingCategory +
                ", date=" + date +
                ", senderId='" + senderId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", course=" + course +
                ", sem=" + sem +
                '}';
    }
}

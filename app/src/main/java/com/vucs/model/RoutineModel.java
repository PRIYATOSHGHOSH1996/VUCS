package com.vucs.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "dt_routine")
public class RoutineModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("day_no")
    @ColumnInfo(name = "day_no")
    private int dayNo;

    @SerializedName("start_time")
    @ColumnInfo(name = "start_time")
    private long startTime;

    @SerializedName("end_time")
    @ColumnInfo(name = "end_time")
    private long endTime;

    @SerializedName("teacher_id")
    @ColumnInfo(name = "teacher_id")
    private String teacherId;

    @SerializedName("subject")
    @ColumnInfo(name = "subject")
    private String subject;

    @SerializedName("course")
    @ColumnInfo(name = "course")
    private int course;

    @SerializedName("semester")
    @ColumnInfo(name = "sem")
    private int sem;

    @Ignore
    public RoutineModel() {
    }

    public RoutineModel(int dayNo, long startTime, long endTime, String teacherId, String subject, int course, int sem) {
        this.dayNo = dayNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacherId = teacherId;
        this.subject = subject;
        this.course = course;
        this.sem = sem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayNo() {
        return dayNo;
    }

    public void setDayNo(int dayNo) {
        this.dayNo = dayNo;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
        return "RoutineModel{" +
                "id='" + id + '\'' +
                ", dayNo=" + dayNo +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", teacherId='" + teacherId + '\'' +
                ", subject='" + subject + '\'' +
                ", course=" + course +
                ", sem=" + sem +
                '}';
    }
}

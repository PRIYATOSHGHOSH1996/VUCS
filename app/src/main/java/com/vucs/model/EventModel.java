package com.vucs.model;


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "dt_event")
public class EventModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    private int eventId;

    @ColumnInfo(name = "event_title")
    private String eventTitle;

    @ColumnInfo(name = "event_desc")
    private String eventDescription;

    @ColumnInfo(name = "date")
    private Date date;

    @Ignore
    public EventModel() {

    }

    public EventModel(String eventTitle, String eventDescription, Date date) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.date = date;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "eventId=" + eventId +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", date=" + date +
                '}';
    }
}

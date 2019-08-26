package com.vucs.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "dt_teacher")
public class TeacherModel {

    @SerializedName("id")
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id")
    private String id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    private String imageURL;

    @SerializedName("page_url")
    @ColumnInfo(name = "page_url")
    private String pageURL;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String description;


    @SerializedName("reply_count")
    @ColumnInfo(name = "reply_count")
    private int replyCount;

    @Ignore
    public TeacherModel() {
    }

    public TeacherModel(String id, String name, String imageURL, String pageURL, String description, int replyCount) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.pageURL = pageURL;
        this.description = description;
        this.replyCount = replyCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }


    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    @NonNull
    @Override
    public String toString() {
        return "TeacherModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", pageURL='" + pageURL + '\'' +
                ", description='" + description + '\'' +
                ", replyCount=" + replyCount +
                '}';
    }
}

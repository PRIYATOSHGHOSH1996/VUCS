package com.vucs.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "dt_blog")
public class BlogModel {


    @SerializedName("blog_id")
    @PrimaryKey()
    @ColumnInfo(name = "blog_id")
    private int blogId;

    @SerializedName("blog_title")
    @ColumnInfo(name = "blog_title")
    private String blogTitle;

    @SerializedName("blog_by_id")
    @ColumnInfo(name = "blog_by_id")
    private String blogById;

    @SerializedName("blog_by")
    @ColumnInfo(name = "blog_by")
    private String blogBy;

    @SerializedName("date")
    @ColumnInfo(name = "date")
    private Date date;

    @SerializedName("content")
    @ColumnInfo(name = "content")
    private String content;

    @SerializedName("blog_image_url")
    @ColumnInfo(name = "blog_image_url")
    private String blogImageURL;

    @SerializedName("status")
    @ColumnInfo(name = "status")
    private int status;
    @Ignore
    public BlogModel() {

    }

    public BlogModel(int blogId, String blogTitle, String blogById, String blogBy, Date date, String content, String blogImageURL, int status) {
        this.blogTitle = blogTitle;
        this.blogId = blogId;
        this.blogById = blogById;
        this.blogBy = blogBy;
        this.date = date;
        this.content = content;
        this.blogImageURL = blogImageURL;
        this.status = status;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogBy() {
        return blogBy;
    }

    public void setBlogBy(String blogBy) {
        this.blogBy = blogBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }



    public String getBlogImageURL() {
        return blogImageURL;
    }

    public void setBlogImageURL(String blogImageURL) {
        this.blogImageURL = blogImageURL;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getBlogById() {
        return blogById;
    }

    public void setBlogById(String blogById) {
        this.blogById = blogById;
    }

    @Override
    public String toString() {
        return "BlogModel{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogById=" + blogById +
                ", blogBy='" + blogBy + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", blogImageURL='" + blogImageURL + '\'' +
                ", status=" + status +
                '}';
    }
}

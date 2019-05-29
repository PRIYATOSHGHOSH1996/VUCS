package com.vucs.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dt_blog")
public class BlogModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "blog_id")
    private int blogId;

    @ColumnInfo(name = "blog_title")
    private String blogTitle;

    @ColumnInfo(name = "blog_by_id")
    private int blogById;

    @ColumnInfo(name = "blog_by")
    private String blogBy;


    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "blog_image_url")
    private String blogImageURL;

    @ColumnInfo(name = "status")
    private int status;
    @Ignore
    public BlogModel() {

    }

    public BlogModel(String blogTitle, int blogById, String blogBy, Date date, String content, String blogImageURL, int status) {
        this.blogTitle = blogTitle;
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


    public int getBlogById() {
        return blogById;
    }

    public void setBlogById(int blogById) {
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

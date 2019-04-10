package com.vucs.model;



public class BlogModel {


    private int blogId;


    private String blogTitle;


    private String blogBy;


    private Long date;

    private String content;


    private String blogImageURL;


    public BlogModel() {

    }

    public BlogModel(Integer id,String blogTitle, String blogBy, Long date, String content, String blogImageURL) {
        this.blogTitle = blogTitle;
        this.blogBy = blogBy;
        this.date = date;
        this.content = content;
        this.blogImageURL = blogImageURL;
        this.blogId =id;

    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogBy() {
        return blogBy;
    }

    public void setBlogBy(String blogBy) {
        this.blogBy = blogBy;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlogImageURL() {
        return blogImageURL;
    }

    public void setBlogImageURL(String blogImageURL) {
        this.blogImageURL = blogImageURL;
    }

    @Override
    public String toString() {
        return "BlogModel{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogBy='" + blogBy + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", blogImageURL='" + blogImageURL + '\'' +
                '}';
    }
}

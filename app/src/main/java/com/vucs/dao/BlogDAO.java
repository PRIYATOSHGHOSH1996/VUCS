package com.vucs.dao;



import com.vucs.model.BlogModel;

import java.util.List;

public interface BlogDAO {
    public void insertBlog(BlogModel blogModel);

    public void addBlogs(List<BlogModel> blogModels);

    public List<BlogModel> getAllBlog();


}

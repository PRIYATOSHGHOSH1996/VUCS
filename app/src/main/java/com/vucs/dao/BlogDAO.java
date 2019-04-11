package com.vucs.dao;

import com.vucs.model.BlogModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BlogDAO {


    @Query("SELECT * FROM dt_blog")
    public List<BlogModel> getAllBlog();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertBlog(BlogModel blogModel);
}

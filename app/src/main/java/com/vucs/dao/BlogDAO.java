package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.BlogModel;

import java.util.List;

@Dao
public interface BlogDAO {


    @Query("SELECT * FROM dt_blog WHERE status = 1 ORDER BY date")
    public List<BlogModel> getAllBlog();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertBlog(BlogModel blogModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertBlog(List<BlogModel> blogModels);

    @Query("DELETE FROM dt_blog")
    public void deleteAllBlog();
}

package com.vucs.dao;

import com.vucs.model.BlogModel;
import com.vucs.model.ImageGalleryModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ImageGalleryDAO {


    @Query("SELECT * FROM dt_image_gallery")
    public LiveData<List<ImageGalleryModel>> getAllImages();

    @Query("SELECT * FROM dt_image_gallery WHERE folder_name = :folderName")
    public LiveData<List<ImageGalleryModel>> getAllImagesByFolder(String folderName);

    @Query("SELECT folder_name FROM dt_image_gallery GROUP BY folder_name")
    public LiveData<List<String>> getAllFolders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertImage(ImageGalleryModel imageGalleryModel);
}

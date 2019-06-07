package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.ImageGalleryModel;

import java.util.List;

@Dao
public interface ImageGalleryDAO {


    @Query("SELECT * FROM dt_image_gallery")
    public List<ImageGalleryModel> getAllImages();

    @Query("DELETE FROM dt_image_gallery")
    public void deleteAllImages();

    @Query("SELECT * FROM dt_image_gallery WHERE folder_name = :folderName")
    public List<ImageGalleryModel> getAllImagesByFolder(String folderName);

    @Query("SELECT * FROM dt_image_gallery WHERE folder_name = :folderName LIMIT 1")
    public ImageGalleryModel getFirstImagesByFolder(String folderName);

    @Query("SELECT folder_name FROM dt_image_gallery GROUP BY folder_name")
    public List<String> getAllFolders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertImage(ImageGalleryModel imageGalleryModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertImage(List<ImageGalleryModel> imageGalleryModel);
}

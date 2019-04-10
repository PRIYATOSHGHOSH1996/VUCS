package com.vucs.dao;

import com.vucs.model.ImageGalleryModel;

import java.util.List;



public interface ImageGalleryDAO {


    public List<ImageGalleryModel> getAllImages();

    public List<ImageGalleryModel> getAllImagesByFolder(String folderName);


    public List<String> getAllFolders();

    public void insertImage(ImageGalleryModel imageGalleryModel);
}

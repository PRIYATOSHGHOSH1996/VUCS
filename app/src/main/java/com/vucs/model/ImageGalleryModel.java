package com.vucs.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "dt_image_gallery")
public class ImageGalleryModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")
    private int imageId;

    @ColumnInfo(name = "folder_name")
    private String folderName;

    @ColumnInfo(name = "thumb_url")
    private String thumbURL;

    @ColumnInfo(name = "image_url")
    private String imageURL;

    @Ignore
    public ImageGalleryModel() {

    }

    public ImageGalleryModel(String folderName, String thumbURL, String imageURL) {
        this.folderName = folderName;
        this.thumbURL = thumbURL;
        this.imageURL = imageURL;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    @Override
    public String toString() {
        return "ImageGalleryModel{" +
                "imageId=" + imageId +
                ", folderName='" + folderName + '\'' +
                ", thumbURL='" + thumbURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}

package com.vucs.model;





public class ImageGalleryModel {

    private int imageId;

    private String folderName;


    private String imageURL;


    public ImageGalleryModel() {

    }

    public ImageGalleryModel(int imageId, String folderName, String imageURL) {
        this.imageId = imageId;
        this.folderName = folderName;
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

    @Override
    public String toString() {
        return "ImageGalleryModel{" +
                "imageId=" + imageId +
                ", folderName='" + folderName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}

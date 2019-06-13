package com.vucs.api;




import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiImageUpdateModel {

    private List<ImageGalleryModel> imageGalleryModels;

    public ApiImageUpdateModel(
                               List<ImageGalleryModel> imageGalleryModels) {
        this.imageGalleryModels = imageGalleryModels;
    }


    public List<ImageGalleryModel> getImageGalleryModels() {
        return imageGalleryModels;
    }

    public void setImageGalleryModels(List<ImageGalleryModel> imageGalleryModels) {
        this.imageGalleryModels = imageGalleryModels;
    }


    @Override
    public String toString() {
        return "ApiImageUpdateModel{" +
                "imageGalleryModels=" + imageGalleryModels +
                '}';
    }
}

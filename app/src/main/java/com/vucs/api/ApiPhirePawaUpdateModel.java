package com.vucs.api;




import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiPhirePawaUpdateModel {

    private List<UserModel> userModels;

    public ApiPhirePawaUpdateModel( List<UserModel> userModels) {

        this.userModels = userModels;
    }


    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }


    @Override
    public String toString() {
        return "ApiPhirePawaUpdateModel{" +
                ", userModels=" + userModels +
                '}';
    }
}

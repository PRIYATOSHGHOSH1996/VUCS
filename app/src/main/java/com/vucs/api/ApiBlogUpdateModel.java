package com.vucs.api;




import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiBlogUpdateModel {

    private List<BlogModel> blogModels;

    public ApiBlogUpdateModel(List<BlogModel> blogModels) {
        this.blogModels = blogModels;

    }



    public List<BlogModel> getBlogModels() {
        return blogModels;
    }

    public void setBlogModels(List<BlogModel> blogModels) {
        this.blogModels = blogModels;
    }

    @Override
    public String toString() {
        return "ApiUpdateModel{" +
                "blogModels=" + blogModels +
                '}';
    }
}

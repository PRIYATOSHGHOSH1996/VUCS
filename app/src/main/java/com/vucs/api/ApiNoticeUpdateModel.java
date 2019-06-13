package com.vucs.api;




import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiNoticeUpdateModel {

    private List<NoticeModel> noticeModels;

    public ApiNoticeUpdateModel( List<NoticeModel> noticeModels) {
        this.noticeModels = noticeModels;
    }



    public List<NoticeModel> getNoticeModels() {
        return noticeModels;
    }

    public void setNoticeModels(List<NoticeModel> noticeModels) {
        this.noticeModels = noticeModels;
    }


    @Override
    public String toString() {
        return "ApiNoticeUpdateModel{" +
                "noticeModels=" + noticeModels +
                '}';
    }
}

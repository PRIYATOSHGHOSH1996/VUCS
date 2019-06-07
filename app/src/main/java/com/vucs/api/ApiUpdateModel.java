package com.vucs.api;




import com.vucs.model.CareerModel;
import com.vucs.model.EventModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiUpdateModel {

    private List<CareerModel> careerModels;
    private List<EventModel> eventModels;
    private List<ImageGalleryModel> imageGalleryModels;
    private List<JobFileModel> jobFileModels;
    private List <JobModel> jobModels;
    private List<NoticeModel> noticeModels;
    private List<UserModel> userModels;

    public ApiUpdateModel( List<CareerModel> careerModels,
             List<EventModel> eventModels,
             List<ImageGalleryModel> imageGalleryModels,
             List<JobFileModel> jobFileModels,
             List <JobModel> jobModels,
             List<NoticeModel> noticeModels,
             List<UserModel> userModels) {
        this.careerModels = careerModels;
        this.eventModels = eventModels;
        this.imageGalleryModels = imageGalleryModels;
        this.jobFileModels = jobFileModels;
        this.jobModels = jobModels;
        this.noticeModels = noticeModels;
        this.userModels = userModels;
    }

    public List<CareerModel> getCareerModels() {
        return careerModels;
    }

    public void setCareerModels(List<CareerModel> careerModels) {
        this.careerModels = careerModels;
    }

    public List<EventModel> getEventModels() {
        return eventModels;
    }

    public void setEventModels(List<EventModel> eventModels) {
        this.eventModels = eventModels;
    }

    public List<ImageGalleryModel> getImageGalleryModels() {
        return imageGalleryModels;
    }

    public void setImageGalleryModels(List<ImageGalleryModel> imageGalleryModels) {
        this.imageGalleryModels = imageGalleryModels;
    }

    public List<JobFileModel> getJobFileModels() {
        return jobFileModels;
    }

    public void setJobFileModels(List<JobFileModel> jobFileModels) {
        this.jobFileModels = jobFileModels;
    }

    public List<JobModel> getJobModels() {
        return jobModels;
    }

    public void setJobModels(List<JobModel> jobModels) {
        this.jobModels = jobModels;
    }

    public List<NoticeModel> getNoticeModels() {
        return noticeModels;
    }

    public void setNoticeModels(List<NoticeModel> noticeModels) {
        this.noticeModels = noticeModels;
    }

    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }

    @Override
    public String toString() {
        return "ApiUpdateModel{" +
                "careerModels=" + careerModels +
                ", eventModels=" + eventModels +
                ", imageGalleryModels=" + imageGalleryModels +
                ", jobFileModels=" + jobFileModels +
                ", jobModels=" + jobModels +
                ", noticeModels=" + noticeModels +
                ", userModels=" + userModels +
                '}';
    }
}

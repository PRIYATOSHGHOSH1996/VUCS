package com.vucs.api;




import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.RoutineModel;
import com.vucs.model.TeacherModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiUpdateModel {

    private List<BlogModel> blogModels;
    private List<CareerModel> careerModels;
    private List<ImageGalleryModel> imageGalleryModels;
    private List<JobFileModel> jobFileModels;
    private List <JobModel> jobModels;
    private List<NoticeModel> noticeModels;
    private List<UserModel> userModels;
    private List<TeacherModel> teacherModels;
    private List<RoutineModel> routineModels;

    public ApiUpdateModel(List<BlogModel> blogModels, List<CareerModel> careerModels,
                          List<ImageGalleryModel> imageGalleryModels,
                          List<JobFileModel> jobFileModels,
                          List<JobModel> jobModels,
                          List<NoticeModel> noticeModels,
                          List<UserModel> userModels, List<TeacherModel> teacherModels, List<RoutineModel> routineModels) {
        this.blogModels = blogModels;
        this.careerModels = careerModels;
        this.imageGalleryModels = imageGalleryModels;
        this.jobFileModels = jobFileModels;
        this.jobModels = jobModels;
        this.noticeModels = noticeModels;
        this.userModels = userModels;
        this.teacherModels = teacherModels;
        this.routineModels = routineModels;
    }

    public List<CareerModel> getCareerModels() {
        return careerModels;
    }

    public void setCareerModels(List<CareerModel> careerModels) {
        this.careerModels = careerModels;
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
                ", careerModels=" + careerModels +
                ", imageGalleryModels=" + imageGalleryModels +
                ", jobFileModels=" + jobFileModels +
                ", jobModels=" + jobModels +
                ", noticeModels=" + noticeModels +
                ", userModels=" + userModels +
                ", teacherModels=" + teacherModels +
                ", routineModels=" + routineModels +
                '}';
    }


    public List<TeacherModel> getTeacherModels() {
        return teacherModels;
    }

    public void setTeacherModels(List<TeacherModel> teacherModels) {
        this.teacherModels = teacherModels;
    }

    public List<RoutineModel> getRoutineModels() {
        return routineModels;
    }

    public void setRoutineModels(List<RoutineModel> routineModels) {
        this.routineModels = routineModels;
    }
}

package com.vucs.api;




import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiJobPostUpdateModel {
    private List<JobFileModel> jobFileModels;
    private List <JobModel> jobModels;

    public ApiJobPostUpdateModel(
                                 List<JobFileModel> jobFileModels,
                                 List<JobModel> jobModels) {
        this.jobFileModels = jobFileModels;
        this.jobModels = jobModels;

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


    @Override
    public String toString() {
        return "ApiJobPostUpdateModel{" +
                "jobFileModels=" + jobFileModels +
                ", jobModels=" + jobModels +
                '}';
    }
}

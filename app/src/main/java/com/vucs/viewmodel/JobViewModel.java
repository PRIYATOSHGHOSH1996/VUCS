package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.JobDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;

import java.util.List;

public class JobViewModel extends AndroidViewModel {

    private JobDAO jobDAO;

    public JobViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        jobDAO = db.jobDAO();
    }

    public List<JobModel> getAllJob() {
        return jobDAO.getAllJob();
    }

    public List<JobFileModel> getAllJobFileById(Integer jobId) {
        return jobDAO.getAllJobFileById(jobId);
    }
}


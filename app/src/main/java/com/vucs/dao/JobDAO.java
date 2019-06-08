package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;

import java.util.List;

@Dao
public interface JobDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertJob(JobModel jobModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertJobFile(JobFileModel jobFileModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertJob(List<JobModel> jobModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertJobFile(List<JobFileModel> jobFileModel);

    @Query("SELECT * FROM dt_job WHERE status = 1")
    public List<JobModel> getAllJob();

    @Query("DELETE FROM dt_job ")
    public void deleteAllJob();

    @Query("DELETE FROM dt_job_file ")
    public void deleteAllJobFile();

    @Query("SELECT * FROM dt_job_file WHERE job_id = :jobId")
    public List<JobFileModel> getAllJobFileById(Integer jobId);
}

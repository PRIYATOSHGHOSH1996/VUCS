package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.TeacherDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.TeacherModel;

import java.util.List;

public class TeacherViewModel extends AndroidViewModel {
    private TeacherDAO teacherDAO;

    public TeacherViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        teacherDAO = db.teacherDAO();
    }

    public List<TeacherModel> getAllTeacher(){
        return  teacherDAO.getAllTeacher();
    }
}

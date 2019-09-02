package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.RoutineDAO;
import com.vucs.dao.TeacherDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.RoutineDisplayModel;
import com.vucs.model.RoutineModel;
import com.vucs.model.TeacherModel;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {
    private RoutineDAO routineDAO;

    public RoutineViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        routineDAO = db.routineDAO();
    }

    public List<RoutineDisplayModel> getAllRoutine(){
        return  routineDAO.getAllRoutine();
    }

    public List<RoutineDisplayModel> getAllRoutineByDayNo(int dayNo){
        return  routineDAO.getAllRoutineByDayNo(dayNo);
    }

    public List<RoutineDisplayModel> getAllRoutineForStudent(int dayNo,int course,int sem){
        return routineDAO.getAllRoutineForStudent(dayNo,course,sem);
    }

    public List<RoutineDisplayModel> getAllRoutineForTeacher(int dayNo,String teacherId){
        return routineDAO.getAllRoutineForTeacher(dayNo,teacherId);
    }

    public List<Integer> getDays(){
        return routineDAO.getDays();
    }
}

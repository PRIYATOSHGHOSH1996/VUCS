package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Query;

import com.vucs.dao.ChattingDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.ChattingModel;

import java.util.List;

public class ChattingViewModel extends AndroidViewModel {
   private ChattingDAO chattingDAO;

    public ChattingViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        chattingDAO = db.chattingDAO();
    }


    public List<ChattingModel> getAllChatting(){
        return chattingDAO.getAllChatting();
    }


    public List<ChattingModel> getAllChattingBySemAndCourse(int sem, int course){
        return chattingDAO.getAllChattingBySemAndCourse(sem,course);
    }

    public List<ChattingModel> getAllChattingByTeacher(int teacherId){
        return chattingDAO.getAllChattingByTeacher(teacherId);
    }
}

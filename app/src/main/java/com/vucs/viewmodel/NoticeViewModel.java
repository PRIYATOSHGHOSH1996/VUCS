package com.vucs.viewmodel;

import android.app.Application;

import com.vucs.dao.NoticeDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.NoticeModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NoticeViewModel extends AndroidViewModel {

    private NoticeDAO noticeDAO;

    public NoticeViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        noticeDAO = db.noticeDAO();
    }

    public List<NoticeModel> getAllNotice() {
        return noticeDAO.getAllNotice();
    }
}

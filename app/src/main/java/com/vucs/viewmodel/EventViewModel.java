package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.EventDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.EventModel;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventDAO eventDAO;

    public EventViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDAO = db.eventDAO();
    }

    public List<EventModel> getAllEvent() {
        return eventDAO.getAllEvent();
    }
}

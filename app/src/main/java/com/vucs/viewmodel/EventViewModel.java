package com.vucs.viewmodel;

import android.app.Application;

import com.vucs.dao.BlogDAO;
import com.vucs.dao.EventDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.BlogModel;
import com.vucs.model.EventModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EventViewModel extends AndroidViewModel {

    private EventDAO eventDAO;
    public EventViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDAO = db.eventDAO();
    }

    public LiveData<List<EventModel>> getAllEvent(){
        return eventDAO.getAllEvent();
    }
}

package com.vucs.dao;

import com.vucs.model.BlogModel;
import com.vucs.model.EventModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface EventDAO {


    public void addEvent(EventModel eventModel);

    public void addEvents(List<EventModel> eventModels);

    public List<EventModel> getAllEvent();
}

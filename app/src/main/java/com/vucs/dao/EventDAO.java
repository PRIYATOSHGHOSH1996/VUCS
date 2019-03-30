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


    @Query("SELECT * FROM dt_event")
    public LiveData<List<EventModel>> getAllEvent();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEvent(EventModel eventModel);
}

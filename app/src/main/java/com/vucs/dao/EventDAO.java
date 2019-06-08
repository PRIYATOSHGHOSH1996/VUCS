package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.EventModel;

import java.util.List;

@Dao
public interface EventDAO {


    @Query("SELECT * FROM dt_event")
    public List<EventModel> getAllEvent();

    @Query("DELETE FROM dt_event")
    public void deleteAllEvent();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEvent(EventModel eventModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEvent(List<EventModel> eventModels);
}

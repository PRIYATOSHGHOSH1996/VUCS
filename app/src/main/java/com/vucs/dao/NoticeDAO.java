package com.vucs.dao;

import com.vucs.model.BlogModel;
import com.vucs.model.NoticeModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NoticeDAO {


    @Query("SELECT * FROM dt_notice")
    public LiveData<List<NoticeModel>> getAllNotice();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNotice(NoticeModel noticeModel);
}

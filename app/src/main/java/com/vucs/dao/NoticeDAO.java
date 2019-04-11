package com.vucs.dao;

import com.vucs.model.NoticeModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NoticeDAO {


    @Query("SELECT * FROM dt_notice")
    public List<NoticeModel> getAllNotice();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNotice(NoticeModel noticeModel);
}

package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.ClassNoticeModel;
import com.vucs.model.NoticeModel;

import java.util.List;

@Dao
public interface NoticeDAO {


    @Query("SELECT * FROM dt_notice")
    public List<NoticeModel> getAllNotice();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNotice(NoticeModel noticeModel);

    @Query("SELECT * FROM dt_class_notice")
    public List<ClassNoticeModel> getAllClassNotice();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertClassNotice(ClassNoticeModel classNoticeModel);
}

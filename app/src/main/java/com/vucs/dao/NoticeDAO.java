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


    @Query("SELECT * FROM dt_notice ORDER BY date DESC")
    public List<NoticeModel> getAllNotice();

    @Query("DELETE FROM dt_notice")
    public void deleteAllNotice();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNotice(NoticeModel noticeModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNotice(List<NoticeModel> noticeModel);

    @Query("SELECT * FROM dt_class_notice ORDER BY date DESC")
    public List<ClassNoticeModel> getAllClassNotice();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertClassNotice(ClassNoticeModel classNoticeModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertClassNotice(List<ClassNoticeModel> classNoticeModel);
}

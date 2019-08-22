package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.ChattingModel;

import java.util.List;

@Dao
public interface ChattingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertChatting(ChattingModel chattingModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertChatting(List<ChattingModel> chattingModel);

    @Query("SELECT * FROM dt_chatting ORDER BY date DESC")
    public List<ChattingModel> getAllChatting();

    @Query("SELECT * FROM dt_chatting WHERE sem=:sem AND course=:course ORDER BY date DESC")
    public List<ChattingModel> getAllChattingBySemAndCourse(int sem, int course);

    @Query("SELECT * FROM dt_chatting WHERE sender_id=:teacherId  ORDER BY date DESC")
    public List<ChattingModel> getAllChattingByTeacher(int teacherId);

    @Query("DELETE FROM dt_chatting")
    public void deleteAllChatting();
}

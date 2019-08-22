package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.TeacherModel;

import java.util.List;

@Dao
public interface TeacherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTeacher(TeacherModel teacherModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTeacher(List<TeacherModel> teacherModel);

    @Query("SELECT * FROM dt_teacher")
    public List<TeacherModel> getAllTeacher();

    @Query("DELETE FROM dt_teacher")
    public void deleteAllTeacher();
}

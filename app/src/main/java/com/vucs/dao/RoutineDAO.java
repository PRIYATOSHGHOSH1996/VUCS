package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.RoutineDisplayModel;
import com.vucs.model.RoutineModel;

import java.util.List;

@Dao
public interface RoutineDAO {


    @Query("SELECT dt_routine.*,dt_teacher.name as teacherName FROM dt_routine LEFT JOIN dt_teacher ON dt_routine.teacher_id = dt_teacher.id ORDER BY start_time ")
    public List<RoutineDisplayModel> getAllRoutine();

    @Query("SELECT dt_routine.*,dt_teacher.name as teacherName FROM dt_routine" +
            " LEFT JOIN dt_teacher ON dt_routine.teacher_id = dt_teacher.id WHERE day_no=:dayNo ORDER BY start_time ")
    public List<RoutineDisplayModel> getAllRoutineByDayNo(int dayNo);

    @Query("SELECT dt_routine.*,dt_teacher.name as teacherName FROM dt_routine" +
            " LEFT JOIN dt_teacher ON dt_routine.teacher_id = dt_teacher.id WHERE day_no=:dayNo AND course=:course AND sem=:sem ORDER BY start_time ")
    public List<RoutineDisplayModel> getAllRoutineForStudent(int dayNo,int course,int sem);

    @Query("SELECT dt_routine.*,dt_teacher.name as teacherName FROM dt_routine" +
            " LEFT JOIN dt_teacher ON dt_routine.teacher_id = dt_teacher.id WHERE day_no=:dayNo AND teacher_id=:teacherId ORDER BY start_time ")
    public List<RoutineDisplayModel> getAllRoutineForTeacher(int dayNo,String teacherId);



    @Query("SELECT dt_routine.day_no FROM dt_routine GROUP BY day_no ORDER BY day_no ")
    public List<Integer> getDays();

    @Query("SELECT dt_routine.day_no FROM dt_routine WHERE course=:course AND sem=:sem GROUP BY day_no ORDER BY day_no ")
    public List<Integer> getDays(int course,int sem);

    @Query("SELECT dt_routine.day_no FROM dt_routine WHERE teacher_id=:teacherId GROUP BY day_no ORDER BY day_no ")
    public List<Integer> getDays(String teacherId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRoutine(RoutineModel routineModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRoutine(List<RoutineModel> routineModels);

    @Query("DELETE FROM dt_routine")
    public void deleteAllRoutine();
}

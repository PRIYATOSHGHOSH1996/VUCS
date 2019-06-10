package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.CareerModel;
import com.vucs.model.PhirePawaModel;

import com.vucs.model.UserModel;

import java.util.List;

@Dao
public interface PhirePawaProfileDAO {


    @Query("SELECT * FROM dt_users" +
            " ORDER BY first_name")
    public List<UserModel> getUsersByName();

    @Query("SELECT * FROM dt_users" +
            " WHERE dt_users.first_name LIKE :s ORDER BY first_name")
    public List<UserModel> getUsersByName(String s);

    @Query("SELECT * FROM dt_users" +
            " ORDER BY batch_end_date DESC,first_name")
    public List<UserModel> getUsersByBatch();

    @Query("SELECT * FROM dt_users" +
            " WHERE dt_users.batch_end_date >= :start AND dt_users.batch_end_date<:end ORDER BY batch_end_date DESC, first_name")
    public List<UserModel> getUsersByBatch(long start,long end);

    @Query("SELECT * FROM dt_users order by course, first_name")
    public List<UserModel> getUsersByCourse();

    @Query("SELECT * FROM dt_users" +
            " WHERE course LIKE :s ORDER BY course,first_name")
    public List<UserModel> getUsersByCourse(String s);

    @Query("select * from dt_career where user_id = :id order by end_date")
    public List<CareerModel> getCareerDetailsByUserId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCareer(CareerModel careerModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(List<UserModel> userModels);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCareer(List<CareerModel> careerModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(UserModel userModel);
    @Query("SELECT * FROM dt_users" +
            " WHERE id = :id")
    public UserModel getUserDetailsById(int id);

    @Query("DELETE FROM dt_career WHERE id=:id")
    public void deleteCareerById(int id);

    @Query("DELETE FROM dt_career")
    public void deleteAllCareer();

    @Query("DELETE FROM dt_users")
    public void deleteAllUser();

}

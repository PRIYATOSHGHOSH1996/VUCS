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
            " WHERE dt_users.id <> :userId ORDER BY first_name")
    public List<UserModel> getUsersByName(String userId);

    @Query("SELECT * FROM dt_users" +
            " WHERE dt_users.first_name LIKE :s AND dt_users.id <> :userId ORDER BY first_name")
    public List<UserModel> getUsersByName(String s,String userId);

    @Query("SELECT * FROM dt_users" +
            " WHERE   dt_users.id <> :userId ORDER BY batch_end_date DESC,first_name")
    public List<UserModel> getUsersByBatch(String userId);

    @Query("SELECT * FROM dt_users" +
            " WHERE dt_users.batch_end_date >= :start AND dt_users.batch_end_date<:end AND dt_users.id <> :userId ORDER BY batch_end_date DESC, first_name")
    public List<UserModel> getUsersByBatch(long start,long end,String userId);

    @Query("SELECT * FROM dt_users WHERE dt_users.id <> :userId order by course, first_name")
    public List<UserModel> getUsersByCourse(String userId);

    @Query("SELECT * FROM dt_users" +
            " WHERE course LIKE :s AND dt_users.id <> :userId ORDER BY course,first_name")
    public List<UserModel> getUsersByCourse(String s,String userId);

    @Query("select * from dt_career where user_id = :id order by end_date")
    public List<CareerModel> getCareerDetailsByUserId(String id);

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
    public UserModel getUserDetailsById(String id);

    @Query("DELETE FROM dt_career WHERE id=:id")
    public void deleteCareerById(int id);

    @Query("DELETE FROM dt_career")
    public void deleteAllCareer();

    @Query("DELETE FROM dt_users")
    public void deleteAllUser();

}

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


    @Query("SELECT first_name as firstName,last_name as lastName ," +
            "batch_end_date as batch,company,imageUrl as userImageURL FROM dt_career,dt_users" +
            " WHERE dt_users.id = dt_career.user_id AND dt_career.end_date = 10 ORDER BY first_name")
    public List<PhirePawaModel> getUsersByName();

    @Query("SELECT first_name as firstName,last_name as lastName ," +
            "batch_end_date as batch,company,imageUrl as userImageURL FROM dt_career,dt_users" +
            " WHERE dt_users.id = dt_career.user_id AND dt_career.end_date = 10 ORDER BY batch_end_date DESC")
    public List<PhirePawaModel> getUsersByBatch();

    @Query("SELECT first_name as firstName,last_name as lastName ," +
            "batch_end_date as batch,company,imageUrl as userImageURL FROM dt_career,dt_users" +
            " WHERE dt_users.id = dt_career.user_id AND dt_career.end_date = 10 AND dt_users.batch_end_date >= :start AND dt_users.batch_end_date<:end ORDER BY batch_end_date DESC")
    public List<PhirePawaModel> getUsersByBatch(long start,long end);

    @Query("SELECT first_name as firstName,last_name as lastName ," +
            "batch_end_date as batch,company,imageUrl as userImageURL FROM dt_career,dt_users" +
            " WHERE dt_users.id = dt_career.user_id AND dt_career.end_date = 10 AND dt_users.first_name LIKE :s ORDER BY first_name")
    public List<PhirePawaModel> getUsersByName(String s);

    @Query("SELECT first_name as firstName,last_name as lastName ," +
            "batch_end_date as batch,company,imageUrl as userImageURL FROM dt_career,dt_users" +
            " WHERE dt_users.id = dt_career.user_id AND dt_career.end_date = 10 ORDER BY company")
    public List<PhirePawaModel> getUsersByCompany();

    @Query("SELECT first_name as firstName,last_name as lastName ," +
            "batch_end_date as batch,company,imageUrl as userImageURL FROM dt_career,dt_users" +
            " WHERE dt_users.id = dt_career.user_id AND dt_career.end_date = 10 AND dt_career.company LIKE :s ORDER BY company")
    public List<PhirePawaModel> getUsersByCompany(String s);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCareer(CareerModel careerModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(UserModel userModel);
}

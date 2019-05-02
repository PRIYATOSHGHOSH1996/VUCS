package com.vucs.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vucs.model.CareerModel;
import com.vucs.model.PhirePawaModel;
import com.vucs.model.PhirePawaProfileModel;
import com.vucs.model.UserModel;

import java.util.List;

@Dao
public interface PhirePawaProfileDAO {


    @Query("SELECT * FROM dt_phire_pawa ORDER BY user_name")
    public List<PhirePawaProfileModel> getAllUserByName();

    @Query("SELECT * FROM dt_phire_pawa  WHERE user_name LIKE :searchText ORDER BY user_name")
    public List<PhirePawaProfileModel> getAllUserByName(String searchText);

    @Query("SELECT * FROM dt_phire_pawa")
    public List<PhirePawaProfileModel> getAllUser();

    @Query("SELECT * FROM dt_phire_pawa ORDER BY user_batch")
    public List<PhirePawaProfileModel> getAllUserByBatch();

    @Query("SELECT * FROM dt_phire_pawa WHERE user_batch LIKE :searchText ORDER BY user_batch")
    public List<PhirePawaProfileModel> getAllUserByBatch(String searchText);

    @Query("SELECT * FROM dt_phire_pawa ORDER BY user_company")
    public List<PhirePawaProfileModel> getAllUserByCompany();

    @Query("SELECT * FROM dt_phire_pawa WHERE user_company LIKE :searchText ORDER BY user_company")
    public List<PhirePawaProfileModel> getAllUserByCompany(String searchText);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(PhirePawaProfileModel phirePawaProfileModel);

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCareer(CareerModel careerModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(UserModel userModel);
}

package com.vucs.dao;

import com.vucs.model.PhirePawaProfileModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
}

package com.vucs.dao;

import com.vucs.model.PhirePawaProfileModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PhirePawaProfileDAO {


    @Query("SELECT * FROM dt_phire_pawa")
    public List<PhirePawaProfileModel> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(PhirePawaProfileModel phirePawaProfileModel);
}

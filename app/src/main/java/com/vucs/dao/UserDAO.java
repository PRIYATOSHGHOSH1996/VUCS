package com.vucs.dao;

import com.vucs.model.BlogModel;
import com.vucs.model.UserModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDAO {


    @Query("SELECT * FROM dt_user")
    public LiveData<List<UserModel>> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(UserModel userModel);
}

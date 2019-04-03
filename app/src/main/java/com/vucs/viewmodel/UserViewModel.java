package com.vucs.viewmodel;

import android.app.Application;

import com.vucs.dao.BlogDAO;
import com.vucs.dao.UserDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.BlogModel;
import com.vucs.model.UserModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {

    private UserDAO userDAO;
    public UserViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
    }

    public LiveData<List<UserModel>> getAllUser(){
        return userDAO.getAllUser();
    }
}

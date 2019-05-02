package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.PhirePawaModel;
import com.vucs.model.PhirePawaProfileModel;

import java.util.Calendar;
import java.util.List;

public class PhirePawaProfileViewModel extends AndroidViewModel {

    private PhirePawaProfileDAO phirePawaProfileDAO;

    public PhirePawaProfileViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        phirePawaProfileDAO = db.phirePawaProfileDAO();
    }

    public List<PhirePawaProfileModel> getAllUser() {
        return phirePawaProfileDAO.getAllUser();
    }

    public List<PhirePawaProfileModel> getAllUserByName() {
        return phirePawaProfileDAO.getAllUserByName();
    }

    public List<PhirePawaProfileModel> getAllUserByName(String searchText) {
        return phirePawaProfileDAO.getAllUserByName(searchText);
    }

    public List<PhirePawaProfileModel> getAllUserByBatch() {
        return phirePawaProfileDAO.getAllUserByBatch();
    }

    public List<PhirePawaProfileModel> getAllUserByBatch(String searchText) {
        return phirePawaProfileDAO.getAllUserByBatch(searchText);
    }

    public List<PhirePawaProfileModel> getAllUserByCompany() {
        return phirePawaProfileDAO.getAllUserByCompany();
    }

    public List<PhirePawaProfileModel> getAllUserByCompany(String searchText) {
        return phirePawaProfileDAO.getAllUserByCompany(searchText);
    }

    public List<PhirePawaModel> getUsersByName(){
        return phirePawaProfileDAO.getUsersByName();
    }

    public List<PhirePawaModel> getUsersByBatch(){
        return phirePawaProfileDAO.getUsersByBatch();
    }

    public List<PhirePawaModel> getUsersByBatch(int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(date,0,1,0,0,0);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(date+1,0,1,0,0,0);
        return phirePawaProfileDAO.getUsersByBatch(calendar.getTimeInMillis(),calendar1.getTimeInMillis());
    }
}

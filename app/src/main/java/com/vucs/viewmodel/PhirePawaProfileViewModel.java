package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.PhirePawaModel;


import java.util.Calendar;
import java.util.List;

public class PhirePawaProfileViewModel extends AndroidViewModel {

    private PhirePawaProfileDAO phirePawaProfileDAO;

    public PhirePawaProfileViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        phirePawaProfileDAO = db.phirePawaProfileDAO();
    }

    public List<PhirePawaModel> getUsersByName(){
        return phirePawaProfileDAO.getUsersByName();
    }

    public List<PhirePawaModel> getUsersByBatch(){
        return phirePawaProfileDAO.getUsersByBatch();
    }

    public List<PhirePawaModel> getUsersByBatch(String s){
        try {
            int size= s.length();
            int date=Integer.parseInt(s);

            Calendar calendar = Calendar.getInstance();
            calendar.set(date* (int)Math.pow(10,4-size),0,1,0,0,0);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set((date+1)* (int)Math.pow(10,4-size),0,1,0,0,0);
            return phirePawaProfileDAO.getUsersByBatch(calendar.getTimeInMillis(),calendar1.getTimeInMillis());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return phirePawaProfileDAO.getUsersByBatch();
        }
    }

    public List<PhirePawaModel> getUsersByName(String s){
        s=s+"%";

            return phirePawaProfileDAO.getUsersByName(s);

    }

    public List<PhirePawaModel> getUsersByCompany(String s){
        s=s+"%";

        return phirePawaProfileDAO.getUsersByCompany(s);

    }

    public List<PhirePawaModel> getUsersByCompany(){
        return phirePawaProfileDAO.getUsersByCompany();

    }
}

package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.model.CareerModel;
import com.vucs.model.PhirePawaModel;
import com.vucs.model.UserModel;


import java.util.List;

public class PhirePawaProfileViewModel extends AndroidViewModel {

    private PhirePawaProfileDAO phirePawaProfileDAO;
    AppPreference appPreference;

    public PhirePawaProfileViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        appPreference = new AppPreference(application);
        phirePawaProfileDAO = db.phirePawaProfileDAO();
    }

    public List<UserModel> getUsersByName(){
        return phirePawaProfileDAO.getUsersByName(appPreference.getUserId());
    }

    public List<UserModel> getUsersByBatch(){
        return phirePawaProfileDAO.getUsersByBatch(appPreference.getUserId());
    }

    public List<UserModel> getUsersByBatch(String s){
        try {
            int size= s.length();
            int date=Integer.parseInt(s);


            return phirePawaProfileDAO.getUsersByBatch(date* (int)Math.pow(10,4-size),(date+1)* (int)Math.pow(10,4-size),appPreference.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return phirePawaProfileDAO.getUsersByBatch(appPreference.getUserId());
        }
    }

    public List<UserModel> getUsersByName(String s){
        s=s+"%";


            return phirePawaProfileDAO.getUsersByName(s,appPreference.getUserId());

    }

    public List<UserModel> getUsersByCourse(String s){
        s=s+"%";

        return phirePawaProfileDAO.getUsersByCourse(s,appPreference.getUserId());

    }


    public List<UserModel> getUsersByCourse(){
        return phirePawaProfileDAO.getUsersByCourse(appPreference.getUserId());


    }

   public UserModel getUserDetailsById(String id){
        return phirePawaProfileDAO.getUserDetailsById(id);
   }

    public List<CareerModel> getCareerDetailsByUserId(String id){
        return phirePawaProfileDAO.getCareerDetailsByUserId(id);
    }
}

package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.PhirePawaModel;
import com.vucs.model.UserModel;


import java.util.List;

public class PhirePawaProfileViewModel extends AndroidViewModel {

    private PhirePawaProfileDAO phirePawaProfileDAO;

    public PhirePawaProfileViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        phirePawaProfileDAO = db.phirePawaProfileDAO();
    }

    public List<UserModel> getUsersByName(){
        return phirePawaProfileDAO.getUsersByName();
    }

    public List<UserModel> getUsersByBatch(){
        return phirePawaProfileDAO.getUsersByBatch();
    }

    public List<UserModel> getUsersByBatch(String s){
        try {
            int size= s.length();
            int date=Integer.parseInt(s);

            return phirePawaProfileDAO.getUsersByBatch(date* (int)Math.pow(10,4-size),(date+1)* (int)Math.pow(10,4-size));
        } catch (Exception e) {
            e.printStackTrace();
            return phirePawaProfileDAO.getUsersByBatch();
        }
    }

    public List<UserModel> getUsersByName(String s){
        s=s+"%";

            return phirePawaProfileDAO.getUsersByName(s);

    }

    public List<UserModel> getUsersByCourse(String s){
        s=s+"%";

        return phirePawaProfileDAO.getUsersByCourse(s);

    }


    public List<UserModel> getUsersByCourse(){
        return phirePawaProfileDAO.getUsersByCourse();


    }

   public UserModel getUserDetailsById(int id){
        return phirePawaProfileDAO.getUserDetailsById(id);
   }
}

package com.vucs.dao;

import com.vucs.model.PhirePawaProfileModel;

import java.util.List;

import androidx.lifecycle.LiveData;



public interface PhirePawaProfileDAO {


    public List<PhirePawaProfileModel> getAllUser();


    public void insertUser(PhirePawaProfileModel phirePawaProfileModel);
}

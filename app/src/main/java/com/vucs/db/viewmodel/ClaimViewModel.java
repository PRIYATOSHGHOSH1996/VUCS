package com.vucs.db.viewmodel;

import android.app.Application;

import com.trackpay.model.ClaimDisplayModel;
import com.trackpay.model.ClaimFileModel;
import com.trackpay.model.ClaimModel;
import com.trackpay.model.ExpenseCategoryModel;
import com.trackpay.model.UserAllClaim;
import com.trackpay.repository.ClaimRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ClaimViewModel extends AndroidViewModel {

    private ClaimRepository claimRepository;
    private LiveData<List<ClaimDisplayModel>> claims;

    public ClaimViewModel(@NonNull Application application) {
        super(application);
        claimRepository = new ClaimRepository(application);
    }

    public LiveData<List<ClaimModel>> getClaimListByProjectId(Integer projectId){
        return claimRepository.getClaimListByProjectId(projectId);
    }
    public LiveData<List<UserAllClaim>> getAllClaim(){
        return claimRepository.getAllClaim();
    }

    public LiveData<List<ClaimDisplayModel>> getPendingClaimList(List<Integer> statusList, Integer userId, Integer projectId){
        return  claimRepository.getPendingClaimList(statusList,userId,projectId);
    }
    public LiveData<List<ClaimDisplayModel>> getActionClaimList(List<Integer> statusList, Integer userId, Integer projectId) {
        return claimRepository.getActionClaimList(statusList,userId, projectId);
    }
    public void insertClaim(ClaimModel claimModel){
        claimRepository.insertClaim(claimModel);
    }

    public void insertClaimFile(ClaimFileModel claimFileModel){
        claimRepository.insertClaimFile(claimFileModel);
    }

    public void updateClaim(ClaimModel claimModel){
        claimRepository.updateClaim(claimModel);
    }

    public void deleteClaim(String claimId){
        claimRepository.deleteClaim(claimId);
    }

    public void deleteAllClaims(){
        claimRepository.deleteAllClaims();
    }

    public LiveData<List<ExpenseCategoryModel>> getAllExpenseCategory(){
        return claimRepository.getAllExpenseCategory();
    }
}

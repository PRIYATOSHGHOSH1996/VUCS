package com.vucs.dao;


import com.trackpay.model.ClaimDisplayFileModel;
import com.trackpay.model.ClaimDisplayModel;
import com.trackpay.model.ClaimFileModel;
import com.trackpay.model.ClaimModel;
import com.trackpay.model.ClaimStatusModel;
import com.trackpay.model.ExpenseCategoryModel;
import com.trackpay.model.UserAllClaim;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClaimDAO {

    @Query("SELECT * FROM dt_claim claim " +
            "WHERE claim.project_id = :projectId")
    public LiveData<List<ClaimModel>> getClaimListByProjectId(Integer projectId);

    @Query("SELECT *," +
            "(SELECT claim_file_synced_status FROM dt_claim_file claim_file WHERE claim.claim_id = claim_file.claim_file_synced_status ) AS claimFileSyncedStatus ," +
            "(SELECT expense_category_name FROM dt_expense_category expense WHERE claim.expense_category_id = expense.expense_category_id ) AS expenseCategory ," +
            "(SELECT claim_status_name FROM dt_claim_status status WHERE claim.claim_status_id = status.claim_status_id ) AS claimStatus " +
            "FROM dt_claim claim " +
            "WHERE claim.claim_status_id IN (:statusList) AND claim.project_id = :projectId AND claim.user_id = :userId ORDER BY claim_date DESC")
    public LiveData<List<ClaimDisplayModel>> getPendingClaimList(List<Integer> statusList, Integer userId, Integer projectId);

    @Query("SELECT *," +
            "(SELECT claim_file_synced_status FROM dt_claim_file claim_file WHERE claim.claim_id = claim_file.claim_file_synced_status ) AS claimFileSyncedStatus ," +
            "(SELECT expense_category_name FROM dt_expense_category expense WHERE claim.expense_category_id = expense.expense_category_id ) AS expenseCategory ," +
            "(SELECT claim_status_name FROM dt_claim_status status WHERE claim.claim_status_id = status.claim_status_id ) AS claimStatus " +
            "FROM dt_claim claim " +
            "WHERE claim.claim_status_id IN (:statusList) AND claim.project_id = :projectId AND claim.user_id <> :userId AND claim.claim_synced_status = 1 ORDER BY claim_date DESC")
    public LiveData<List<ClaimDisplayModel>> getActionClaimList(List<Integer> statusList, Integer userId, Integer projectId);

    @Query("SELECT * from dt_claim " +
            "WHERE claim_synced_status = 0")
    public List<ClaimModel> getAllUnSyncedClaims();


    @Query("SELECT file.* , claim.file_name AS fileName ,claim.claim_title AS claimTitle " +
            "FROM dt_claim_file file,dt_claim claim " +
            "WHERE file.claim_id = claim.claim_id AND file.claim_file_synced_status = 0")
    public List<ClaimDisplayFileModel> getAllUnSyncedClaimFiles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertClaim(ClaimModel claimModel);

    @Update
    public void updateClaim(ClaimModel claimModel);

    @Query("DELETE FROM dt_claim WHERE claim_id =:claimId")
    public void deleteClaim(String claimId);

    @Query("DELETE FROM dt_claim")
    public void deleteAllClaims();

    @Query("UPDATE dt_claim SET claim_synced_status = 1 WHERE claim_id = :claimId")
    public int updateClaimSyncedStatus(String claimId);

    @Query("UPDATE dt_claim_file SET claim_file_synced_status = 1 WHERE claim_id = :claimId")
    public int updateClaimFileSyncedStatus(String claimId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertExpenseCategory(ExpenseCategoryModel expenseCategoryModel);

    @Query("DELETE FROM dt_expense_category")
    public void deleteAllExpenceCategory();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertClaimStatus(ClaimStatusModel claimStatusModel);

    @Query("SELECT * FROM dt_expense_category")
    public LiveData<List<ExpenseCategoryModel>> getAllExpenseCategory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertClaimFile(ClaimFileModel claimFileModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllUserClaim(UserAllClaim userAllClaim);

    @Query("SELECT * FROM dt_all_claims")
    public LiveData<List<UserAllClaim>> getAllClaim();

    @Query("DELETE FROM dt_all_claims")
    public void deleteAllUserClaims();

}

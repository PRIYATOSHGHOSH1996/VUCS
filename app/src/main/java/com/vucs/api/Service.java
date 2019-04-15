package com.vucs.api;


public interface Service {

//    @GET("projects.json")
//    Call<List<ProjectModel>> getProjects();
//
//    @GET("headexpenses.json")
//    Call<List<ProjectHeadExpenseModel>> getProjectHeadExpenses();
//
//    @GET("userexpenses.json")
//    Call<List<ProjectUserExpenseModel>> getProjectUserExpenses();
//
//    /* API for login */
//    @POST("get_login")
//    Call<ApiLoginResponseModel> getUserLogin(@Body ApiLoginModel apiLoginModel);
//
//    /* API for search claim*/
//    @POST("search_claim_by_unique_code")
//    Call<ApiClaimSearchResponceModel> getClaimById(@Body ApiClaimSearchModel apiClaimSearchModel);
//
//    /* API for getting projects, head expenses, users expenses, and claims */
//    @POST("fetch_all_data")
//    Call<ApiUpdateModel> getExpenseTrackerData(@Body ApiCredentialWithUserId credentials);
//
//    /* Api for saving claims */
//    @POST("save_claims")
//    Call<ApiResponseModel> uploadClaimData(@Body ApiClaimModel apiClaimModel);
//
//    /* Api for saving claim files */
//    @Multipart
//    @POST("save_claim_files")
//    Call<ApiResponseModel> uploadClaimFiles(@Part("apiLogin") RequestBody apiLogin, @Part("apiPass") RequestBody apiPass, @Part MultipartBody.Part file);
//
//    /* API for updating firebase token projects */
//    @POST("update_firebase_token")
//    Call<ApiResponseModel> updateFirebaseToken(@Body ApiFirebaseModel apiFirebaseModel);
}
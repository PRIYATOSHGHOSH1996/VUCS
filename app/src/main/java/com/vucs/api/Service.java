package com.vucs.api;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

//
//    /* API for search claim*/
//    @POST("search_claim_by_unique_code")
//    Call<ApiClaimSearchResponceModel> getClaimById(@Body ApiClaimSearchModel apiClaimSearchModel);
//
//    /* API for getting projects, head expenses, users expenses, and claims */
    @POST("fetch_all_data")
    Call<ApiUpdateModel> getExpenseTrackerData(@Body ApiCredentialWithUserId credentials);
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

    @Multipart
    @POST("new_registration")
    Call<ApiResponseModel> registrationWithProfilePic(@Part("apiLogin") RequestBody apiLogin, @Part("apiPass") RequestBody apiPass,
                                                      @Part("firstName") RequestBody firstName, @Part("lastName") RequestBody lastName,
                                                      @Part("mail") RequestBody mail, @Part("phoneNo") RequestBody phoneNo,
                                                      @Part("address") RequestBody address, @Part("dob") RequestBody dob,
                                                      @Part("course") RequestBody course, @Part("startYear") RequestBody startYear,
                                                      @Part("endYear") RequestBody endYear, @Part MultipartBody.Part profilePic,
                                                      @Part MultipartBody.Part supportFile);

    //     API for login
@POST("check_login")
Call<ApiLoginResponseModel> getUserLogin(@Body ApiLoginModel apiLoginModel);
}
package com.vucs.api;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Service {


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
    @POST("get_login")
    Call<ApiLoginResponseModel> getUserLogin(@Body ApiLoginModel apiLoginModel);

    //     API for career delete
    @POST("delete_career")
    Call<ApiResponseModel> deleteCareer(@Body ApiDeleteCareerModel apiDeleteCareerModel);

    //     API for career add
    @POST("add_career")
    Call<ApiAddCareerResponseModel> addCareer(@Body ApiAddCareerModel apiAddCareerModel);

    //     API for change password
    @POST("change_password")
    Call<ApiResponseModel> changePassword(@Body ApiChangePasswordModel apiChangePasswordModel);

    //     API for forgot password
    @POST("forgot_password")
    Call<ApiResponseModel> forgotPassword(@Body ApiForgotPasswordModel apiForgotPasswordModel);

    //     API for upload token
    @POST("firebase_token_update")
    Call<ApiResponseModel> uploadToken(@Body ApiUploadFirebaseTokenModel apiUploadFirebaseTokenModel);

    //     API for send class notice
    @Multipart
    @POST("add_class_notification")
    Call<ApiClassNoticeResponseModel> sendClassNotice(@Part("apiLogin") RequestBody apiLogin, @Part("apiPass") RequestBody apiPass,@Part("user_id") RequestBody userId, @Part("user_name") RequestBody userName,@Part("course") RequestBody course, @Part("sem") RequestBody sem,@Part("message") RequestBody message);

    //     API for send class notice
    @Multipart
    @POST("add_class_notification")
    Call<ApiClassNoticeResponseModel> sendClassNoticeWithFile(@Part("apiLogin") RequestBody apiLogin, @Part("apiPass") RequestBody apiPass,@Part("user_id") RequestBody userId, @Part("user_name") RequestBody userName,@Part("course") RequestBody course, @Part("sem") RequestBody sem,@Part("message") RequestBody message,@Part MultipartBody.Part jobFile);

    /* API for fetching all data */
    @POST("fetch_all_data")
    Call<ApiUpdateModel> getAllData(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all Blogs */
    @POST("fetch_blogs")
    Call<ApiBlogUpdateModel> getBlog(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all routine */
    @POST("fetch_routine")
    Call<ApiRoutineUpdateModel> getRoutine(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all teacher */
    @POST("fetch_teachers")
    Call<ApiTeacherUpdateModel> getTeacher(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all Jobs */
    @POST("fetch_job")
    Call<ApiJobPostUpdateModel> getJob(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all Image */
    @POST("fetch_gallery")
    Call<ApiImageUpdateModel> getImage(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all Notice */
    @POST("fetch_notice")
    Call<ApiNoticeUpdateModel> getNotice(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all phirepawa */
    @POST("fetch_user")
    Call<ApiPhirePawaUpdateModel> getPhirePawa(@Body ApiCredentialWithUserId credentials);

    /* API for fetching all phirepawa */
    @POST("fetch_career")
    Call<ApiCareerUpdateModel> getCareer(@Body ApiCredentialWithUserId credentials);

    /* API for addJob */
    @POST("add_job")
    Call<ApiAddJobResponseModel> addJob(@Body ApiAddJobModel apiAddJobModel);

    /* API for add Job File */
    @Multipart
    @POST("job_files_upload")
    Call<ApiAddJobFileResponseModel> uploadJobFile(@Part("apiLogin") RequestBody apiLogin, @Part("apiPass") RequestBody apiPass, @Part("jobId") RequestBody jobId,@Part("userId") RequestBody userId,@Part MultipartBody.Part jobFile);

    /* API for update profile */
    @Multipart
    @POST("edit_profile")
    Call<ApiClassNoticeResponseModel> updateProfile(@Part("apiLogin") RequestBody apiLogin, @Part("apiPass") RequestBody apiPass,@Part("user_id") RequestBody userId, @Part("first_name") RequestBody firstName,
                                                   @Part("last_name") RequestBody lastName, @Part("phone_number") RequestBody phoneNumber, @Part("address") RequestBody address,
                                                   @Part("start_year") RequestBody startYear, @Part("end_year") RequestBody endYear);

    /* API for update profile */
    @Multipart
    @POST("edit_profile")
    Call<ApiClassNoticeResponseModel> updateProfile(@Part("apiLogin") RequestBody apiLogin, @Part("apiPass") RequestBody apiPass,@Part("user_id") RequestBody userId, @Part("first_name") RequestBody firstName,
                                                   @Part("last_name") RequestBody lastName, @Part("phone_number") RequestBody phoneNumber, @Part("address") RequestBody address,
                                                   @Part("start_year") RequestBody startYear, @Part("end_year") RequestBody endYear,@Part MultipartBody.Part profileImage);
}

package com.vucs.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vucs.R;
import com.vucs.activity.ClassNoticeActivity;
import com.vucs.activity.HomeActivity;
import com.vucs.activity.LoginActivity;
import com.vucs.api.ApiBlogUpdateModel;
import com.vucs.api.ApiCareerUpdateModel;
import com.vucs.api.ApiCredentialWithUserId;
import com.vucs.api.ApiImageUpdateModel;
import com.vucs.api.ApiJobPostUpdateModel;
import com.vucs.api.ApiNoticeUpdateModel;
import com.vucs.api.ApiPhirePawaUpdateModel;
import com.vucs.api.ApiRoutineUpdateModel;
import com.vucs.api.ApiTeacherUpdateModel;
import com.vucs.api.Service;
import com.vucs.dao.BlogDAO;
import com.vucs.dao.ImageGalleryDAO;
import com.vucs.dao.JobDAO;
import com.vucs.dao.NoticeDAO;
import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.dao.RoutineDAO;
import com.vucs.dao.TeacherDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Constants;
import com.vucs.helper.Notification;
import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ClassNoticeModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.RoutineModel;
import com.vucs.model.TeacherModel;
import com.vucs.model.UserModel;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vucs.App.getContext;

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = FirebaseMessaging.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "  message received");
        if (remoteMessage != null) {
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "Message data payload: " + remoteMessage.getData());
                try {
                    sendData(remoteMessage.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            // Check if message contains a notification payload.
            else if (remoteMessage.getNotification() != null) {
                Intent intent = new Intent(this, LoginActivity.class);
                Notification.show(this, 3241, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), intent);
                Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            }
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);


    }

    private void forcedLogout() {
        AppPreference appPreference = new AppPreference(getBaseContext());
        appPreference.setForceLogout(true);
        Intent intent = new Intent();
        intent.setAction(getString(R.string.force_logout_broadcast));
        sendBroadcast(intent);
    }

    private void sendData(Map<String, String> data) {
        try {
            int code = Integer.parseInt(Objects.requireNonNull(data.get("code")));
            switch (code) {
                case Constants.FORCED_LOGOUT:
                    forcedLogout();
                    break;
                case Constants.CLASS_NOTICE_UPDATE:
                    setClassNotice(data);
                    break;
                case Constants.BLOG_UPDATE:
                    blogUpdate(data);
                    break;
                case Constants.NOTICE_UPDATE:
                    noticeUpdate(data);
                    break;
                case Constants.JOB_UPDATE:
                    jobUpdate(data);
                    break;
                case Constants.IMAGE_UPDATE:
                    ImageUpdate(data);
                    break;
                case Constants.USER_UPDATE:
                    userUpdate(data);
                    break;
                case Constants.CAREER_UPDATE:
                    careerUpdate(data);
                    break;
                case Constants.ALL_DATA_UPDATE:
                    allDataUpdate(data);
                    break;
                case Constants.BATCH_UPDATE:
                    batchUpdate(data);
                    break;
                case Constants.ROUTINE_UPDATE:
                    routineUpdate(data);
                    break;
                case Constants.TEACHER_UPDATE:
                    teacherUpdate(data);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void teacherUpdate(Map<String, String> data) {
        new UpdateTeacher(getBaseContext()).execute();
    }

    private void batchUpdate(Map<String, String> data) {
        AppPreference appPreference = new AppPreference(getBaseContext());
        appPreference.setUserCourse(data.get("course"));
        appPreference.setUserCourseCode(Integer.parseInt(Objects.requireNonNull(data.get("course_code"))));
        appPreference.setUserSem(Integer.parseInt(Objects.requireNonNull(data.get("sem"))));
        appPreference.setUserType(Integer.parseInt(Objects.requireNonNull(data.get("user_type"))));
    }

    private void allDataUpdate(Map<String, String> data) {
        new UpdateAllData(getBaseContext()).execute();
    }

    private void careerUpdate(Map<String, String> data) {
        new UpdateCareer(getContext()).execute();
    }

    private void userUpdate(Map<String, String> data) {
        new UpdatePhirePawa(getContext()).execute();

    }

    private void ImageUpdate(Map<String, String> data) {
        new UpdateImage(getContext()).execute();
    }

    private void routineUpdate(Map<String, String> data) {
        new UpdateRoutine(getContext()).execute();
    }

    private void jobUpdate(Map<String, String> data) {
        Intent intent1 = new Intent(getContext(), HomeActivity.class);
        intent1.putExtra("item",3);
        new UpdateJob(getContext()).execute();
        Notification.show(getContext(), new Random().nextInt(9999), data.get("title"), data.get("message"), intent1);
    }

    private void noticeUpdate(Map<String, String> data) {
        Intent intent1 = new Intent(getContext(), HomeActivity.class);
        intent1.putExtra("item",2);
        new UpdateNotice(getContext()).execute();
        Notification.show(getContext(), new Random().nextInt(9999), data.get("title"), data.get("message"), intent1);
    }

    private void blogUpdate(Map<String, String> data) {
        Intent intent1 = new Intent(getContext(), HomeActivity.class);
        intent1.putExtra("item",0);
        new UpdateBlog(getContext()).execute();
        Notification.show(getContext(), new Random().nextInt(9999), data.get("title"), data.get("message"), intent1);
    }

    private void setClassNotice(Map<String, String> data) {
        try {
            AppPreference appPreference = new AppPreference(getContext());
            if (appPreference.getUserType()==2) {
                ClassNoticeModel classNoticeModel = new ClassNoticeModel(data.get("message"), new Date(data.get("date")), data.get("notice_by"), data.get("sem"), data.get("url"));
                AppDatabase database = AppDatabase.getDatabase(getContext());
                NoticeDAO noticeDAO = database.noticeDAO();
                noticeDAO.insertClassNotice(classNoticeModel);
                Intent intent = new Intent();


                appPreference.setNotificationCount(appPreference.getNotificationCount() + 1);
                Intent intent3 = new Intent();
                intent3.setAction(getString(R.string.notification_count_broadcast));
                intent.setAction(getString(R.string.class_notice_broadcast_receiver));
                sendBroadcast(intent);
                sendBroadcast(intent3);
                Intent intent1 = new Intent(getContext(), ClassNoticeActivity.class);
                Notification.show(getContext(), new Random().nextInt(9999), data.get("title"), data.get("message"), intent1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static class UpdateBlog extends AsyncTask<Void, Void, String> {
        BlogDAO blogDAO;
        private WeakReference<Context> weakReference;

        UpdateBlog(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            blogDAO = db.blogDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiBlogUpdateModel> call = service.getBlog(new ApiCredentialWithUserId());
                call.enqueue(new Callback<ApiBlogUpdateModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiBlogUpdateModel> call, @NonNull Response<ApiBlogUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiBlogUpdateModel apiBlogUpdateModel = response.body();
                                    Log.e(TAG, "Api blog Response:\n" + response.body().toString());
                                    List<BlogModel> blogModels = apiBlogUpdateModel.getBlogModels();

                                    Thread blogThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!Constants.UPDATING_BLOG) {
                                                        Constants.UPDATING_BLOG = true;
                                                        blogDAO.deleteAllBlog();
                                                        blogDAO.insertBlog(blogModels);
                                                        Constants.UPDATING_BLOG = false;
                                                    }

                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.blog_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    blogThread.start();

                                    blogThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiBlogUpdateModel> call, @NonNull Throwable t) {
                        Log.e(TAG, "OnFailure " + t.getMessage());
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private static class UpdateNotice extends AsyncTask<Void, Void, String> {
        NoticeDAO noticeDAO;
        private WeakReference<Context> weakReference;

        UpdateNotice(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            noticeDAO = db.noticeDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiNoticeUpdateModel> call = service.getNotice(new ApiCredentialWithUserId());
                call.enqueue(new Callback<ApiNoticeUpdateModel>() {
                    @Override
                    public void onResponse(Call<ApiNoticeUpdateModel> call, Response<ApiNoticeUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiNoticeUpdateModel apiNoticeUpdateModel = response.body();
                                    Log.e(TAG, "Api ntice Response:\n" + response.body().toString());
                                    List<NoticeModel> noticeModels = apiNoticeUpdateModel.getNoticeModels();

                                    Thread noticeThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {

                                                    if (!Constants.UPDATING_NOTICE) {
                                                        Constants.UPDATING_NOTICE = true;
                                                        noticeDAO.deleteAllNotice();
                                                        noticeDAO.insertNotice(noticeModels);
                                                        Constants.UPDATING_NOTICE = false;
                                                    }
                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.notice_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    noticeThread.start();

                                    noticeThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiNoticeUpdateModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private static class UpdateImage extends AsyncTask<Void, Void, String> {
        ImageGalleryDAO imageGalleryDAO;
        private WeakReference<Context> weakReference;

        UpdateImage(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            imageGalleryDAO = db.imageGalleryDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiImageUpdateModel> call = service.getImage(new ApiCredentialWithUserId());
                call.enqueue(new Callback<ApiImageUpdateModel>() {
                    @Override
                    public void onResponse(Call<ApiImageUpdateModel> call, Response<ApiImageUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiImageUpdateModel apiImageUpdateModel = response.body();
                                    Log.e(TAG, "Api image Response:\n" + response.body().toString());
                                    List<ImageGalleryModel> imageGalleryModels = apiImageUpdateModel.getImageGalleryModels();

                                    Thread imageGalleryThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {

                                                    if (!Constants.UPDATING_IMAGE_GALLERY) {
                                                        Constants.UPDATING_IMAGE_GALLERY = true;
                                                        imageGalleryDAO.deleteAllImages();
                                                        imageGalleryDAO.insertImage(imageGalleryModels);
                                                        Constants.UPDATING_IMAGE_GALLERY = false;
                                                    }
                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.image_gallery_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    imageGalleryThread.start();

                                    imageGalleryThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiImageUpdateModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private static class UpdateJob extends AsyncTask<Void, Void, String> {
        JobDAO jobDAO;
        private WeakReference<Context> weakReference;

        UpdateJob(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            jobDAO = db.jobDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiJobPostUpdateModel> call = service.getJob(new ApiCredentialWithUserId());
                call.enqueue(new Callback<ApiJobPostUpdateModel>() {
                    @Override
                    public void onResponse(Call<ApiJobPostUpdateModel> call, Response<ApiJobPostUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiJobPostUpdateModel apiJobPostUpdateModel = response.body();
                                    Log.e(TAG, "Api job Response:\n" + response.body().toString());
                                    List<JobFileModel> jobFileModels = apiJobPostUpdateModel.getJobFileModels();
                                    List<JobModel> jobModels = apiJobPostUpdateModel.getJobModels();

                                    Thread jobThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {

                                                    if (!Constants.UPDATING_JOB) {
                                                        Constants.UPDATING_JOB = true;
                                                        jobDAO.deleteAllJob();
                                                        jobDAO.deleteAllJobFile();
                                                        jobDAO.insertJobFile(jobFileModels);
                                                        jobDAO.insertJob(jobModels);
                                                        Constants.UPDATING_JOB = false;
                                                    }
                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.job_post_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    jobThread.start();

                                    jobThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiJobPostUpdateModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private static class UpdatePhirePawa extends AsyncTask<Void, Void, String> {
        PhirePawaProfileDAO phirePawaProfileDAO;
        private WeakReference<Context> weakReference;

        UpdatePhirePawa(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            phirePawaProfileDAO = db.phirePawaProfileDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiPhirePawaUpdateModel> call = service.getPhirePawa(new ApiCredentialWithUserId());
                call.enqueue(new Callback<ApiPhirePawaUpdateModel>() {
                    @Override
                    public void onResponse(Call<ApiPhirePawaUpdateModel> call, Response<ApiPhirePawaUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiPhirePawaUpdateModel apiPhirePawaUpdateModel = response.body();
                                    Log.e(TAG, "Api phire pawa Response:\n" + response.body().toString());
                                    List<UserModel> userModels = apiPhirePawaUpdateModel.getUserModels();

                                    Thread userThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {

                                                    if (!Constants.UPDATING_USER) {
                                                        Constants.UPDATING_USER = true;
                                                        phirePawaProfileDAO.deleteAllUser();
                                                        phirePawaProfileDAO.insertUsers(userModels);
                                                        Constants.UPDATING_USER = false;
                                                    }
                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.phire_pawa_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    userThread.start();

                                    userThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiPhirePawaUpdateModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private static class UpdateCareer extends AsyncTask<Void, Void, String> {
        PhirePawaProfileDAO phirePawaProfileDAO;
        private WeakReference<Context> weakReference;

        UpdateCareer(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            phirePawaProfileDAO = db.phirePawaProfileDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiCareerUpdateModel> call = service.getCareer(new ApiCredentialWithUserId());

                call.enqueue(new Callback<ApiCareerUpdateModel>() {
                    @Override
                    public void onResponse(Call<ApiCareerUpdateModel> call, Response<ApiCareerUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiCareerUpdateModel apiCareerUpdateModel = response.body();
                                    Log.e(TAG, "Api career Response:\n" + response.body().toString());
                                    List<CareerModel> careerModels = apiCareerUpdateModel.getCareerModels();

                                    Thread careerThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!Constants.UPDATING_CAREER) {
                                                        Constants.UPDATING_CAREER = true;
                                                        phirePawaProfileDAO.deleteAllCareer();
                                                        phirePawaProfileDAO.insertCareer(careerModels);
                                                        Constants.UPDATING_CAREER = false;
                                                    }

                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.career_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    careerThread.start();

                                    careerThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiCareerUpdateModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private static class UpdateAllData extends AsyncTask<Void, Void, String> {

        private WeakReference<Context> weakReference;

        UpdateAllData(Context context) {
            weakReference = new WeakReference<>(context);

        }

        @Override
        protected String doInBackground(Void... voids) {
          GetDataService.updateData(weakReference.get());
            return null;
        }


    }

    private static class UpdateRoutine extends AsyncTask<Void, Void, String> {
        RoutineDAO routineDAO;
        private WeakReference<Context> weakReference;

        UpdateRoutine(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            routineDAO = db.routineDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiRoutineUpdateModel> call = service.getRoutine(new ApiCredentialWithUserId());
                call.enqueue(new Callback<ApiRoutineUpdateModel>() {
                    @Override
                    public void onResponse(Call<ApiRoutineUpdateModel> call, Response<ApiRoutineUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiRoutineUpdateModel apiRoutineUpdateModel = response.body();
                                    Log.e(TAG, "Api blog Response:\n" + response.body().toString());
                                    List<RoutineModel> routineModels = apiRoutineUpdateModel.getRoutineModels();

                                    Thread routineThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!Constants.UPDATING_ROUTINE) {
                                                        Constants.UPDATING_ROUTINE = true;
                                                        routineDAO.deleteAllRoutine();
                                                        routineDAO.insertRoutine(routineModels);
                                                        Constants.UPDATING_ROUTINE = false;
                                                    }

                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.routine_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    routineThread.start();

                                    routineThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiRoutineUpdateModel> call, Throwable t) {
                        Log.e(TAG, "OnFailure " + t.getMessage());
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private static class UpdateTeacher extends AsyncTask<Void, Void, String> {
        TeacherDAO teacherDAO;
        private WeakReference<Context> weakReference;

        UpdateTeacher(Context context) {
            weakReference = new WeakReference<>(context);
            AppDatabase db = AppDatabase.getDatabase(weakReference.get());
            teacherDAO = db.teacherDAO();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiTeacherUpdateModel> call = service.getTeacher(new ApiCredentialWithUserId());
                call.enqueue(new Callback<ApiTeacherUpdateModel>() {
                    @Override
                    public void onResponse(Call<ApiTeacherUpdateModel> call, Response<ApiTeacherUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code: " + response.code() + "");
                            if (response.body() != null) {
                                try {
                                    ApiTeacherUpdateModel apiTeacherUpdateModel = response.body();
                                    Log.e(TAG, "Api blog Response:\n" + response.body().toString());
                                    List<TeacherModel> teacherModels = apiTeacherUpdateModel.getTeacherModels();

                                    Thread teacherThread = new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!Constants.UPDATING_TEACHER) {
                                                        Constants.UPDATING_TEACHER = true;
                                                        teacherDAO.deleteAllTeacher();
                                                        teacherDAO.insertTeacher(teacherModels);
                                                        Constants.UPDATING_TEACHER = false;
                                                    }

                                                }
                                            }
                                    );


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (weakReference.get()!=null) {
                                                Intent in = new Intent(weakReference.get().getString(R.string.teacher_broadcast_receiver));
                                                getContext().sendBroadcast(in);
                                            }

                                        }
                                    });


                                    teacherThread.start();

                                    teacherThread.join();

                                    completed.start();
                                    completed.join();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e(TAG, "Response code: " + response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiTeacherUpdateModel> call, Throwable t) {
                        Log.e(TAG, "OnFailure " + t.getMessage());
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}

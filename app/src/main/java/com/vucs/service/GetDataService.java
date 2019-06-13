package com.vucs.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.vucs.R;
import com.vucs.activity.LoginActivity;
import com.vucs.api.ApiCredentialWithUserId;
import com.vucs.api.ApiUpdateModel;
import com.vucs.api.Service;
import com.vucs.dao.BlogDAO;
import com.vucs.dao.EventDAO;
import com.vucs.dao.ImageGalleryDAO;
import com.vucs.dao.JobDAO;
import com.vucs.dao.NoticeDAO;
import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.Constants;
import com.vucs.helper.Utils;
import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vucs.App.CHANNEL_ID;
import static com.vucs.App.getContext;

public class GetDataService extends IntentService {

    private static final String TAG = "GetDataService";
    private static WeakReference<Context> weakReference;
    public static final int NOTIFICATION_ID = 1;
    private static  BlogDAO blogDAO;
    private static  EventDAO eventDAO;
    private static  ImageGalleryDAO imageGalleryDAO;
    private static  JobDAO jobDAO;
    private static  NoticeDAO noticeDAO;
    private static  PhirePawaProfileDAO phirePawaProfileDAO;

    public GetDataService() {
        super("GetData Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        weakReference = new WeakReference<>(getApplicationContext());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "Starting GetDataService", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Starting GetDataService");
        if (Utils.isNetworkAvailable()) {
            Intent notificationIntent = new Intent(this, LoginActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Uploading claim details")
                    .setContentText("Please wait...")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(NOTIFICATION_ID, notification);
        } else {
            Log.e(TAG, "No internet connection");
        }
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }


    public static void updateData(final Context context) {
        try {
            AppDatabase db = AppDatabase.getDatabase(context);
            blogDAO = db.blogDAO();
            eventDAO = db.eventDAO();
            imageGalleryDAO = db.imageGalleryDAO();
            jobDAO = db.jobDAO();
            noticeDAO = db.noticeDAO();
            phirePawaProfileDAO = db.phirePawaProfileDAO();
            Service service = DataServiceGenerator.createService(Service.class);
            // add another part within the multipart request
            Call<ApiUpdateModel> call = service.getAllData(new ApiCredentialWithUserId());

            call.enqueue(new Callback<ApiUpdateModel>() {
                @Override
                public void onResponse(@NonNull Call<ApiUpdateModel> call, @NonNull Response<ApiUpdateModel> response) {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "Response code: " + response.code() + "");
                        if (response.body() != null) {
                            try {
                            ApiUpdateModel apiUpdateModel = response.body();
                            Log.e(TAG, "Api Response:\n" + response.body().toString());
                             List<CareerModel> careerModels=apiUpdateModel.getCareerModels();
                             List<BlogModel> blogModels=apiUpdateModel.getBlogModels();
                            // List<EventModel> eventModels=apiUpdateModel.getEventModels();

                             List<ImageGalleryModel> imageGalleryModels=apiUpdateModel.getImageGalleryModels();
                             List<JobFileModel> jobFileModels=apiUpdateModel.getJobFileModels();
                             List <JobModel> jobModels=apiUpdateModel.getJobModels();
                             List<NoticeModel> noticeModels=apiUpdateModel.getNoticeModels();
                             List<UserModel> userModels=apiUpdateModel.getUserModels();


                                Thread blogThread = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                if (!Constants.UPDATING_BLOG) {
                                                    Constants.UPDATING_BLOG=true;
                                                    blogDAO.deleteAllBlog();
                                                    blogDAO.insertBlog(blogModels);
                                                    Constants.UPDATING_BLOG=false;
                                                }

                                            }
                                        }
                                );

                                Thread careerThread = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                               if (!Constants.UPDATING_CAREER) {
                                                   Constants.UPDATING_CAREER=true;
                                                  phirePawaProfileDAO.deleteAllCareer();
                                                  phirePawaProfileDAO.insertCareer(careerModels);
                                                  Constants.UPDATING_CAREER=false;
                                               }

                                            }
                                        }
                                );

                               /* Thread eventThread = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                if (!Constants.UPDATING_EVENT) {
                                                    Constants.UPDATING_EVENT=true;
                                                    eventDAO.deleteAllEvent();
                                                    eventDAO.insertEvent(eventModels);
                                                    Constants.UPDATING_EVENT=false;
                                                }
                                            }
                                        }
                                );
*/
                                Thread imageGalleryThread = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                if (!Constants.UPDATING_IMAGE_GALLERY) {
                                                    Constants.UPDATING_IMAGE_GALLERY=true;
                                                    imageGalleryDAO.deleteAllImages();
                                                    imageGalleryDAO.insertImage(imageGalleryModels);
                                                    Constants.UPDATING_IMAGE_GALLERY=false;
                                                }
                                            }
                                        }
                                );

                                Thread jobThread = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                if (!Constants.UPDATING_JOB) {
                                                    Constants.UPDATING_JOB=true;
                                                    jobDAO.deleteAllJob();
                                                    jobDAO.deleteAllJobFile();
                                                    jobDAO.insertJobFile(jobFileModels);
                                                    jobDAO.insertJob(jobModels);
                                                    Constants.UPDATING_JOB=false;
                                                }
                                            }
                                        }
                                );
                                Thread noticeThread = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                if (!Constants.UPDATING_NOTICE) {
                                                    Constants.UPDATING_NOTICE=true;
                                                    noticeDAO.deleteAllNotice();
                                                    noticeDAO.insertNotice(noticeModels);
                                                    Constants.UPDATING_NOTICE=false;
                                                }
                                            }
                                        }
                                );
                                Thread userThread = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                if (!Constants.UPDATING_USER) {
                                                    Constants.UPDATING_USER=true;
                                                    phirePawaProfileDAO.deleteAllUser();
                                                    phirePawaProfileDAO.insertUsers(userModels);
                                                    Constants.UPDATING_USER=false;
                                                }
                                            }
                                        }
                                );

                                Thread completed = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e(TAG, "Completed updateData");

                                            Intent in = new Intent(getContext().getString(R.string.fetch_all_data_broad_cast));
                                            in.putExtra(getContext().getString(R.string.dashboard_receiver_action),getContext().getString(R.string.get_data_action));
                                            getContext().sendBroadcast(in);
                                            Log.e(TAG,"Sending getData complete broadcast");

                                    }
                                });

                               // eventThread.start();
                                blogThread.start();
                                careerThread.start();
                                imageGalleryThread.start();

                                jobThread.start();
                                noticeThread.start();
                                userThread.start();

                                //eventThread.join();
                                blogThread.join();
                                careerThread.join();
                                imageGalleryThread.join();
                                jobThread.join();
                                noticeThread.join();
                                userThread.join();

                                completed.start();
                                completed.join();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Intent in = new Intent(getContext().getString(R.string.app_name));
                                in.putExtra(getContext().getString(R.string.dashboard_receiver_action),getContext().getString(R.string.get_data_on_failure_action));
                                getContext().sendBroadcast(in);
                            }
                        }
                    } else {
                        Log.e(TAG, "Response code: " + response.code() + "");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiUpdateModel> call, @NonNull Throwable t) {
                    Log.e(TAG, "OnFailure " + t.getMessage());
                    Intent in = new Intent(getContext().getString(R.string.app_name));
                    in.putExtra(getContext().getString(R.string.dashboard_receiver_action),getContext().getString(R.string.get_data_on_failure_action));
                    getContext().sendBroadcast(in);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Intent in = new Intent(getContext().getString(R.string.app_name));
            in.putExtra(getContext().getString(R.string.dashboard_receiver_action),getContext().getString(R.string.get_data_on_failure_action));
            getContext().sendBroadcast(in);
        }
    }
}
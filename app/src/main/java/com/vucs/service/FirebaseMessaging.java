package com.vucs.service;

import android.app.Activity;
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
import com.vucs.api.ApiCredentialWithUserId;
import com.vucs.api.ApiResponseModel;
import com.vucs.api.ApiUpdateModel;
import com.vucs.api.ApiUploadFirebaseTokenModel;
import com.vucs.api.Service;
import com.vucs.dao.BlogDAO;
import com.vucs.dao.NoticeDAO;
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
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blogUpdate(Map<String, String> data) {
        Intent intent1 = new Intent(getBaseContext(), HomeActivity.class);
        new UpdateBlog(getBaseContext()).execute();
        Notification.show(getBaseContext(), new Random().nextInt(9999), data.get("title"), data.get("message"), intent1);
    }

    private void setClassNotice(Map<String, String> data) {
        try {
            ClassNoticeModel classNoticeModel = new ClassNoticeModel(data.get("message"), new Date(data.get("date")), data.get("notice_by"), Integer.parseInt(data.get("sem")));
            AppDatabase database = AppDatabase.getDatabase(getBaseContext());
            NoticeDAO noticeDAO = database.noticeDAO();
            noticeDAO.insertClassNotice(classNoticeModel);
            Intent intent = new Intent();
            intent.setAction(getString(R.string.class_notice_broadcast_receiver));
            sendBroadcast(intent);
            AppPreference appPreference =new AppPreference(getBaseContext());
            appPreference.setNotificationCount(appPreference.getNotificationCount()+1);
            Intent intent3 = new Intent();
            intent3.setAction(getString(R.string.notification_count_broadcast));
            sendBroadcast(intent3);
            Intent intent1 = new Intent(getBaseContext(), ClassNoticeActivity.class);
            Notification.show(getBaseContext(), new Random().nextInt(9999), data.get("title"), data.get("message"), intent1);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static class UpdateBlog extends AsyncTask<Void, Void, String> {
        private WeakReference<Context> weakReference;
        BlogDAO blogDAO;

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
                                    List<BlogModel> blogModels=apiBlogUpdateModel.getBlogModels();

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


                                    Thread completed = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                                Intent in = new Intent(weakReference.get().getString(R.string.blog_broadcast_receiver));
                                                getContext().sendBroadcast(in);

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
}

package com.vucs.service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vucs.App;
import com.vucs.activity.LoginActivity;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Notification;

import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final  String TAG= FirebaseMessaging.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG,"  message received");
        if (remoteMessage!=null){
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "Message data payload: " + remoteMessage.getData());
                try {
                    JSONObject data;
                    data = new JSONObject(remoteMessage.getData().toString());
                    JSONObject notificationData = data.getJSONObject("data");
                    //sesendNotification(notificationData.getString("title"),notificationData.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            // Check if message contains a notification payload.
            else if (remoteMessage.getNotification() != null) {
                Intent intent = new Intent(this, LoginActivity.class);
                Notification.show(this,3241, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),intent);
                Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            }
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        upLoadToken(s);


    }
    public static void upLoadToken(String token){
        Log.e(TAG,"upload token...");
        AppPreference appPreference = new AppPreference(App.getContext());
        appPreference.setTokenGenerated(true);


    }
}

package com.vucs;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import java.lang.ref.WeakReference;

public class App extends Application {

    public static final String CHANNEL_ID = "TrackPayServiceChannel";
    private static WeakReference<Context> context;
    private static WeakReference<Resources> resources;

    public static Context getContext() {
        return context.get();
    }

    public static Resources getResource() {
        return resources.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<Context>(this);
        resources = new WeakReference<Resources>(getResources());
        //createNotificationChannel();
    }

   /* private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "TrackPay Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }*/
}

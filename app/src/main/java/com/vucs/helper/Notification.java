package com.vucs.helper;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.filelibrary.Utils;
import com.vucs.HomeActivity;
import com.vucs.R;

import java.io.File;

import androidx.core.app.NotificationCompat;
import androidx.core.app.ShareCompat;

public class Notification {
    public  static void show(Context context,long id,String title,String desc,String file_path){
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        String s[]=file_path.split("\\.");
        Log.e("ss=",s[s.length-1]);
        Intent notificationIntent = new Intent();
        Uri uri = Uri.parse(file_path);
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            // FileUri - Convert it to contentUri.

            File file = new File(uri.getPath());
            uri = Utils.fileToUri(context,new File(uri.getPath()));
        }
        notificationIntent.setAction(Intent.ACTION_VIEW);
        notificationIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (s[s.length-1].equals("jpg")||s[s.length-1].equals("png")) {
            notificationIntent.setDataAndType(uri, "image/*");

        }
        else if (s[s.length-1].equals("pdf")){
            notificationIntent.setDataAndType(uri, "application/pdf");

        }
        else {
            notificationIntent.setDataAndType(uri, "*/*");
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)id,
                notificationIntent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String notification_Channel = "Channel_id";
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context,notification_Channel)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setColor(context.getResources().getColor(R.color.colorPrimary1))
                .setContentText(desc).setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notification_Channel, "My notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.MAGENTA);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify((int)id, mNotifyBuilder.build());
    }
}

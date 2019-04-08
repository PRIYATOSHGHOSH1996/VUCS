package com.vucs.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.vucs.helper.Notification;

import static android.content.Context.DOWNLOAD_SERVICE;

public class Broadcastreceiver extends BroadcastReceiver {
    private static final String TAG = "Broadcast Receive";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"receive");
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            Log.e(TAG,"receive from download");

            long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(id != -1){
                Bundle extras = intent.getExtras();
                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
              DownloadManager  mDManager = (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);
                Cursor c = mDManager.query(q);

                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                       String filename = filePath.substring( filePath.lastIndexOf('/')+1, filePath.length() );
                       Log.e(TAG,"path "+filePath);


                    }
                }

                Notification.show(context,id,"Download Complete",c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE)),c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
                c.close();
            }
        }

    }
}

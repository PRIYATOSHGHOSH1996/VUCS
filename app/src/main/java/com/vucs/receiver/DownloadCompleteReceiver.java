package com.vucs.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.filelibrary.Utils;
import com.vucs.helper.Notification;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "Broadcast Receive";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (id != -1) {
                Bundle extras = intent.getExtras();
                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
                DownloadManager mDManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                Cursor c = mDManager.query(q);

                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        String mimeType = c.getString(c.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                        String fileName = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                        Intent notificationIntent = new Intent();
                        Uri uri = Uri.parse(filePath);
                        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
                            uri = Utils.fileToUri(context, new File(uri.getPath()));
                        }
                        notificationIntent.setAction(Intent.ACTION_VIEW);
                        notificationIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        notificationIntent.setDataAndType(uri, mimeType);
                        Notification.show(context, id, "Download Complete", fileName, notificationIntent);

                    }
                }


                c.close();
            }
        }

    }
}

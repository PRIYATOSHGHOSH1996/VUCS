package com.vucs.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vucs.R;
import com.vucs.adapters.RecyclerViewClassNoticeAdapter;
import com.vucs.adapters.RecyclerViewNoticeAdapter;
import com.vucs.dao.NoticeDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Snackbar;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;
import com.vucs.model.ClassNoticeFileModel;
import com.vucs.model.ClassNoticeModel;
import com.vucs.model.NoticeFileModel;
import com.vucs.model.NoticeModel;
import com.vucs.viewmodel.NoticeViewModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

public class ClassNoticeActivity extends AppCompatActivity implements RecyclerViewClassNoticeAdapter.CallbackInterface {
    AppPreference appPreference;
    private String TAG = "classnoticeActivity";
    private RecyclerViewClassNoticeAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private NoticeViewModel noticeViewModel;
    private ClassNoticeModel classNoticeModel;
    RecyclerView recyclerView;
    int position;
    private static final Integer WRITE_STORAGE_PERMISSION = 121;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_class_notice);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.activity_transation);
        getWindow().setEnterTransition(transition);
        getWindow().setReturnTransition(new Explode());


        try {
            appPreference = new AppPreference(this);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            getSupportActionBar().setTitle("");
            TextView header_text = findViewById(R.id.header_text);
            header_text.setText("Class Notice");
            initView();
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    updateAdapter();
                }
            };
            if (appPreference.getUserId().equals("")){
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.scale_fade_up, R.anim.no_anim);
            }
        } catch (Exception e) {
            Utils.appendLog(TAG + ":oncreate: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    private void initView() {
        try {
            recyclerView = findViewById(R.id.recycler_view);
            adapter = new RecyclerViewClassNoticeAdapter(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            noticeViewModel = ViewModelProviders.of(this).get(NoticeViewModel.class);
            updateAdapter();
            recyclerView.setAdapter(adapter);

            FloatingActionButton floatingActionButton = findViewById(R.id.add);
            if (appPreference.getUserType() == 0) {
                floatingActionButton.setVisibility(View.VISIBLE);
            } else {
                floatingActionButton.setVisibility(View.GONE);
            }
            floatingActionButton.setOnClickListener(v -> {
                startActivity(new Intent(this, AddClassNoticeActivity.class));
                overridePendingTransition(R.anim.scale_fade_up, R.anim.no_anim);
            });
        } catch (Exception e) {
            Utils.appendLog(TAG + ":iniView: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

    }

    private void updateAdapter() {
        try {
            appPreference.setNotificationCount(0);
            List<ClassNoticeModel> list = noticeViewModel.getAllClassNotice();
            if(list.size() == 0){
                findViewById(R.id.empty_notification_layout).setVisibility(View.VISIBLE);
            }
            else {
                findViewById(R.id.empty_notification_layout).setVisibility(View.GONE);
            }
            adapter.addNotice(list);
        } catch (Exception e) {
            Utils.appendLog(TAG + ":update adapater: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            updateAdapter();
            registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.class_notice_broadcast_receiver)));
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onresume: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onpause: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            }
            else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                Snackbar.withRetryStoragePermission(this,findViewById(R.id.parent),"Please give storage permission.");
            } else {
                Snackbar.show(this,findViewById(R.id.parent),"Please give storage permission");
            }
        }

    }
    private void download() {
        try {
            if (classNoticeModel != null && !classNoticeModel.getFileUrl().equals("default")&& !classNoticeModel.getFileUrl().equals("")) {
                if (Utils.isNetworkAvailable()) {
                    new DownloadFile(ClassNoticeActivity.this,position,classNoticeModel).execute();
                    /*Toast.makeText(this, "Downloading ...");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri Download_Uri = Uri.parse(classNoticeModel.getFileUrl());

                    String s = URLUtil.guessFileName(classNoticeModel.getFileUrl(), null, null);
                    String s1[] = s.split("\\.");
                    s = s1[s1.length - 1];
                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle(classNoticeModel.getNoticeTitle());
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/VUCS Notice/" + "/" + classNoticeModel.getNoticeTitle() + "." + s);


                    downloadManager.enqueue(request);*/
                } else {
                    Snackbar.withNetworkAction(this,findViewById(R.id.parent),"Internet connection not available");
                }
            }
        } catch (Exception e) {
            Utils.appendLog(TAG + ":download: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();

        }

    }

    @Override
    public void downloadFile(int position, ClassNoticeModel classNoticeModel) {
        this.classNoticeModel = classNoticeModel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                download();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
            }
        } else {
            download();
        }
    }
    private static class DownloadFile extends AsyncTask<String, Integer, String> {
        WeakReference<ClassNoticeActivity> context;
        File file;
        int position;
        ClassNoticeModel classNoticeModel;
        NoticeDAO noticeDAO;
        public DownloadFile(ClassNoticeActivity homeActivity, int position, ClassNoticeModel noticeModel) {
            context = new WeakReference<>(homeActivity);

            this.position=position;
            this.classNoticeModel=noticeModel;
            String s = URLUtil.guessFileName(classNoticeModel.getFileUrl(), null, null);
            String s1[] = s.split("\\.");
            s = s1[s1.length - 1];
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath()+ "/VUCS/ClassNotice/");
            // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
            file = new File(wallpaperDirectory, noticeModel.getNoticeTitle()+noticeModel.getNoticeId() + "." + s);
            AppDatabase db=AppDatabase.getDatabase(context.get());
            noticeDAO = db.noticeDAO();

        }

        @Override
        protected String doInBackground(String... sUrl) {
            try {

                URL url = new URL(classNoticeModel.getFileUrl());

                URLConnection connection = url.openConnection();
                connection.connect();
                // this will be useful so that you can show a typical 0-100% progress bar
                int fileLength = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                OutputStream output = new FileOutputStream(file.getPath() );

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            try {
                if (context.get()!=null){
                    RecyclerViewClassNoticeAdapter.MyViewHolder holder=((RecyclerViewClassNoticeAdapter.MyViewHolder) context.get().recyclerView.findViewHolderForAdapterPosition(position));
                    if (progress[0]>=100){
                        ClassNoticeFileModel classNoticeFileModel = new ClassNoticeFileModel(classNoticeModel.getNoticeId(),file.getPath());
                        noticeDAO.insertClassNoticeFile(classNoticeFileModel);
                        holder.progressBar.setVisibility(View.GONE);
                        ((RecyclerViewClassNoticeAdapter)context.get().recyclerView.getAdapter()).sparseBooleanArray.put(position,false);
                    }
                    else {
                        holder.progressBar.setIndeterminate(false);
                        holder.progressBar.setProgress(progress[0]);
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (context.get()!=null){
                RecyclerViewClassNoticeAdapter.MyViewHolder holder=((RecyclerViewClassNoticeAdapter.MyViewHolder) context.get().recyclerView.findViewHolderForAdapterPosition(position));
                holder.progressBar.setVisibility(View.VISIBLE);
                ((RecyclerViewClassNoticeAdapter)context.get().recyclerView.getAdapter()).sparseBooleanArray.put(position,true);
            }
        }
    }
}

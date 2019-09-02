package com.vucs.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.vucs.helper.AppPreference;
import com.vucs.helper.Snackbar;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;
import com.vucs.model.ClassNoticeModel;
import com.vucs.viewmodel.NoticeViewModel;

import java.util.Date;
import java.util.List;

public class ClassNoticeActivity extends AppCompatActivity implements RecyclerViewClassNoticeAdapter.CallbackInterface {
    AppPreference appPreference;
    private String TAG = "classnoticeActivity";
    private RecyclerViewClassNoticeAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private NoticeViewModel noticeViewModel;
    private ClassNoticeModel classNoticeModel;
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
        } catch (Exception e) {
            Utils.appendLog(TAG + ":oncreate: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    private void initView() {
        try {
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
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
        Log.e(TAG, "onresume");
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
        Log.e(TAG, "onpause");
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

    @Override
    public void downloadFile(ClassNoticeModel classNoticeModel) {
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
    private void download() {
        try {
            if (classNoticeModel != null && !classNoticeModel.getFileUrl().equals("default")&& !classNoticeModel.getFileUrl().equals("")) {
                if (Utils.isNetworkAvailable()) {
                    Toast.makeText(this, "Downloading ...");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri Download_Uri = Uri.parse(classNoticeModel.getFileUrl());

                    String s = URLUtil.guessFileName(classNoticeModel.getFileUrl(), null, null);
                    String s1[] = s.split("\\.");
                    s = s1[s1.length - 1];
                    Log.e("fie name with ex = ", s);
                    Log.e("fie name = ", s1.length + "");
                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle(classNoticeModel.getNoticeTitle());
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/VUCS Notice/" + "/" + classNoticeModel.getNoticeTitle() + "." + s);


                    downloadManager.enqueue(request);
                } else {
                    Snackbar.withNetworkAction(this,findViewById(R.id.parent),"Internet connection not available");
                }
            }
        } catch (Exception e) {
            Utils.appendLog(TAG + ":download: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();

        }

    }
}

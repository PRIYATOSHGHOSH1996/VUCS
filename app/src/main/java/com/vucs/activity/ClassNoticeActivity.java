package com.vucs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vucs.R;
import com.vucs.adapters.RecyclerViewClassNoticeAdapter;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Utils;
import com.vucs.viewmodel.NoticeViewModel;

import java.util.Date;

public class ClassNoticeActivity extends AppCompatActivity {
    AppPreference appPreference;
    private String TAG = "classnoticeActivity";
    private RecyclerViewClassNoticeAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private NoticeViewModel noticeViewModel;

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
                overridePendingTransition(R.anim.scale_up, R.anim.no_anim);
            });
        } catch (Exception e) {
            Utils.appendLog(TAG + ":iniView: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

    }

    private void updateAdapter() {
        try {
            adapter.addNotice(noticeViewModel.getAllClassNotice());
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
}

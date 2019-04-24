package com.vucs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.adapters.RecyclerViewNoticeAdapter;
import com.vucs.helper.Utils;
import com.vucs.viewmodel.NoticeViewModel;

import java.util.Date;

public class NoticeFragment extends Fragment {

    private String TAG = "noticeFragment";
    private View view = null;
    private RecyclerViewNoticeAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private NoticeViewModel noticeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        try {
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
        return view;
    }

    private void initView() {
        try {
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            adapter = new RecyclerViewNoticeAdapter(getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            noticeViewModel = ViewModelProviders.of(this).get(NoticeViewModel.class);
            updateAdapter();
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Utils.appendLog(TAG + ":iniView: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

    }

    private void updateAdapter() {
        try {
            adapter.addNotice(noticeViewModel.getAllNotice());
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
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.notice_broadcast_receiver)));
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
            getContext().unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onpause: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }
}

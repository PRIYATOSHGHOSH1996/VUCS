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

import com.vucs.R;
import com.vucs.adapters.RecyclerViewNoticeAdapter;
import com.vucs.viewmodel.NoticeViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeFragment extends Fragment {

    String TAG = "noticeFragment";
    private View view;
    private RecyclerViewNoticeAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private NoticeViewModel noticeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        initView();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateAdapter();
            }
        };
        return view;
    }

    private void initView() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewNoticeAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeViewModel = ViewModelProviders.of(this).get(NoticeViewModel.class);
        updateAdapter();
        recyclerView.setAdapter(adapter);

    }

    private void updateAdapter() {
        adapter.addNotice(noticeViewModel.getAllNotice());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            updateAdapter();
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.notice_broadcast_receiver)));
        } catch (Exception e) {
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
            e.printStackTrace();
        }
    }
}

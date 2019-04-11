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

import androidx.fragment.app.Fragment;


public class TeachersFragment extends Fragment {
    String TAG = "teachersFragment";
    private View view;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teachers, container, false);
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

    }

    private void updateAdapter() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            updateAdapter();
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.teacher_broadcast_receiver)));
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

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
import com.vucs.adapters.RecyclerViewBlogAdapter;
import com.vucs.adapters.RecyclerViewJobAdapter;
import com.vucs.helper.Utils;
import com.vucs.viewmodel.BlogViewModel;
import com.vucs.viewmodel.JobViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;


public class JobPostFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewJobAdapter adapter;
    private JobViewModel jobViewModel;
    String TAG = "JobpostFragment";
    private View view;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_job_post, container, false);
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
        try {

            recyclerView = view.findViewById(R.id.recycler_view);
            adapter = new RecyclerViewJobAdapter(getContext());
            recyclerView.setHasFixedSize(true);
            jobViewModel = ViewModelProviders.of(this).get(JobViewModel.class);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            updateAdapter();
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Utils.appendLog(TAG+":iniView: "+e.getMessage()+"Date :"+new Date());
            e.printStackTrace();

        }
    }

    private void updateAdapter() {
        try {
            adapter.addJob(jobViewModel.getAllJob());
        } catch (Exception e) {
            Utils.appendLog(TAG+":update adapter: "+e.getMessage()+"Date :"+new Date());
            e.printStackTrace();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            updateAdapter();
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.job_post_broadcast_receiver)));
        } catch (Exception e) {
            Utils.appendLog(TAG+":onresume: "+e.getMessage()+"Date :"+new Date());
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
            Utils.appendLog(TAG+":onpause: "+e.getMessage()+"Date :"+new Date());
            e.printStackTrace();
        }
    }
}

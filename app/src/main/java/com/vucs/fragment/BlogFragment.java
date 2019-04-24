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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.adapters.RecyclerViewBlogAdapter;
import com.vucs.helper.Utils;
import com.vucs.viewmodel.BlogViewModel;

import java.util.Date;


public class BlogFragment extends Fragment {
    private String TAG = "BlogFragment";
    private View view = null;
    private RecyclerView recyclerView;
    private RecyclerViewBlogAdapter adapter;
    private BlogViewModel blogViewModel;
    private BroadcastReceiver broadcastReceiver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blog, container, false);
        try {
            initView();
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    updateAdapter();
                }
            };
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onCreate: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

        return view;
    }

    private void initView() {
        try {

            recyclerView = view.findViewById(R.id.recycler_view);
            adapter = new RecyclerViewBlogAdapter(getContext());
            recyclerView.setHasFixedSize(true);
            blogViewModel = ViewModelProviders.of(this).get(BlogViewModel.class);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);
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
            adapter.addBlog(blogViewModel.getAllBlog());
        } catch (Exception e) {
            Utils.appendLog(TAG + ":update adapter: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            updateAdapter();
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.blog_broadcast_receiver)));
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

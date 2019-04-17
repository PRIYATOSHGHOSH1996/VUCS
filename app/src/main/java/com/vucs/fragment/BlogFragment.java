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

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.vucs.R;
import com.vucs.adapters.RecyclerViewBlogAdapter;
import com.vucs.viewmodel.BlogViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;



public class BlogFragment extends Fragment {
    String TAG = "BlogFragment";
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewBlogAdapter adapter;
    private BlogViewModel blogViewModel;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blog, container, false);
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
        recyclerView = view.findViewById(R.id.recycler_view);
       FastScroller fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        adapter = new RecyclerViewBlogAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        blogViewModel = ViewModelProviders.of(this).get(BlogViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        updateAdapter();
        recyclerView.setAdapter(adapter);
        fastScroller.setRecyclerView(recyclerView);

        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


    }

    private void updateAdapter() {
        adapter.addBlog(blogViewModel.getAllBlog());

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            updateAdapter();
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.blog_broadcast_receiver)));
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

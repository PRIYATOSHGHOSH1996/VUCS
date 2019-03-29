package com.vucs.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vucs.R;
import com.vucs.adapters.RecyclerViewBlogAdapter;
import com.vucs.adapters.RecyclerViewNoticeAdapter;
import com.vucs.model.BlogModel;
import com.vucs.model.NoticeModel;
import com.vucs.viewmodel.BlogViewModel;
import com.vucs.viewmodel.NoticeViewModel;

import java.util.List;

public class NoticeFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewNoticeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notice, container, false);
        initView();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewNoticeAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        NoticeViewModel noticeViewModel = ViewModelProviders.of(this).get(NoticeViewModel.class);
        noticeViewModel.getAllNotice().observe(this, new Observer<List<NoticeModel>>() {
            @Override
            public void onChanged(List<NoticeModel> noticeModels) {
                adapter.addNotice(noticeModels);
            }
        });
    }
}

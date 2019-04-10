package com.vucs.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vucs.R;

import com.vucs.adapters.RecyclerViewNoticeAdapter;
import com.vucs.dao.NoticeDAO;
import com.vucs.dao.NoticeDAOImplementation;


public class NoticeFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewNoticeAdapter adapter;
    private NoticeDAO noticeDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notice, container, false);
        noticeDAO = new NoticeDAOImplementation(getContext());
        initView();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewNoticeAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addNotice(noticeDAO.getAllNotice());
        recyclerView.setAdapter(adapter);

    }
}

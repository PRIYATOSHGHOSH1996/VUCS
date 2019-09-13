package com.vucs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.App;
import com.vucs.R;
import com.vucs.adapters.RecyclerViewTeacherAdapter;
import com.vucs.adapters.RecyclerViewUserAdapter;
import com.vucs.helper.Utils;
import com.vucs.viewmodel.PhirePawaProfileViewModel;
import com.vucs.viewmodel.TeacherViewModel;

import java.util.Date;


public class TeachersFragment extends Fragment {
    String TAG = "teachersFragment";
    private View view;
    private BroadcastReceiver broadcastReceiver;
    private RecyclerViewTeacherAdapter adapter;
    private TeacherViewModel teacherViewModel;

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
        try {
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            adapter = new RecyclerViewTeacherAdapter(getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            teacherViewModel = ViewModelProviders.of(this).get(TeacherViewModel.class);
            updateAdapter();
            recyclerView.setAdapter(adapter);
            //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


        } catch (Exception e) {
            Utils.appendLog(TAG + ":iniView: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    private void updateAdapter() {
        adapter.addTeacher(teacherViewModel.getAllTeacher());
    }

    @Override
    public void onResume() {
        super.onResume();
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
        try {
            getContext().unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

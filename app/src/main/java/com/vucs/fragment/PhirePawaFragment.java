package com.vucs.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.vucs.R;
import com.vucs.adapters.RecyclerViewBlogAdapter;
import com.vucs.adapters.RecyclerViewUserAdapter;
import com.vucs.model.UserModel;
import com.vucs.viewmodel.UserViewModel;

import java.util.Calendar;
import java.util.List;

public class PhirePawaFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewUserAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_phire_pawa, container, false);

       initView();

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewUserAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUser().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                adapter.addUser(userModels);
            }
        });
    }


}

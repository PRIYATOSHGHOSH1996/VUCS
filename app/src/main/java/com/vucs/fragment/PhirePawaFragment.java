package com.vucs.fragment;

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

import com.vucs.adapters.RecyclerViewUserAdapter;
import com.vucs.model.PhirePawaProfileModel;
import com.vucs.viewmodel.PhirePawaProfileViewModel;


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

        PhirePawaProfileViewModel phirePawaProfileViewModel = ViewModelProviders.of(this).get(PhirePawaProfileViewModel.class);
        adapter.addUser(phirePawaProfileViewModel.getAllUser());
        recyclerView.setAdapter(adapter);
    }


}

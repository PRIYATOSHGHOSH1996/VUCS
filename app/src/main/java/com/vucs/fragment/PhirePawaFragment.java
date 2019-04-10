package com.vucs.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vucs.R;

import com.vucs.adapters.RecyclerViewUserAdapter;
import com.vucs.dao.PhirePawaDAOImplementation;
import com.vucs.dao.PhirePawaProfileDAO;


public class PhirePawaFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewUserAdapter adapter;
    private PhirePawaProfileDAO phirePawaProfileDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_phire_pawa, container, false);
phirePawaProfileDAO = new PhirePawaDAOImplementation(getContext());
       initView();

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewUserAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addUser(phirePawaProfileDAO.getAllUser());
        recyclerView.setAdapter(adapter);

    }


}

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.App;
import com.vucs.R;
import com.vucs.adapters.RecyclerViewUserAdapter;
import com.vucs.helper.Utils;
import com.vucs.viewmodel.PhirePawaProfileViewModel;

import java.util.Date;

public class PhirePawaFragment extends Fragment {

    String TAG = "PhirepawaFragment";
    private View view;
    private RecyclerViewUserAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private PhirePawaProfileViewModel phirePawaProfileViewModel;
    private int sortCategory = 0;
    private String searchText = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phire_pawa, container, false);

        try {
            initView();
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    updateAdapter();
                }
            };
        } catch (Exception e) {
            Utils.appendLog(TAG + ":oncreate: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

        return view;
    }

    private void initView() {
        try {
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            adapter = new RecyclerViewUserAdapter(getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            phirePawaProfileViewModel = ViewModelProviders.of(this).get(PhirePawaProfileViewModel.class);
            updateAdapter();
            recyclerView.setAdapter(adapter);
            //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
            Spinner spinner = view.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(App.getContext(), R.layout.item_spinner, R.id.textView, getResources().getStringArray(R.array.sort_types_phire_pawa));

            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    // 0 is name
                    // 1 is price
                    sortCategory = position;
                    updateAdapter();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            SearchView searchView = view.findViewById(R.id.search);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchText = newText + "%";

                    updateAdapter();
                    return false;
                }
            });
        } catch (Exception e) {
            Utils.appendLog(TAG + ":iniView: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    private void updateAdapter() {
        try {
            switch (sortCategory) {
                case 0:
                    if (searchText.equals("") || searchText.equals("%")) {
                        adapter.addUser(phirePawaProfileViewModel.getAllUserByName());
                    } else {
                        adapter.addUser(phirePawaProfileViewModel.getAllUserByName(searchText));

                    }
                    break;
                case 1:
                    if (searchText.equals("") || searchText.equals("%")) {
                        adapter.addUser(phirePawaProfileViewModel.getAllUserByBatch());
                    } else {
                        adapter.addUser(phirePawaProfileViewModel.getAllUserByBatch(searchText));

                    }
                    break;
                case 2:
                    if (searchText.equals("") || searchText.equals("%")) {
                        adapter.addUser(phirePawaProfileViewModel.getAllUserByCompany());
                    } else {
                        adapter.addUser(phirePawaProfileViewModel.getAllUserByCompany(searchText));

                    }
                    break;
            }
            Log.e("search text", searchText);
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
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.phire_pawa_broadcast_receiver)));
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

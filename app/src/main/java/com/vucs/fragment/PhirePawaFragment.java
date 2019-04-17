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
import com.vucs.adapters.RecyclerViewUserAdapter;
import com.vucs.viewmodel.PhirePawaProfileViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class PhirePawaFragment extends Fragment {

    String TAG = "PhirepawaFragment";
    private View view;
    private RecyclerViewUserAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private PhirePawaProfileViewModel phirePawaProfileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phire_pawa, container, false);

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
        IndexFastScrollRecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewUserAdapter(getContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        phirePawaProfileViewModel = ViewModelProviders.of(this).get(PhirePawaProfileViewModel.class);
        updateAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setIndexTextSize(12);



        mRecyclerView.setIndexBarTextColor("#FFFFFF");

        mRecyclerView.setIndexBarTextColor(R.color.white);


        mRecyclerView.setIndexBarColor("#33334c");

        mRecyclerView.setIndexBarColor(R.color.colorPrimary1);


        mRecyclerView.setIndexBarCornerRadius(3);



        mRecyclerView.setIndexBarTransparentValue((float) 0.4);



        mRecyclerView.setIndexbarMargin(4);



        mRecyclerView.setIndexbarWidth(40);



        mRecyclerView.setPreviewPadding(2);



        mRecyclerView.setPreviewVisibility(false);







        mRecyclerView.setIndexBarVisibility(true);


        mRecyclerView.setIndexbarHighLateTextColor("#33334c");

        mRecyclerView.setIndexbarHighLateTextColor(R.color.colorAccent);


        mRecyclerView.setIndexBarHighLateTextVisibility(true);

    }

    private void updateAdapter() {
        adapter.addUser(phirePawaProfileViewModel.getAllUser());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            updateAdapter();
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.phire_pawa_broadcast_receiver)));
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

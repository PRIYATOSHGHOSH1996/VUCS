package com.vucs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.adapters.RecyclerViewImageGalleryAdapter;
import com.vucs.adapters.RecyclerViewImageGalleryFolderAdapter;
import com.vucs.helper.Constants;
import com.vucs.recycler_view.MyRecyclerView;
import com.vucs.viewmodel.ImageGalleryViewModel;

public class ImageGalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView header_text;
    ImageGalleryViewModel imageGalleryViewModel;
    private String TAG = "Image Gallery Activity";
    private BroadcastReceiver broadcastReceiver;
    int columnCount= 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.explode);

        setContentView(R.layout.activity_image_gallery);
        transition.excludeTarget(findViewById(R.id.toolbar), true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getWindow().setEnterTransition(transition);

        header_text = findViewById(R.id.header_text);
        recyclerView = findViewById(R.id.recycler_view);

        imageGalleryViewModel = ViewModelProviders.of(this).get(ImageGalleryViewModel.class);


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateAdapter();
            }
        };

    }

    private void showImages(String folderName) {
        final RecyclerViewImageGalleryAdapter recyclerViewImageGalleryAdapter = new RecyclerViewImageGalleryAdapter(this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Do some stuff
            recyclerViewImageGalleryAdapter.setLandscape(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this,Constants.LANDSCAPE_COLUMN_COUNT));
        }else {
            recyclerViewImageGalleryAdapter.setLandscape(false);
            recyclerView.setLayoutManager(new GridLayoutManager(this,Constants.PORTRAIT_COLUMN_COUNT));
        }

        recyclerViewImageGalleryAdapter.addImage(imageGalleryViewModel.getAllImagesByFolder(folderName));
        recyclerView.setAdapter(recyclerViewImageGalleryAdapter);


    }

    private void showFolders() {
        final RecyclerViewImageGalleryFolderAdapter recyclerViewImageGalleryFolderAdapter = new RecyclerViewImageGalleryFolderAdapter(this);
        recyclerView.setAdapter(recyclerViewImageGalleryFolderAdapter);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Do some stuff
            recyclerViewImageGalleryFolderAdapter.setLandscape(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.LANDSCAPE_COLUMN_COUNT));
        }else {
            recyclerViewImageGalleryFolderAdapter.setLandscape(false);
            recyclerView.setLayoutManager(new GridLayoutManager(this,Constants.PORTRAIT_COLUMN_COUNT));
        }
        recyclerViewImageGalleryFolderAdapter.addFolder(imageGalleryViewModel.getAllFolders());


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            columnCount = 3;
        }
        else {
            columnCount = 5;
        }
    }

    private void updateAdapter() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getStringExtra(getString(R.string.folder_name)).equals("root123")) {
                header_text.setText(getString(R.string.image_gallery));
                showFolders();
            } else {
                header_text.setText(intent.getStringExtra(getString(R.string.folder_name)));
                showImages(intent.getStringExtra(getString(R.string.folder_name)));

            }
        } else {
            Toast.makeText(this, "Something Wrong", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            updateAdapter();
            registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.image_gallery_broadcast_receiver)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

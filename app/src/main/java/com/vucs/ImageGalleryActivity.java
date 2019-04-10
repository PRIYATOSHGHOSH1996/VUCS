package com.vucs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.vucs.adapters.RecyclerViewImageGalleryAdapter;
import com.vucs.adapters.RecyclerViewImageGalleryFolderAdapter;
import com.vucs.model.ImageGalleryModel;
import com.vucs.recycler_view.MyRecyclerView;
import com.vucs.viewmodel.ImageGalleryViewModel;

import java.util.List;

public class ImageGalleryActivity extends AppCompatActivity {
    MyRecyclerView recyclerView;
    TextView header_text;
    private String TAG ="Image Gallery Activity";
    ImageGalleryViewModel imageGalleryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.explode);

        setContentView(R.layout.activity_image_gallery); transition.excludeTarget(findViewById(R.id.toolbar),true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getWindow().setEnterTransition(transition);

        header_text = findViewById(R.id.header_text);
        recyclerView = findViewById(R.id.recycler_view);
        imageGalleryViewModel = ViewModelProviders.of(this).get(ImageGalleryViewModel.class);
        Intent intent = getIntent();
        if (intent!=null){
            if (intent.getStringExtra(getString(R.string.folder_name)).equals("root123")){
                header_text.setText(getString(R.string.image_gallery));
                showFolders();
            }
            else {
                header_text.setText(intent.getStringExtra(getString(R.string.folder_name)));
               showImages(intent.getStringExtra(getString(R.string.folder_name)));

            }
        }else {
            Toast.makeText(this,"Something Wrong",Toast.LENGTH_LONG);
        }

    }

    private void showImages(String folderName) {
        Log.e(TAG,"image");
        final RecyclerViewImageGalleryAdapter recyclerViewImageGalleryAdapter = new RecyclerViewImageGalleryAdapter(this);
        recyclerView.setAdapter(recyclerViewImageGalleryAdapter);

        imageGalleryViewModel.getAllImagesByFolder(folderName).observe(this, new Observer<List<ImageGalleryModel>>() {
            @Override
            public void onChanged(List<ImageGalleryModel> imageGalleryModels) {
                recyclerViewImageGalleryAdapter.addImage(imageGalleryModels);
            }
        });

    }

    private void showFolders() {
        Log.e(TAG,"folder");
        final RecyclerViewImageGalleryFolderAdapter recyclerViewImageGalleryFolderAdapter = new RecyclerViewImageGalleryFolderAdapter(this);
        recyclerView.setAdapter(recyclerViewImageGalleryFolderAdapter);
        imageGalleryViewModel.getAllFolders().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Log.e(TAG,"folder count = "+strings.size());
                recyclerViewImageGalleryFolderAdapter.addFolder(strings);
            }
        });

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

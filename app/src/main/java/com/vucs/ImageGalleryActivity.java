package com.vucs;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.vucs.adapters.RecyclerViewImageGalleryAdapter;
import com.vucs.adapters.RecyclerViewImageGalleryFolderAdapter;
import com.vucs.dao.ImageGalleryDAO;
import com.vucs.dao.ImageGalleryDAOImplementation;
import com.vucs.recycler_view.MyRecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ImageGalleryActivity extends AppCompatActivity {
    MyRecyclerView recyclerView;
    TextView header_text;
    ImageGalleryDAO imageGalleryDAO;
    private String TAG = "Image Gallery Activity";

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
        imageGalleryDAO = new ImageGalleryDAOImplementation(this);

        header_text = findViewById(R.id.header_text);
        recyclerView = findViewById(R.id.recycler_view);
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
            Toast.makeText(this, "Something Wrong", Toast.LENGTH_LONG);
        }

    }

    private void showImages(String folderName) {
        Log.e(TAG, "image");
        final RecyclerViewImageGalleryAdapter recyclerViewImageGalleryAdapter = new RecyclerViewImageGalleryAdapter(this);
        recyclerViewImageGalleryAdapter.addImage(imageGalleryDAO.getAllImagesByFolder(folderName));
        recyclerView.setAdapter(recyclerViewImageGalleryAdapter);


    }

    private void showFolders() {
        Log.e(TAG, "folder");
        final RecyclerViewImageGalleryFolderAdapter recyclerViewImageGalleryFolderAdapter = new RecyclerViewImageGalleryFolderAdapter(this);
        recyclerViewImageGalleryFolderAdapter.addFolder(imageGalleryDAO.getAllFolders());
        recyclerView.setAdapter(recyclerViewImageGalleryFolderAdapter);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

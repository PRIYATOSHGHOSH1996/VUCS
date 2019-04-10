package com.vucs;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;


public class PreviewFile extends AppCompatActivity {

    String itemImageURL="";
    private static final Integer WRITE_STORAGE_PERMISSION = 121;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.add_claim_explode);
        getWindow().setEnterTransition(transition);
        setContentView(R.layout.activity_preview_file);




        final PhotoView photoView =  findViewById(R.id.show_image);
        Intent intent = getIntent();
        if (intent!=null) {

            itemImageURL = intent.getStringExtra(getString(R.string.item_image_url));

        }

        try {

            if (!itemImageURL.equals("default")&&getApplication()!=null){

                Glide
                        .with(this)
                        .load(itemImageURL)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .fitCenter()
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                photoView.setImageDrawable(resource);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FrameLayout frameLayout = findViewById(R.id.download_frame);
        ImageButton imageButton = findViewById(R.id.download_button);
        frameLayout.setAlpha(0.0f);
        frameLayout.animate().alpha(1.0f).setDuration(2000);
        imageButton.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(ActivityCompat.checkSelfPermission(PreviewFile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    download();
                }else{
                    ActivityCompat.requestPermissions(PreviewFile.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
                }
            }else{
                download();
            }
        });


    }

    private void download(){
        if (!itemImageURL.equals("") && !itemImageURL.equals("default")){
            if(isNetworkAvailable()) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri Download_Uri = Uri.parse(itemImageURL);

                String s = URLUtil.guessFileName(itemImageURL, null, null);

                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(s);
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/vucs_images/" + "/" + s);


                downloadManager.enqueue(request);
            }
            else {
                showSnackBar("Internet connection not available");
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_STORAGE_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                download();
            }
            else {
                showSnackBar("Please give storage permission");
            }
        }

    }
    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.snackbar_background));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextAppearance(this, R.style.mySnackbarStyle);
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}

package com.vucs.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.snackbar.Snackbar;
import com.vucs.R;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;

import java.util.Date;


public class PreviewFile extends AppCompatActivity {

    private static final Integer WRITE_STORAGE_PERMISSION = 121;
    String itemImageURL = "";
    private String TAG = "previewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);


            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.add_claim_explode);
            getWindow().setEnterTransition(transition);
            setContentView(R.layout.activity_preview_file);


            final PhotoView photoView = findViewById(R.id.show_image);
            Intent intent = getIntent();
            if (intent != null) {

                itemImageURL = intent.getStringExtra(getString(R.string.item_image_url));

            }


            if (!itemImageURL.equals("default") && getApplication() != null) {

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


            FrameLayout frameLayout = findViewById(R.id.download_frame);
            ImageButton imageButton = findViewById(R.id.download_button);
            frameLayout.setAlpha(0.0f);
            frameLayout.animate().alpha(1.0f).setDuration(2000);
            imageButton.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(PreviewFile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        download();
                    } else {
                        ActivityCompat.requestPermissions(PreviewFile.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
                    }
                } else {
                    download();
                }
            });
        } catch (Exception e) {
            Utils.appendLog(TAG + ":oncreate: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }


    }

    private void download() {
        try {
            if (!itemImageURL.equals("") && !itemImageURL.equals("default")) {
                if (isNetworkAvailable()) {
                    Toast.makeText(this, "Downloading ...");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri Download_Uri = Uri.parse(itemImageURL);

                    String s = URLUtil.guessFileName(itemImageURL, null, null);

                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle(s);
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/VUCS Images/" + s);


                    downloadManager.enqueue(request);
                } else {
                    showSnackBarWithNetworkAction("Internet connection not available");
                }
            }
        } catch (Exception e) {
            Utils.appendLog(TAG + ":ondownload: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                showSnackBarWithRetryStoragePermission("Please give storage permission.");
            } else {
                showSnackBar("Please give storage permission.");
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

    private void showSnackBarWithNetworkAction(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG)
                .setAction("Open Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                });
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.snackbar_background));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        TextView textView1 = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTextAppearance(this, R.style.mySnackbarStyle);
        textView1.setTextAppearance(this, R.style.mySnackbarStyle);
        textView1.setTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    private void showSnackBarWithRetryStoragePermission(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG)
                .setAction("Open Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PreviewFile.this, "Please allow storage permission");
                        Intent i = new Intent();
                        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        i.setData(Uri.parse("package:" + getPackageName()));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // User selected the Never Ask Again Option
                        startActivity(i);
                    }
                });
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.snackbar_background));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        TextView textView1 = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTextAppearance(this, R.style.mySnackbarStyle);
        textView1.setTextAppearance(this, R.style.mySnackbarStyle);
        textView1.setTextColor(getResources().getColor(R.color.colorAccent));
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

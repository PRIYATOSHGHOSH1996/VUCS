package com.vucs.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;
import com.vucs.R;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;
import com.vucs.model.JobFileModel;
import com.vucs.viewmodel.JobViewModel;

import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class JobDetailsActivity extends AppCompatActivity {
    TextView header_text;
    String itemTitle = "", itemBy = "", itemDate = "", itemContent = "",itemFileURL = "";
    Integer itemId = -1;
    private String TAG="itemDetailsActivity";
    private static final Integer WRITE_STORAGE_PERMISSION = 121;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("");
        header_text = findViewById(R.id.header_text);
        Intent intent = getIntent();
        itemTitle = intent.getStringExtra(getString(R.string.item_title));
        itemId = intent.getIntExtra(getString(R.string.item_id),-1);
        itemBy = intent.getStringExtra(getString(R.string.item_by));
        itemDate = intent.getStringExtra(getString(R.string.item_date));
        itemContent = intent.getStringExtra(getString(R.string.item_content));
        header_text.setText("Job Details");
        TextView item_title = findViewById(R.id.item_title);
        TextView item_by = findViewById(R.id.item_by);
        TextView item_date = findViewById(R.id.item_date);
        TextView item_content = findViewById(R.id.item_content);
        LinearLayout file_layout = findViewById(R.id.file_layout);

        item_title.setText(itemTitle);
        item_date.setText(itemDate);
        item_content.setText(itemContent);
        item_by.setText("By " + itemBy);
        JobViewModel jobViewModel = ViewModelProviders.of(this).get(JobViewModel.class);

        if (itemId!=-1){
            List<JobFileModel> jobFileModelList = jobViewModel.getAllJobFileById(itemId);
            for (JobFileModel jobFileModel:jobFileModelList){
                if (!jobFileModel.getJobFileURL().equals("default") && getApplication() != null) {
                    View view = getLayoutInflater().inflate(R.layout.item_file_layout,null);
                    final GifImageView item_image = view.findViewById(R.id.item_image);
                    ImageButton download = view.findViewById(R.id.download);
                    itemFileURL = jobFileModel.getJobFileURL();
                    Glide
                            .with(this)
                            .load(jobFileModel.getJobFileURL())
                            .fitCenter()
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    item_image.setImageDrawable(resource);
                                }
                            });

                    download.setOnClickListener(v->{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(JobDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                download();
                            } else {
                                ActivityCompat.requestPermissions(JobDetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
                            }
                        } else {
                            download();
                        }
                    });
                    file_layout.addView(view);
                }
            }


        }


    }
    private void download() {
        try {
            if (!itemFileURL.equals("") && !itemFileURL.equals("default")) {
                if (Utils.isNetworkAvailable()) {
                    Toast.makeText(this, "Downloading ...");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri Download_Uri = Uri.parse(itemFileURL);

                    String s = URLUtil.guessFileName(itemFileURL, null, null);

                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle(s);
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/vucs_job/" + "/" + s);


                    downloadManager.enqueue(request);
                } else {
                    showSnackBarWithNetworkAction("Internet connection not available");
                }
            }
        } catch (Exception e) {
            Utils.appendLog(TAG+":ondownload: "+e.getMessage()+"Date :"+new Date());
            e.printStackTrace();
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
                        Toast.makeText(JobDetailsActivity.this, "Please allow storage permission");
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            }
            else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                showSnackBarWithRetryStoragePermission("Please give storage permission.");
            }else {
                showSnackBar("Please give storage permission.");
            }
        }

    }
}

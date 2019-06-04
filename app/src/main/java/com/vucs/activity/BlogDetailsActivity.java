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
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;
import com.vucs.R;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;

import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

public class BlogDetailsActivity extends AppCompatActivity {

    private static final Integer WRITE_STORAGE_PERMISSION = 121;
    TextView header_text;
    String itemTitle = "", itemBy = "", itemDate = "", itemImageURL = "", itemContent = "";
    private String TAG = "itemDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_blog_details);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            android.transition.Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.activity_transation);
            transition.excludeTarget( findViewById(R.id.toolbar),true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(new Explode());
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
            if (intent != null) {
                itemTitle = intent.getStringExtra(getString(R.string.item_title));
                itemBy = intent.getStringExtra(getString(R.string.item_by));
                itemDate = intent.getStringExtra(getString(R.string.item_date));
                itemContent = intent.getStringExtra(getString(R.string.item_content));
                itemImageURL = intent.getStringExtra(getString(R.string.item_image_url));
                header_text.setText(intent.getStringExtra(getString(R.string.head_title)));
            }
            TextView item_title = findViewById(R.id.item_title);
            TextView item_by = findViewById(R.id.item_by);
            TextView item_date = findViewById(R.id.item_date);
            TextView item_content = findViewById(R.id.item_content);
            LinearLayout linearLayout = findViewById(R.id.linear_layout);
            View view = getLayoutInflater().inflate(R.layout.item_file_layout, null);
            final GifImageView item_image = view.findViewById(R.id.item_image);
            item_title.setText(itemTitle);
            item_date.setText(itemDate);
            item_content.setText(itemContent);

            item_by.setText("By " + itemBy);


            if (!itemImageURL.equals("default") && getApplication() != null) {
                ImageButton download = view.findViewById(R.id.download);
                FrameLayout frameLayout = view.findViewById(R.id.download_button_layout);


                item_image.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;


                Glide
                        .with(this)
                        .load(itemImageURL)
                        .fitCenter()
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                item_image.setImageDrawable(resource);
                            }
                        });
                download.setOnClickListener(v -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(BlogDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            download();
                        } else {
                            ActivityCompat.requestPermissions(BlogDetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
                        }
                    } else {
                        download();
                    }
                });
                linearLayout.addView(view);
                frameLayout.setAlpha(0.0f);
                frameLayout.animate().alpha(1.0f).setDuration(2000);

            }

        } catch (Exception e) {
            Utils.appendLog(TAG + ":oncreate: " + e.getMessage() + "Date :" + new Date());

            e.printStackTrace();
        }
    }

    private void download() {
        try {
            if (!itemImageURL.equals("") && !itemImageURL.equals("default")) {
                if (Utils.isNetworkAvailable()) {
                    Toast.makeText(this, "Downloading ...");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri Download_Uri = Uri.parse(itemImageURL);

                    String s = URLUtil.guessFileName(itemImageURL, null, null);
                    String s1[] = s.split("\\.");
                    s = s1[s1.length - 1];
                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle(itemTitle);
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/VUCS Blog/" + itemTitle + "." + s);


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
                        Toast.makeText(BlogDetailsActivity.this, "Please allow storage permission");
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
            } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                showSnackBarWithRetryStoragePermission("Please give storage permission.");
            } else {
                showSnackBar("Please give storage permission.");
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);

    }
}

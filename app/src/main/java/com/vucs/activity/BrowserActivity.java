package com.vucs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.filelibrary.exception.ActivityOrFragmentNullException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vucs.R;
import com.vucs.helper.Snackbar;
import com.vucs.helper.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BrowserActivity extends AppCompatActivity  {
    TextView header_text;
    private WebView webView;
    ProgressBar progressBar;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private static final int REQUEST_WRITE_PERMISSIONS = 103;
    LinearLayout noInternet;
    private final static int FCR = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    Intent chooserIntent;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noInternet=findViewById(R.id.no_internet);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("");
        header_text = findViewById(R.id.header_text);
        webView = (WebView) findViewById(R.id.webView1);
        progressBar = findViewById(R.id.progress_bar);
        header_text.setText(getIntent().getStringExtra(getString(R.string.title))+"");
        swipeRefreshLayout=findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noInternet.setVisibility(View.GONE);
                startWebView(getIntent().getStringExtra(getString(R.string.url))+"");
            }
        });
        startWebView(getIntent().getStringExtra(getString(R.string.url))+"");
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {


            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (!Utils.isNetworkAvailable()) {
                    noInternet.setVisibility(View.VISIBLE);
                }
            }

            public void onPageFinished(WebView view, String url) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        });
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setVerticalScrollBarEnabled(true);
        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowContentAccess(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }
                mUMA = filePathCallback;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,}, REQUEST_WRITE_PERMISSIONS);
                    }
                    else {
                        showGetFileDialog();
                    }
                }else {
                   showGetFileDialog();
                }

                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                DownloadManager.Request dr=new DownloadManager.Request(Uri.parse(s));
                dr.allowScanningByMediaScanner();
                dr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(dr);
                com.vucs.helper.Toast.makeText(BrowserActivity.this,"file is downloading");
            }
        });
        webView.loadUrl(url);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);

    }

    private void showGetFileDialog() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.item_get_file_layout, null);
        ImageView camera = sheetView.findViewById(R.id.camera);
        ImageView file_manager = sheetView.findViewById(R.id.file_manager);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                try {
                    com.filelibrary.Utils.with(BrowserActivity.this)
                            .getImageFromCamera()
                            .compressEnable(true)
                            .cropEnable(true)
                            .setAspectRatio(1,1)
                            .getResult(new com.filelibrary.Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath) {
                                    Uri[] results=new Uri[]{uri};
                                    mUMA.onReceiveValue(results);
                                    mUMA = null;

                                }

                                @Override
                                public void onFailure(String error) {

                                }
                            })
                            .start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        file_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                try {
                    com.filelibrary.Utils.with(BrowserActivity.this)
                            .getImageFile()
                            .cropEnable(true)
                            .compressEnable(true)
                            .setAspectRatio(1,1)
                            .getResult(new com.filelibrary.Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath)  {
                                    try {
                                        Uri[] results=new Uri[]{uri};
                                        mUMA.onReceiveValue(results);
                                        mUMA = null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(String error) {

                                }
                            }).start();
                } catch (ActivityOrFragmentNullException e) {
                    e.printStackTrace();
                }

            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setCancelable(true);
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        com.filelibrary.Utils.Builder.notifyPermissionsChange(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    showGetFileDialog();

            } else if (Build.VERSION.SDK_INT >= 23 && (!shouldShowRequestPermissionRationale(permissions[0])||!shouldShowRequestPermissionRationale(permissions[1]))) {
                Snackbar.withRetryStoragePermission(this,findViewById(R.id.parent),"Please give storage and camera permission.");
            } else {
                Snackbar.show(this,findViewById(R.id.parent),"Please give storage and camera permission");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        com.filelibrary.Utils.Builder.notifyActivityChange(requestCode, resultCode, intent);

    }


    @Override
    protected void onStop() {
        super.onStop();
        swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (webView.getScrollY() == 0)
                            swipeRefreshLayout.setEnabled(true);
                        else
                            swipeRefreshLayout.setEnabled(false);

                    }
                });
    }
}

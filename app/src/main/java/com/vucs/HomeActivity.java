package com.vucs;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.vucs.adapters.RecyclerViewNoticeAdapter;
import com.vucs.fragment.BlogFragment;
import com.vucs.fragment.JobPostFragment;
import com.vucs.fragment.NoticeFragment;
import com.vucs.fragment.PhirePawaFragment;
import com.vucs.fragment.TeachersFragment;
import com.vucs.helper.Toast;
import com.vucs.model.NoticeModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewNoticeAdapter.CallbackInterface {
    ViewPager viewPager;
    NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce = false;
    private static final Integer WRITE_STORAGE_PERMISSION = 121;
    private NoticeModel noticeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //NestedScrollView nestedScrollView = findViewById(R.id.nested_scrollView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(6);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.setCheckedItem(R.id.blog);
                        break;
                    case 1:
                        navigationView.setCheckedItem(R.id.phire_pawa);
                        break;
                    case 2:
                        navigationView.setCheckedItem(R.id.notice);
                        break;
                    case 3:
                        navigationView.setCheckedItem(R.id.job_post);
                        break;
                    case 4:
                        navigationView.setCheckedItem(R.id.image_gallery);
                        break;
                    case 5:
                        navigationView.setCheckedItem(R.id.teachers);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            showSnackBar("Press back again to exit VUCS");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }

            }, 2000);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.blog) {
            viewPager.setCurrentItem(0, true);
            // Handle the camera action
        } else if (id == R.id.phire_pawa) {
            viewPager.setCurrentItem(1, true);

        } else if (id == R.id.notice) {
            viewPager.setCurrentItem(2, true);

        } else if (id == R.id.job_post) {
            viewPager.setCurrentItem(3, true);

        } else if (id == R.id.teachers) {
            viewPager.setCurrentItem(4, true);

        } else if (id == R.id.chat_room) {
            startActivity(new Intent(HomeActivity.this, ChatRoomActivity.class));

        } else if (id == R.id.events) {
            startActivity(new Intent(HomeActivity.this, EventsActivity.class));

        } else if (id == R.id.image_gallery) {
            Intent intent = new Intent(HomeActivity.this, ImageGalleryActivity.class);
            intent.putExtra(getString(R.string.folder_name), "root123");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            startActivity(intent);

        } else if (id == R.id.about) {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.snackbar_background));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextAppearance(this, R.style.mySnackbarStyle);
        snackbar.show();
    }

    @Override
    public void downloadFile(NoticeModel noticeModel) {
        this.noticeModel = noticeModel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
               download();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
            }
        }else{
            download();
        }

    }

    private void download(){
        if (noticeModel != null && !noticeModel.getDownloadURL().equals("default")){
            if(isNetworkAvailable()) {
                Toast.makeText(this,"Downloading ...");
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri Download_Uri = Uri.parse(noticeModel.getDownloadURL());

                String s = URLUtil.guessFileName(noticeModel.getDownloadURL(), null, null);
                String s1[] = s.split("//.");
                s = s1[s1.length - 1];
                Log.e("fie name with ex = ", s);
                Log.e("fie name = ", s1.length + "");
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(noticeModel.getNoticeTitle());
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/vucs_notice/" + "/" + noticeModel.getNoticeTitle() + "." + s);


                downloadManager.enqueue(request);
            }
            else {
                showSnackBar("Internet connection not available");
            }
        }

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BlogFragment();
                case 1:
                    return new PhirePawaFragment();
                case 2:
                    return new NoticeFragment();
                case 3:
                    return new JobPostFragment();
                case 4:
                    return new TeachersFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.blog);
                case 1:
                    return getString(R.string.phire_pawa);
                case 2:
                    return getString(R.string.notice);
                case 3:
                    return getString(R.string.job_post);
                case 4:
                    return getString(R.string.teachers);
            }
            return super.getPageTitle(position);
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

package com.vucs.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.vucs.R;
import com.vucs.adapters.RecyclerViewNoticeAdapter;
import com.vucs.fragment.BlogFragment;
import com.vucs.fragment.JobPostFragment;
import com.vucs.fragment.NoticeFragment;
import com.vucs.fragment.PhirePawaFragment;
import com.vucs.fragment.TeachersFragment;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;
import com.vucs.model.NoticeModel;
import com.vucs.service.FirebaseMessaging;

import java.util.Date;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewNoticeAdapter.CallbackInterface {
    private static final Integer WRITE_STORAGE_PERMISSION = 121;
    ViewPager viewPager;
    NavigationView navigationView;
    View content_background;
    AppPreference appPreference;
    PagerTabStrip pagerTabStrip;
    private boolean doubleBackToExitPressedOnce = false;
    private NoticeModel noticeModel;
    private String TAG = "HomeActivity";


    private int revealX;
    private int revealY;
    DrawerLayout drawer;
    TextSwitcher textSwitcher;
    boolean textChangeFlag=true;
    Animation makeInAnimation;
    FloatingActionButton floatingActionButton;
    Animation makeOutAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            /*Fade fade = new Fade(Fade.MODE_IN);
            getWindow().setReenterTransition(fade);*/
            setContentView(R.layout.activity_home);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            content_background = findViewById(R.id.default_background);
            content_background.setAlpha(0);
            appPreference = new AppPreference(this);

            FrameLayout navBack = findViewById(R.id.nav_back);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.setScrimColor(Color.TRANSPARENT);
            drawer.setBackgroundColor(Color.TRANSPARENT);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            viewPager = findViewById(R.id.view_pager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
            pagerTabStrip = findViewById(R.id.tab_title);

            pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.colorPrimary1));
          /*  viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    page.setCameraDistance(20000);

                    if (position < -1) {
                        page.setAlpha(0);
                    } else if (position <= 0) {
                        page.setAlpha(1);
                        page.setPivotX(page.getWidth());
                        page.setRotationY(90 * Math.abs(position));
                    } else if (position <= 1) {
                        page.setAlpha(1);
                        page.setPivotX(0);
                        page.setRotationY(-90 * Math.abs(position));
                    } else {
                        page.setAlpha(0);
                    }

                    if (Math.abs(position) <= 0.5) {
                        page.setScaleY(Math.max(.4f, 1 - Math.abs(position)));
                    } else if (Math.abs(position) <= 1) {
                        page.setScaleY(Math.max(.4f, 1 - Math.abs(position)));

                    }
                }
            });*/
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            navigationView.setCheckedItem(R.id.blog);
                            if (!floatingActionButton.isShown()) {
                                floatingActionButton.startAnimation(makeInAnimation);
                            }
                            break;
                        case 1:
                            navigationView.setCheckedItem(R.id.phire_pawa);
                            if (floatingActionButton.isShown()) {
                                floatingActionButton.startAnimation(makeOutAnimation);
                            }
                            break;
                        case 2:
                            navigationView.setCheckedItem(R.id.notice);
                            if (floatingActionButton.isShown()) {
                                floatingActionButton.startAnimation(makeOutAnimation);
                            }
                            break;
                        case 3:
                            navigationView.setCheckedItem(R.id.job_post);
                            if (!floatingActionButton.isShown()) {
                                floatingActionButton.startAnimation(makeInAnimation);
                            }
                            break;
                        case 4:
                            navigationView.setCheckedItem(R.id.teachers);
                            if (floatingActionButton.isShown()) {
                                floatingActionButton.startAnimation(makeOutAnimation);
                            }
                            break;
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                    //drawerView.setBackgroundColor(getResources().getColor(R.color.white));
                    drawerView.setBackground(getDrawable(R.drawable.nav_item_background));
                    drawerView.setElevation(0);
                    // drawerView.setScaleY(slideOffset);
                    // linearLayout.setTranslationX(slideOffset * linearLayout.getWidth() / 4);
                    //drawerView.setTranslationZ(-100);
                    // navigationView.setPadding((int) (1 - slideOffset) * drawerView.getWidth(), 0, 0, 0);
                    drawerView.setTranslationX((1 - slideOffset) * drawerView.getWidth());
                    drawerView.setRotationY((float) (90 * (1 - slideOffset)));
                    drawerView.setPivotX(0.2f);
                    navBack.setAlpha(1 - slideOffset);
                    content_background.setAlpha(slideOffset);

                    //  drawerView.setPadding((int)(1-slideOffset)*drawerView.getWidth(),0,0,0);

                }

                @Override
                public void onDrawerOpened(@NonNull View drawerView) {

                }

                @Override
                public void onDrawerClosed(@NonNull View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });

            if (!appPreference.isTokenGenerated()) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.e("token==", instanceIdResult.getToken());
                        FirebaseMessaging.upLoadToken(instanceIdResult.getToken());
                    }
                });
            }
            textSwitcher=findViewById(R.id.text_switcher);
            textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

                public View makeView() {
                    TextView t = new TextView(HomeActivity.this);
                    t.setTextAppearance(HomeActivity.this,R.style.TextAppearance_MaterialComponents_Body1);
                    t.setGravity(Gravity.CENTER);
                    t.setSingleLine();
                    t.setTypeface(null, Typeface.ITALIC);
                    t.setTextColor(getResources().getColor(R.color.colorAccent));
                    return t;
                }
            });

            // Declare in and out animations and load them using AnimationUtils class
            Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

            // set the animation type to TextSwitcher
            textSwitcher.setInAnimation(in);
            textSwitcher.setOutAnimation(out);
            final Handler tipsHanlder = new Handler();
            Runnable tipsRunnable = new Runnable() {
                @Override
                public void run()
                {
                  if (textChangeFlag){
                      textSwitcher.setText("Vidyasagar University");
                      textChangeFlag =false;
                  }
                  else {
                      textSwitcher.setText("Dept. of Computer Science");
                      textChangeFlag =true;
                  }
                    tipsHanlder.postDelayed(this, 5000);
                }
            };
            tipsHanlder.post(tipsRunnable);
            floatingActionButton = findViewById(R.id.add);
            makeInAnimation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.scale_up);
            makeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) { }

                @Override
                public void onAnimationRepeat(Animation animation) { }

                @Override
                public void onAnimationStart(Animation animation) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            });

            makeOutAnimation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.scale_down);
            makeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }

                @Override
                public void onAnimationStart(Animation animation) { }
            });
        } catch (Exception e) {
            Utils.appendLog(TAG + ":oncreate: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }


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
            startActivity(intent, options.toBundle());

        } else if (id == R.id.about) {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));

        } else if (id == R.id.logout) {
            startActivity(new Intent(this,LoginActivity.class));

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.snackbar_background));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextAppearance(this, R.style.mySnackbarStyle);
        snackbar.show();
    }

    private void showSnackBarWithNetworkAction(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator), message, Snackbar.LENGTH_LONG)
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
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator), message, Snackbar.LENGTH_LONG)
                .setAction("Open Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Please allow storage permission");
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
    public void downloadFile(NoticeModel noticeModel) {
        this.noticeModel = noticeModel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                download();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
            }
        } else {
            download();
        }

    }

    private void download() {
        try {
            if (noticeModel != null && !noticeModel.getDownloadURL().equals("default")) {
                if (Utils.isNetworkAvailable()) {
                    Toast.makeText(this, "Downloading ...");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri Download_Uri = Uri.parse(noticeModel.getDownloadURL());

                    String s = URLUtil.guessFileName(noticeModel.getDownloadURL(), null, null);
                    String s1[] = s.split("\\.");
                    s = s1[s1.length - 1];
                    Log.e("fie name with ex = ", s);
                    Log.e("fie name = ", s1.length + "");
                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle(noticeModel.getNoticeTitle());
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/VUCS Notice/" + "/" + noticeModel.getNoticeTitle() + "." + s);


                    downloadManager.enqueue(request);
                } else {
                    showSnackBarWithNetworkAction("Internet connection not available");
                }
            }
        } catch (Exception e) {
            Utils.appendLog(TAG + ":download: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            }
            else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                showSnackBarWithRetryStoragePermission("Please give storage permission.");


            } else {
                showSnackBar("Please give storage permission");
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        viewPager.setOffscreenPageLimit(5);

    }

    public void onClassNoticeClick(View view) {
        ActivityOptionsCompat activityOptionsCompat  = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, ClassNoticeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent,activityOptionsCompat.toBundle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.class_notice: ActivityOptionsCompat activityOptionsCompat  = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent intent = new Intent(this, ClassNoticeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent,activityOptionsCompat.toBundle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.class_notice);
        item.setActionView(R.layout.icon_notification);
        View view = item.getActionView();
        FrameLayout frameLayout =view.findViewById(R.id.notification);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat activityOptionsCompat  = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, ClassNoticeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent,activityOptionsCompat.toBundle());
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    public void onAddClick(View view) {
        startActivity(new Intent(HomeActivity.this,AddBlogJobActivity.class));

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            try {
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
            } catch (Exception e) {
                Utils.appendLog(TAG + ":viewpageradapter: " + e.getMessage() + "Date :" + new Date());
                e.printStackTrace();
                return null;
            }
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
}

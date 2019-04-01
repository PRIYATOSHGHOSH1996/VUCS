package com.vucs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.vucs.fragment.BlogFragment;
import com.vucs.fragment.JobPostFragment;
import com.vucs.fragment.NoticeFragment;
import com.vucs.fragment.PhirePawaFragment;
import com.vucs.fragment.TeachersFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}

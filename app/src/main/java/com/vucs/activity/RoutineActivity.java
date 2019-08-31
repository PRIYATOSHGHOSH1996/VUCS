package com.vucs.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vucs.R;
import com.vucs.dao.RoutineDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.RoutineDisplayModel;
import com.vucs.model.RoutineModel;
import com.vucs.viewmodel.RoutineViewModel;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
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
        header_text.setText(getString(R.string.routine));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        RoutinePagerAdapter adapter=new RoutinePagerAdapter(this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager,true);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);
    }


}
 class RoutinePagerAdapter extends PagerAdapter{

    RoutineActivity context;
    RoutineViewModel routineViewModel;

     RoutinePagerAdapter(RoutineActivity context) {
        this.context = context;
        routineViewModel = ViewModelProviders.of(context).get(RoutineViewModel.class);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Monday";
            case 1:return "Tuesday";
            case 2:return "Wednesday";
            case 3:return "Thursday";
            case 4:return "Friday";

        }
        return super.getPageTitle(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        List<RoutineDisplayModel> routineModels = routineViewModel.getAllRoutineByDayNo(position);
        for (RoutineDisplayModel routineModel:routineModels){
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_routine, container, false);
            TextView time = layout.findViewById(R.id.time);
            TextView className= layout.findViewById(R.id.class_name);
            TextView teacherName = layout.findViewById(R.id.teacher_name);
            TextView status = layout.findViewById(R.id.status);
            time.setText(getTime(routineModel.getStartTime())+"");
            className.setText(routineModel.getSubject()+"");
            teacherName.setText(routineModel.getTeacherName()+"");

            linearLayout.addView(layout);
        }

        container.addView(linearLayout);
        return linearLayout;
    }

    private String getTime(long time){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,(int)time);
         return new  SimpleDateFormat("hh:mm aaa").format(new Date(calendar.getTimeInMillis()));
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}

package com.vucs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.vucs.R;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Constants;
import com.vucs.helper.Utils;
import com.vucs.model.ChattingModel;
import com.vucs.model.TeacherModel;
import com.vucs.viewmodel.ChattingViewModel;
import com.vucs.viewmodel.TeacherViewModel;

import java.util.Date;
import java.util.List;

public class ChattingListingActivity extends AppCompatActivity {
    AppPreference appPreference;
    private String TAG = "classnoticeActivity";
    private BroadcastReceiver broadcastReceiver;
    LinearLayout container;
    TeacherViewModel teacherViewModel;
    ChattingViewModel chattingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_chatting_listing);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.activity_transation);
        getWindow().setEnterTransition(transition);
        getWindow().setReturnTransition(new Explode());


        try {
            appPreference = new AppPreference(this);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            container =  findViewById(R.id.container);
            teacherViewModel = ViewModelProviders.of(this).get(TeacherViewModel.class);
            chattingViewModel = ViewModelProviders.of(this).get(ChattingViewModel.class);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            getSupportActionBar().setTitle("");
            TextView header_text = findViewById(R.id.header_text);
            header_text.setText("Class Notice");
            initView();
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    initView();
                }
            };
        } catch (Exception e) {
            Utils.appendLog(TAG + ":oncreate: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    private void initView() {
        try {

            if (appPreference.getUserType() == Constants.CATEGORY_TEACHER) {
               showTeacherView();
            } else {
                showUserView();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

    }

    private void showUserView() {
        List<TeacherModel> teacherModels =teacherViewModel.getAllTeacher();
        container.removeAllViews();
        for (TeacherModel teacherModel:teacherModels){
            View view = getLayoutInflater().inflate(R.layout.item_class_notice_teacher,null);
            TextView title=view.findViewById(R.id.title);
            ImageView imageView=view.findViewById(R.id.user_image);
            title.setText(teacherModel.getName());
            TextView message=view.findViewById(R.id.message);
            if (teacherModel.getImageURL()!=null&&!teacherModel.getImageURL().equals("")&&!teacherModel.getImageURL().equals("default")){
                Glide
                        .with(this)
                        .load(teacherModel.getImageURL())
                        .fitCenter()
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                imageView.setImageDrawable(resource);
                            }
                        });
            }
            view.findViewById(R.id.parent).setOnClickListener(view1 -> {
                Intent intent = new Intent(ChattingListingActivity.this, ChattingActivity.class);
                intent.putExtra(getString(R.string.teacherId), teacherModel.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.scale_fade_up, R.anim.no_anim);
            });
            container.addView(view);

        }
    }

    private void showTeacherView() {
        container.removeAllViews();
        String[] course=getResources().getStringArray(R.array.category_name);
        String[] sem=getResources().getStringArray(R.array.semesters);
        for (int i=1;i<course.length;i++){
            int size=(i==1?6:4);
            for (int j=1;j<=size;j++){
                View view = getLayoutInflater().inflate(R.layout.item_class_notice_student,null);
                TextView course_text=view.findViewById(R.id.course);
                TextView sem_text=view.findViewById(R.id.sem);
                TextView last_message=view.findViewById(R.id.last_message);
                ChattingModel chattingModel = chattingViewModel.getLastChattingBySemAndCourse(j+1,i+1);
                if (chattingModel!=null){
                    last_message.setText(chattingModel.getSenderName()+":"+chattingModel.getMessage());
                }else {
                    last_message.setText("");
                }

                course_text.setText(course[i]);
                sem_text.setText(sem[j]);
                int finalI = i;
                int finalJ = j;
                view.findViewById(R.id.parent).setOnClickListener(view1 -> {
                    Intent intent = new Intent(ChattingListingActivity.this, ChattingActivity.class);
                    intent.putExtra(getString(R.string.course), finalI );
                    intent.putExtra(getString(R.string.sem), finalJ );
                    startActivity(intent);
                    overridePendingTransition(R.anim.scale_fade_up, R.anim.no_anim);
                });
                container.addView(view);
            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            initView();
            registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.class_notice_broadcast_receiver)));
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onresume: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onpause");
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onpause: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

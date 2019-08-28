package com.vucs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vucs.R;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Constants;

public class ChattingActivity extends AppCompatActivity {
    TextView header_text;
    AppPreference appPreference;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
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
        appPreference = new AppPreference(this);
        recyclerView = findViewById(R.id.recycler_view);
        header_text = findViewById(R.id.header_text);
        initView();
    }

    private void initView() {
        if (appPreference.getUserType()== Constants.CATEGORY_TEACHER){
            String teacherId = getIntent().getStringExtra(getString(R.string.teacherId));
            if (teacherId!=null){
                updateTeacherAdapter(teacherId);
            }

        }else {
            int course = getIntent().getIntExtra(getString(R.string.course),0);
            int sem = getIntent().getIntExtra(getString(R.string.sem),0);
            if (course!=0&&sem!=0){
                updateStudentAdapter(course,sem);
            }
        }
    }

    private void updateStudentAdapter(int course, int sem) {
    }

    private void updateTeacherAdapter(String teacherId) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);
    }
}

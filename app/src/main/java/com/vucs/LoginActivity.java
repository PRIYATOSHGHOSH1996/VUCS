package com.vucs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.parent);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher_foreground));
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.logo_transition);
        Scene aScene = Scene.getSceneForLayout(viewGroup, R.layout.activity_login, this);
        TransitionManager.go(aScene, transition);
        //viewGroup.addView(imageView);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);//Right to Left
                finish();
            }
        }, 4000);
    }
}

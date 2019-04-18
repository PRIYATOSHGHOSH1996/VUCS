package com.vucs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.vucs.helper.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class LoginActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    ViewGroup viewGroup;
    CircularProgressButton circularProgressButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewGroup = (ViewGroup) findViewById(R.id.parent);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.logo_transition);
        Scene aScene = Scene.getSceneForLayout(viewGroup, R.layout.activity_login, this);
        TransitionManager.go(aScene, transition);
        linearLayout = findViewById(R.id.login_layout);
        imageView = findViewById(R.id.logo);
            @SuppressLint("WrongThread") String s = FirebaseInstanceId.getInstance().getId();
        Log.e("is=",s);

        circularProgressButton = (CircularProgressButton) findViewById(R.id.login_button);


        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> check = new AsyncTask<String, String, String>() {
                    @SuppressLint("WrongThread")
                    @Override
                    protected String doInBackground(String... strings) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "success";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if(s.equals("success")){
                            circularProgressButton.doneLoadingAnimation(R.color.colorPrimary1, BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));
                           new Handler().postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                   finish();
                               }
                           },200);



                        }
                        else {
                            circularProgressButton.revertAnimation();
                            Toast.makeText(LoginActivity.this,"Invalid User Name and Password");
                        }
                    }
                };
                circularProgressButton.startAnimation();
                check.execute();
            }
        });


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                initLoginview();
            }
        }, 4500);
    }

    private void initLoginview() {
        linearLayout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.login);

        linearLayout.setAnimation(animation);
        animation.start();


    }

    @Override
    protected void onDestroy() {

        circularProgressButton.dispose();
        super.onDestroy();


    }
}

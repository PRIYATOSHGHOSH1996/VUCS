package com.vucs.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.vucs.R;



public class LoginActivity extends AppCompatActivity {
    private final int DURATION = 300;
    LinearLayout linearLayout;
    ViewGroup viewGroup;
    ImageView imageView;
    Button login;
    ProgressBar progressBar;
    FrameLayout frameLayout;

    private int progressBarWidth, buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
frameLayout=findViewById(R.id.transitions_container);
        viewGroup = (ViewGroup) findViewById(R.id.parent);
     /*   Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.logo_transition);
        Scene aScene = Scene.getSceneForLayout(viewGroup, R.layout.activity_login, this);
        TransitionManager.go(aScene, transition);*/
        linearLayout = findViewById(R.id.login_layout);
        imageView = findViewById(R.id.logo);
        Animation connectingAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        imageView.startAnimation(connectingAnimation);
        login = findViewById(R.id.login_button1);
        progressBar = findViewById(R.id.progress_bar);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonToProgressBar();

                openHomeActivity(v);



            }
        });

        @SuppressLint("WrongThread") String s = FirebaseInstanceId.getInstance().getId();
        Log.e("is=", s);







        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                initLoginview();
            }
        }, 4500);
    }

    private void openHomeActivity(View v) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, progressBar, "transition");
               Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.scale_up, R.anim.no_anim);
        finish();

    }

    private void initLoginview() {
        linearLayout.setVisibility(View.VISIBLE);
       TextView textView= (TextView)findViewById(R.id.register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml("<u>Register</u>",Html.FROM_HTML_MODE_LEGACY));
        }else {
            textView.setText(Html.fromHtml("<u>Register</u>"));
        }
        frameLayout.post(new Runnable() {
            @Override
            public void run() {
                progressBarWidth = progressBar.getWidth();
                // `mItemInputEditText` should be left visible from XML in order to get measured
                // setting to GONE after we have got actual width
                progressBar.setVisibility(View.GONE);
                buttonWidth = login.getWidth();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.login);

        linearLayout.setAnimation(animation);
        animation.start();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }



    private void buttonToProgressBar() {
        final int from = login.getWidth();
        final LinearInterpolator interpolator = new LinearInterpolator();

        login.setAlpha(1.0f);
        login.setVisibility(View.GONE);


        ValueAnimator secondAnimator = ValueAnimator.ofInt(from, progressBarWidth);
        secondAnimator.setTarget(progressBar);
        secondAnimator.setInterpolator(interpolator);
        secondAnimator.setDuration(DURATION);

        final ViewGroup.LayoutParams params = progressBar.getLayoutParams();
        secondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (Integer) animation.getAnimatedValue();
                progressBar.requestLayout();
            }
        });

        secondAnimator.start();
    }

    private void progressBarToButton() {
        final int from = progressBar.getWidth();
        final LinearInterpolator interpolator = new LinearInterpolator();

        progressBar.setAlpha(1.0f);


        login.setVisibility(View.VISIBLE);

        ValueAnimator secondAnimator = ValueAnimator.ofInt(from, buttonWidth);
        secondAnimator.setTarget(login);
        secondAnimator.setInterpolator(interpolator);
        secondAnimator.setDuration(DURATION);

        final ViewGroup.LayoutParams params = login.getLayoutParams();
        secondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (Integer) animation.getAnimatedValue();
                login.requestLayout();
            }
        });

        secondAnimator.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("x=",findViewById(R.id.transitions_container).getX()+"");
        Log.e("y=",findViewById(R.id.transitions_container).getY()+"");
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this,RegistrationActivity.class));
        finish();
    }
}


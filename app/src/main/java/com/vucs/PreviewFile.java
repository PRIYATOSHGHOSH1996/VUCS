package com.vucs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class PreviewFile extends AppCompatActivity {

    String itemImageURL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.add_claim_explode);
        getWindow().setEnterTransition(transition);
        setContentView(R.layout.activity_preview_file);
        final PhotoView photoView =  findViewById(R.id.show_image);
        Intent intent = getIntent();
        if (intent!=null) {

            itemImageURL = intent.getStringExtra(getString(R.string.item_image_url));

        }

        try {

            if (!itemImageURL.equals("default")&&getApplication()!=null){

                Glide
                        .with(this)
                        .load(itemImageURL)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .fitCenter()
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                photoView.setImageDrawable(resource);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}

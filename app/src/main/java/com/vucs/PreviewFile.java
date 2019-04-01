package com.vucs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

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
        PhotoView photoView =  findViewById(R.id.show_image);
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
                        .placeholder(R.drawable.double_ring)
                        .into(photoView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}

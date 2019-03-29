package com.vucs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.droidsonroids.gif.GifImageView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView header_text;
    String itemTitle = "",itemBy = "",itemDate = "",itemImageURL = "",itemContent = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Explode explode = new Explode();
        explode.excludeTarget(android.R.id.navigationBarBackground, true);
        explode.excludeTarget(android.R.id.statusBarBackground, true);
        explode.excludeTarget(R.id.toolbar, true);
        getWindow().setEnterTransition(explode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("");
        header_text = findViewById(R.id.header_text);
        Intent intent = getIntent();
        if (intent!=null) {
            itemTitle = intent.getStringExtra(getString(R.string.item_title));
            itemBy = intent.getStringExtra(getString(R.string.item_by));
            itemDate = intent.getStringExtra(getString(R.string.item_date));
            itemContent = intent.getStringExtra(getString(R.string.item_content));
            itemImageURL = intent.getStringExtra(getString(R.string.item_image_url));
            header_text.setText(intent.getStringExtra(getString(R.string.head_title)));
        }
        TextView item_title = findViewById(R.id.item_title);
        TextView item_by = findViewById(R.id.item_by);
        TextView item_date = findViewById(R.id.item_date);
        TextView item_content = findViewById(R.id.item_content);
        final GifImageView item_image = findViewById(R.id.item_image);
        item_title.setText(itemTitle);
        item_date.setText(itemDate);
        item_content.setText(itemContent);

        item_by.setText("By "+ itemBy);
        try {

            if (!itemImageURL.equals("default")&&getApplication()!=null){
                item_image.setVisibility(View.VISIBLE);
                Glide
                        .with(this)
                        .load(itemImageURL)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .fitCenter()
                        .placeholder(R.drawable.double_ring)
                        .into(item_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
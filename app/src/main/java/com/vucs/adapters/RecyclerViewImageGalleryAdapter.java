package com.vucs.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vucs.R;
import com.vucs.activity.PreviewFile;
import com.vucs.helper.Constants;
import com.vucs.helper.Utils;
import com.vucs.model.ImageGalleryModel;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewImageGalleryAdapter extends RecyclerView.Adapter<RecyclerViewImageGalleryAdapter.MyViewHolder> {

    private List<ImageGalleryModel> imageGalleryModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private String TAG = "ImageGalleryAdapter";
    private boolean landscape = false;

    public RecyclerViewImageGalleryAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addImage(List<ImageGalleryModel> imageGalleryModels) {
        imageGalleryModelList = imageGalleryModels;
        notifyDataSetChanged();
    }

    public void setLandscape(boolean count){
        this.landscape = count;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.iten_image_gallery, parent, false);

        /*androidx.transition.Transition transition = TransitionInflater.from(weakReference.get()).inflateTransition(R.transition.explode);
        TransitionManager.beginDelayedTransition(parent,transition);*/
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            final ImageGalleryModel imageGalleryModel = imageGalleryModelList.get(position);


            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) weakReference.get()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int devicewidth;
          if (!landscape) {
               devicewidth = displaymetrics.widthPixels / Constants.PORTRAIT_COLUMN_COUNT;
          }
          else {
              devicewidth = displaymetrics.widthPixels / Constants.LANDSCAPE_COLUMN_COUNT;
          }
            holder.imageView.getLayoutParams().width = devicewidth-18;
            holder.imageView.getLayoutParams().height = devicewidth-18;
            if (!imageGalleryModel.getThumbURL().equals("default") && weakReference.get() != null) {


                Glide
                        .with(weakReference.get())
                        .load(imageGalleryModel.getThumbURL())
                        .fitCenter()
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                holder.imageView.setImageDrawable(resource);
                            }
                        });
                // notifyItemChanged(position);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(weakReference.get(), PreviewFile.class);
                    intent. putExtra("list", (Serializable) imageGalleryModelList);
                    intent. putExtra("position", position);
                    Pair<View, String> p = Pair.create((View) holder.imageView, "transition");
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(v,(int)v.getX(),(int)v.getY(),v.getWidth(),v.getHeight());
                    weakReference.get().startActivity(intent, options.toBundle());
                }
            });
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onBind: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return imageGalleryModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}

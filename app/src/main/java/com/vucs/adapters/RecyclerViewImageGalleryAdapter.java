package com.vucs.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.vucs.helper.Utils;
import com.vucs.model.ImageGalleryModel;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewImageGalleryAdapter extends RecyclerView.Adapter<RecyclerViewImageGalleryAdapter.MyViewHolder> {

    private List<ImageGalleryModel> imageGalleryModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private String TAG = "ImageGalleryAdapter";

    public RecyclerViewImageGalleryAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addImage(List<ImageGalleryModel> imageGalleryModels) {
        imageGalleryModelList = imageGalleryModels;
        notifyDataSetChanged();
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


            if (!imageGalleryModel.getImageURL().equals("default") && weakReference.get() != null) {


                Glide
                        .with(weakReference.get())
                        .load(imageGalleryModel.getImageURL())
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
                    intent.putExtra(weakReference.get().getString(R.string.item_image_url), imageGalleryModel.getImageURL());
                    Pair<View, String> p = Pair.create((View) holder.imageView, weakReference.get().getString(R.string.item_image_url));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) weakReference.get(), p);
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

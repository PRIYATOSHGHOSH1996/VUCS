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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vucs.R;
import com.vucs.activity.ImageGalleryActivity;
import com.vucs.helper.Constants;
import com.vucs.helper.Utils;
import com.vucs.model.ImageGalleryModel;
import com.vucs.viewmodel.ImageGalleryViewModel;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewImageGalleryFolderAdapter extends RecyclerView.Adapter<RecyclerViewImageGalleryFolderAdapter.MyViewHolder> {

    private List<String> folderList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private String TAG = "ImageGalleryfolderAdapter";
    private boolean landscape = false;
    ImageGalleryViewModel imageGalleryViewModel;

    public RecyclerViewImageGalleryFolderAdapter(Context context) {
        weakReference = new WeakReference<>(context);
        ImageGalleryActivity activity = (ImageGalleryActivity) context;
        imageGalleryViewModel= ViewModelProviders.of(activity).get(ImageGalleryViewModel.class);
    }
    public void setLandscape(boolean count){
        this.landscape = count;
    }

    public void addFolder(List<String> folders) {
        folderList = folders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.iten_image_gallery_folder, parent, false);
        /*androidx.transition.Transition transition = TransitionInflater.from(weakReference.get()).inflateTransition(R.transition.explode);
        TransitionManager.beginDelayedTransition(parent,transition);
*/
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            final String folderName = folderList.get(position);
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
            ImageGalleryModel imageGalleryModel =imageGalleryViewModel.getFirstImagesByFolder(folderName);

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
            holder.folderName.setText(folderName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(weakReference.get(), ImageGalleryActivity.class);
                    intent.putExtra(weakReference.get().getString(R.string.folder_name), folderName);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) weakReference.get());
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
        return folderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView folderName;
        ImageView imageView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.folderName);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}

package com.vucs.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.activity.ImageGalleryActivity;
import com.vucs.helper.Utils;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewImageGalleryFolderAdapter extends RecyclerView.Adapter<RecyclerViewImageGalleryFolderAdapter.MyViewHolder> {

    private List<String> folderList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private String TAG = "ImageGalleryfolderAdapter";

    public RecyclerViewImageGalleryFolderAdapter(Context context) {
        weakReference = new WeakReference<>(context);
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

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.folderName);

        }
    }
}

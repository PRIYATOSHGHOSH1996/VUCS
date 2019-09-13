package com.vucs.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vucs.R;
import com.vucs.activity.BrowserActivity;
import com.vucs.helper.Utils;
import com.vucs.model.TeacherModel;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewTeacherAdapter extends RecyclerView.Adapter<RecyclerViewTeacherAdapter.MyViewHolder> {
    private String TAG = "BlogAdapter";

    private List<TeacherModel> teacherModels = Collections.emptyList();
    private WeakReference<Context> weakReference;

    public RecyclerViewTeacherAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addTeacher(List<TeacherModel> teacherModels) {
        this.teacherModels = teacherModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==1) {
            view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_user, parent, false);
        }else {
            view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_blank, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            if (position==getItemCount() -1){
                return;
            }
            final TeacherModel teacherModel = teacherModels.get(position);
            holder.user_name.setText(teacherModel.getName());
            holder.company.setText(teacherModel.getDescription());

            holder.batch.setVisibility(View.GONE);
            if (!teacherModel.getImageURL().equals("default") && weakReference.get() != null) {

                Glide
                        .with(weakReference.get())
                        .load(teacherModel.getImageURL())
                        .fitCenter()
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                holder.image.setImageDrawable(resource);
                            }
                        });
                // notifyItemChanged(position);
            }
holder.itemView.setOnClickListener(view -> {
    if (teacherModel.getPageURL()!=null&&(!teacherModel.getPageURL().equals(""))) {
        Intent intent  =new Intent(weakReference.get(), BrowserActivity.class);
        intent.putExtra(weakReference.get().getString(R.string.title),teacherModel.getName());
        intent.putExtra(weakReference.get().getString(R.string.url),teacherModel.getPageURL());
        Activity activity=(Activity)weakReference.get();
        weakReference.get().startActivity(intent);
        activity.overridePendingTransition(R.anim.scale_fade_up, R.anim.no_anim);
    }
});
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onBind: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }
    @Override
    public int getItemViewType(int position) {
        if (position<getItemCount()-1){
            return 1;
        }
        else
            return 2;
    }
    @Override
    public int getItemCount() {
        return teacherModels.size()+1;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, batch, company;
        CircleImageView image;
        RelativeLayout parentLayout, view;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            company = itemView.findViewById(R.id.company);
            batch = itemView.findViewById(R.id.batch);
            image = itemView.findViewById(R.id.user_image);
            parentLayout = itemView.findViewById(R.id.parent);
            view = itemView.findViewById(R.id.view);

        }
    }
}

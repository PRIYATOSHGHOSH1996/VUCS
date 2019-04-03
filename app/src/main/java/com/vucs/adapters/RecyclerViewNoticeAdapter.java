package com.vucs.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.vucs.ItemDetailsActivity;
import com.vucs.R;
import com.vucs.model.BlogModel;
import com.vucs.model.NoticeModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import pl.droidsonroids.gif.GifImageView;

public class RecyclerViewNoticeAdapter extends RecyclerView.Adapter<RecyclerViewNoticeAdapter.MyViewHolder>  {

    public interface CallbackInterface{

        void downloadFile(NoticeModel noticeModel);
    }

    private List<NoticeModel> noticeModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private CallbackInterface mCallback;

    public RecyclerViewNoticeAdapter(Context context) {
        weakReference = new WeakReference<>(context);
        try{
            mCallback = (CallbackInterface) weakReference.get();
        }catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }
    }

    public void addNotice(List<NoticeModel> noticeModels){
        noticeModelList = noticeModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_notice,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final NoticeModel noticeModel = noticeModelList.get(position);
        holder.notice_title.setText(noticeModel.getNoticeTitle());
        String date = "";

        try {
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  ");
            date = format.format(noticeModel.getDate());
            holder.notice_date.setText(date);
            holder.notice_by.setText(noticeModel.getNoticeBy()+" | ");
            holder.notice_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mCallback.downloadFile(noticeModel);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }



    }



    @Override
    public int getItemCount() {
        return noticeModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView notice_title,notice_date,notice_by;
        ImageView notice_image;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_date = itemView.findViewById(R.id.notice_date);
            notice_image = itemView.findViewById(R.id.notice_image);
            notice_by = itemView.findViewById(R.id.notice_by);

        }
    }
}

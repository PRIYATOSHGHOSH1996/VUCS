package com.vucs.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.filelibrary.Utils;
import com.vucs.R;
import com.vucs.helper.Toast;
import com.vucs.model.NoticeModel;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class RecyclerViewNoticeAdapter extends RecyclerView.Adapter<RecyclerViewNoticeAdapter.MyViewHolder> {

    public List<NoticeModel> noticeModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private CallbackInterface mCallback;
    private String TAG = "noticeAdapter";
    RecyclerView recyclerView;

    public RecyclerViewNoticeAdapter(Context context, RecyclerView recyclerView) {
        weakReference = new WeakReference<>(context);
        this.recyclerView =recyclerView;
        try {
            mCallback = (CallbackInterface) weakReference.get();
        } catch (ClassCastException ex) {
            //.. should log the error or throw and exception
            Log.e("MyAdapter", "Must implement the CallbackInterface in the Activity", ex);
        }
    }

    public void addNotice(List<NoticeModel> noticeModels) {
        noticeModelList = noticeModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==1) {
            view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_notice, parent, false);
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

            final NoticeModel noticeModel = noticeModelList.get(position);
            holder.notice_title.setText(noticeModel.getNoticeTitle());
            String date = "";

            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  ");
            date = format.format(noticeModel.getDate());
            holder.notice_date.setText(date);
            holder.notice_by.setText(noticeModel.getNoticeBy() + " | ");
            holder.notice_image.setOnClickListener(v -> {
                        File file=new File(noticeModel.getFilePath());
                        Log.e("filepath",noticeModel.getFilePath());
                        if (file.exists()){
                            try {
                                Intent notificationIntent = new Intent();
                                Uri uri = Utils.fileToUri(weakReference.get(), new File(noticeModel.getFilePath()));
                                notificationIntent.setAction(Intent.ACTION_VIEW);
                                notificationIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                notificationIntent.setDataAndType(uri, getUriType(uri));
                                weakReference.get().startActivity(notificationIntent);
                            } catch (Exception e) {
                                Toast.makeText(weakReference.get(),"No app found for open file");
                                e.printStackTrace();
                            }
                        }else {
                            mCallback.downloadFile(recyclerView,position,noticeModel);
                        }

            }
            );


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private String getUriType(Uri uri){
        try {

            ContentResolver cR = weakReference.get().getContentResolver();
            return cR.getType(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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
        return noticeModelList.size()+1;
    }


    public interface CallbackInterface {

        void downloadFile(RecyclerView recyclerViewNoticeAdapter, int position, NoticeModel noticeModel);
    }

   public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView notice_title, notice_date, notice_by;
        ImageView notice_image;
        RelativeLayout parentLayout ,view;
        public ProgressBar progressBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_date = itemView.findViewById(R.id.notice_date);
            notice_image = itemView.findViewById(R.id.notice_image);
            notice_by = itemView.findViewById(R.id.notice_by);
            progressBar = itemView.findViewById(R.id.progress_bar);
            parentLayout = itemView.findViewById(R.id.parent);
            view = itemView.findViewById(R.id.view);

        }
    }
}

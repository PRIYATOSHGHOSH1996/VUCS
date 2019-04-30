package com.vucs.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.helper.Utils;
import com.vucs.model.NoticeModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewNoticeAdapter extends RecyclerView.Adapter<RecyclerViewNoticeAdapter.MyViewHolder> {

    private List<NoticeModel> noticeModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private CallbackInterface mCallback;
    private String TAG = "noticeAdapter";

    public RecyclerViewNoticeAdapter(Context context) {
        weakReference = new WeakReference<>(context);
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
            if (position == 0) {
                holder.parentLayout.setBackground(weakReference.get().getDrawable(R.drawable.cut_corner_primary_shape));
            } else {
                holder.parentLayout.setBackground(weakReference.get().getDrawable(R.drawable.cut_corner_white_shape));
            }
            final NoticeModel noticeModel = noticeModelList.get(position);
            holder.notice_title.setText(noticeModel.getNoticeTitle());
            String date = "";

            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  ");
            date = format.format(noticeModel.getDate());
            holder.notice_date.setText(date);
            holder.notice_by.setText(noticeModel.getNoticeBy() + " | ");
            holder.notice_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.downloadFile(noticeModel);

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
        return noticeModelList.size()+1;
    }


    public interface CallbackInterface {

        void downloadFile(NoticeModel noticeModel);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView notice_title, notice_date, notice_by;
        ImageView notice_image;
        RelativeLayout parentLayout ,view;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_date = itemView.findViewById(R.id.notice_date);
            notice_image = itemView.findViewById(R.id.notice_image);
            notice_by = itemView.findViewById(R.id.notice_by);
            parentLayout = itemView.findViewById(R.id.parent);
            view = itemView.findViewById(R.id.view);

        }
    }
}

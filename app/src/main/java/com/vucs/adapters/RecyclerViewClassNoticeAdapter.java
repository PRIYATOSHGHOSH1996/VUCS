package com.vucs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.helper.Utils;
import com.vucs.model.ClassNoticeModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewClassNoticeAdapter extends RecyclerView.Adapter<RecyclerViewClassNoticeAdapter.MyViewHolder> {

    private List<ClassNoticeModel> classNoticeModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;

    private String TAG = "classnoticeAdapter";

    public RecyclerViewClassNoticeAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addNotice(List<ClassNoticeModel> classNoticeModels) {
        classNoticeModelList = classNoticeModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_class_notice, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            final ClassNoticeModel classNoticeModel = classNoticeModelList.get(position);
            holder.notice_title.setText("By " + classNoticeModel.getNoticeTitle());

            String date = "";

            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  ");
            date = format.format(classNoticeModel.getDate());
            holder.notice_date.setText(date);
            holder.notice_by.setText(classNoticeModel.getNoticeBy());


        } catch (Exception e) {
            Utils.appendLog(TAG + ":onBind: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return classNoticeModelList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView notice_title, notice_date, notice_by;
        LinearLayout linearLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_date = itemView.findViewById(R.id.notice_date);
            notice_by = itemView.findViewById(R.id.notice_by);
            linearLayout = itemView.findViewById(R.id.bottom_layout);

        }
    }
}

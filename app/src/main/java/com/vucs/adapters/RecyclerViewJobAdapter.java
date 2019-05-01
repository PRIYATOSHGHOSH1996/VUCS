package com.vucs.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.activity.JobDetailsActivity;
import com.vucs.helper.Utils;
import com.vucs.model.JobModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewJobAdapter extends RecyclerView.Adapter<RecyclerViewJobAdapter.MyViewHolder> {
    private String TAG = "JobAdapter";

    private List<JobModel> jobModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;

    public RecyclerViewJobAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addJob(List<JobModel> jobModels) {
        jobModelList = jobModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==1) {
            view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_job, parent, false);
        }else {
            view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_blank, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            final JobModel jobModel = jobModelList.get(position);
            holder.job__title.setText(jobModel.getJobTitle());
            holder.job_by.setText("By " + jobModel.getJobBy() + "  ");
            String date = "";

            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  ");
            date = format.format(jobModel.getDate());
            holder.job_date.setText(date);

            final String finalDate = date;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(weakReference.get(), JobDetailsActivity.class);
                    intent.putExtra(weakReference.get().getString(R.string.item_id), jobModel.getJobId());
                    intent.putExtra(weakReference.get().getString(R.string.item_title), jobModel.getJobTitle());
                    intent.putExtra(weakReference.get().getString(R.string.item_by), jobModel.getJobBy() + "  ");
                    intent.putExtra(weakReference.get().getString(R.string.item_date), finalDate);
                    intent.putExtra(weakReference.get().getString(R.string.item_content), jobModel.getContent());

                    Bundle options = ActivityOptionsCompat.makeScaleUpAnimation(
                            holder.itemView, 0, 0, v.getWidth(), v.getHeight()).toBundle();
                    weakReference.get().startActivity(intent, options);
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
        return jobModelList.size()+1;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView job__title, job_by, job_date;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            job__title = itemView.findViewById(R.id.job_title);
            job_by = itemView.findViewById(R.id.job_by);
            job_date = itemView.findViewById(R.id.job_date);

        }
    }
}

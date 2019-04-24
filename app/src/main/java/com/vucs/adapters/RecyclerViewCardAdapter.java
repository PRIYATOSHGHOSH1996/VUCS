package com.vucs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vucs.R;
import com.vucs.model.BlogModel;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class RecyclerViewCardAdapter extends RecyclerView.Adapter<RecyclerViewCardAdapter.MyViewHolder> {

    private List<BlogModel> blogModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;

    public RecyclerViewCardAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addBlog(List<BlogModel> blogModels) {
        blogModelList = blogModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_teachers, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        MyViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}

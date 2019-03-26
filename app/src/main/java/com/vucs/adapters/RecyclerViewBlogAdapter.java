package com.vucs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vucs.R;
import com.vucs.model.BlogModel;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBlogAdapter extends RecyclerView.Adapter<RecyclerViewBlogAdapter.MyViewHolder>  {

    private List<BlogModel> blogModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;

    public RecyclerViewBlogAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addBlog(List<BlogModel> blogModels){
        blogModelList = blogModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_blog,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BlogModel blogModel = blogModelList.get(position);
        holder.blog_title.setText(blogModel.getBlogTitle());
        holder.blog_by.setText(blogModel.getBlogBy());
        holder.blog_date.setText(blogModel.getDate()+"");
        holder.blog_content.setText(blogModel.getContent());

    }

    @Override
    public int getItemCount() {
        return blogModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView blog_title,blog_by,blog_date,blog_content;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            blog_title = itemView.findViewById(R.id.blog_title);
            blog_by = itemView.findViewById(R.id.blog_by);
            blog_date = itemView.findViewById(R.id.blog_date);
            blog_content = itemView.findViewById(R.id.blog_content);

        }
    }
}

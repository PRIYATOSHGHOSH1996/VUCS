package com.vucs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vucs.R;
import com.vucs.model.BlogModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        final BlogModel blogModel = blogModelList.get(position);
        holder.blog_title.setText(blogModel.getBlogTitle());
        holder.blog_by.setText("By "+ blogModel.getBlogBy());

        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy, hh:mm:ss");
            holder.blog_date.setText(format.format(blogModel.getDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.blog_content.setText(blogModel.getContent());
        holder.blog_big_content.setText(blogModel.getContent());
        boolean expanded = blogModel.isExpand();
        // Set the visibility based on state
        holder.blog_content.setVisibility(expanded ? View.GONE : View.VISIBLE);
        holder.blog_big_content.setVisibility(expanded ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blogModel.setExpand(!blogModel.isExpand());

                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return blogModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView blog_title,blog_by,blog_date,blog_content,blog_big_content;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            blog_title = itemView.findViewById(R.id.blog_title);
            blog_by = itemView.findViewById(R.id.blog_by);
            blog_date = itemView.findViewById(R.id.blog_date);
            blog_content = itemView.findViewById(R.id.blog_content);
            blog_big_content = itemView.findViewById(R.id.blog_big_content);

        }
    }
}

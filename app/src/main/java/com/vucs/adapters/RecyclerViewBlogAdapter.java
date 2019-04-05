package com.vucs.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vucs.App;
import com.vucs.ItemDetailsActivity;
import com.vucs.R;
import com.vucs.fragment.UserProfile;
import com.vucs.model.BlogModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.droidsonroids.gif.GifImageView;

public class RecyclerViewBlogAdapter extends RecyclerView.Adapter<RecyclerViewBlogAdapter.MyViewHolder> {

    private List<BlogModel> blogModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;

    public RecyclerViewBlogAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addBlog(List<BlogModel> blogModels) {
        blogModelList = blogModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_blog, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final BlogModel blogModel = blogModelList.get(position);
        holder.blog_title.setText(blogModel.getBlogTitle());
        holder.blog_by.setText("By " + blogModel.getBlogBy()+"  ");
        String date = "";

        try {
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  ");
            date = format.format(blogModel.getDate());
            holder.blog_date.setText(date);
            if (!blogModel.getBlogImageURL().equals("default") && weakReference.get() != null) {
                holder.blog_image.setVisibility(View.VISIBLE);

                Glide
                        .with(weakReference.get())
                        .load(blogModel.getBlogImageURL())
                        .fitCenter()
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                holder.blog_image.setImageDrawable(resource);
                            }
                        });
               // notifyItemChanged(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String finalDate = date;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weakReference.get(), ItemDetailsActivity.class);
                intent.putExtra(weakReference.get().getString(R.string.item_title), blogModel.getBlogTitle());
                intent.putExtra(weakReference.get().getString(R.string.item_by), blogModel.getBlogBy()+"  ");
                intent.putExtra(weakReference.get().getString(R.string.item_image_url), blogModel.getBlogImageURL());
                intent.putExtra(weakReference.get().getString(R.string.item_date), finalDate);
                intent.putExtra(weakReference.get().getString(R.string.item_content), blogModel.getContent());
                intent.putExtra(weakReference.get().getString(R.string.head_title), "Blog Details");

                Pair<View, String> p1 = Pair.create((View) holder.blog_title, weakReference.get().getString(R.string.item_title));
                Pair<View, String> p2 = Pair.create((View) holder.blog_by, weakReference.get().getString(R.string.item_by));
                Pair<View, String> p3 = Pair.create((View) holder.blog_date, weakReference.get().getString(R.string.item_date));
                Pair<View, String> p4 = Pair.create((View) holder.blog_image, weakReference.get().getString(R.string.item_image_url));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) weakReference.get(), p1, p2, p3, p4);

                weakReference.get().startActivity(intent, options.toBundle());
                /*BottomSheetDialogFragment bottomSheetDialogFragment = new UserProfile();
                AppCompatActivity activity = (AppCompatActivity) weakReference.get();

                bottomSheetDialogFragment.show(activity.getSupportFragmentManager(),bottomSheetDialogFragment.getTag());*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return blogModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView blog_title, blog_by, blog_date;
        GifImageView blog_image;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            blog_title = itemView.findViewById(R.id.blog_title);
            blog_by = itemView.findViewById(R.id.blog_by);
            blog_date = itemView.findViewById(R.id.blog_date);
            blog_image = itemView.findViewById(R.id.blog_image);

        }
    }
}

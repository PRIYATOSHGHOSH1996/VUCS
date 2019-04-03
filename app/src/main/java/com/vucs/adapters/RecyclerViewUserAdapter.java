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
import com.vucs.ItemDetailsActivity;
import com.vucs.R;
import com.vucs.model.BlogModel;
import com.vucs.model.UserModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class RecyclerViewUserAdapter extends RecyclerView.Adapter<RecyclerViewUserAdapter.MyViewHolder> {

    private List<UserModel> userModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;

    public RecyclerViewUserAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addUser(List<UserModel> userModels) {
        userModelList = userModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final UserModel userModel = userModelList.get(position);
        holder.user_name.setText(userModel.getName());
        holder.company.setText(userModel.getCompany());

        try {
            holder.batch.setText(userModel.getBatch()+"");
            if (!userModel.getUserImageURL().equals("default") && weakReference.get() != null) {

                Glide
                        .with(weakReference.get())
                        .load(userModel.getUserImageURL())
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(weakReference.get(), ItemDetailsActivity.class);
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

                weakReference.get().startActivity(intent, options.toBundle());*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, batch, company;
        CircleImageView image;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            company = itemView.findViewById(R.id.company);
            batch = itemView.findViewById(R.id.batch);
            image = itemView.findViewById(R.id.user_image);

        }
    }
}

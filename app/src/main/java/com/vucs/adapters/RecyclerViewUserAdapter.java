package com.vucs.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vucs.R;
import com.vucs.fragment.PhirePawaProfileFragment;
import com.vucs.helper.Utils;
import com.vucs.model.PhirePawaProfileModel;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewUserAdapter extends RecyclerView.Adapter<RecyclerViewUserAdapter.MyViewHolder> {

    private List<PhirePawaProfileModel> phirePawaProfileModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    private String TAG = "userAdapter";

    public RecyclerViewUserAdapter(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void addUser(List<PhirePawaProfileModel> phirePawaProfileModels) {
        phirePawaProfileModelList = phirePawaProfileModels;
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
        try {
            final PhirePawaProfileModel phirePawaProfileModel = phirePawaProfileModelList.get(position);
            holder.user_name.setText(phirePawaProfileModel.getName());
            holder.company.setText(phirePawaProfileModel.getCompany());


            holder.batch.setText(phirePawaProfileModel.getBatch() + "");
            if (!phirePawaProfileModel.getUserImageURL().equals("default") && weakReference.get() != null) {

                Glide
                        .with(weakReference.get())
                        .load(phirePawaProfileModel.getUserImageURL())
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

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomSheetDialogFragment bottomSheetDialogFragment = new PhirePawaProfileFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(weakReference.get().getString(R.string.object), phirePawaProfileModel);
                    bottomSheetDialogFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) weakReference.get();

                    bottomSheetDialogFragment.show(activity.getSupportFragmentManager(), bottomSheetDialogFragment.getTag());


                }
            });
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onBind: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return phirePawaProfileModelList.size();
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

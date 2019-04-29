package com.vucs.fragment;


import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vucs.R;
import com.vucs.helper.Utils;
import com.vucs.model.PhirePawaProfileModel;

import java.util.Date;

public class PhirePawaProfileFragment extends BottomSheetDialogFragment {

    private PhirePawaProfileModel phirePawaProfileModel;
    private String TAG = "phirepawaProfileFragment";
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_phire_pawa_profile, container, false);
        Log.e("phire pawa profile", "start");
        iniView();
        return view;
    }

    private void iniView() {
        try {
            Log.e("phire pawa profile", "initView");
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(PhirePawaProfileFragment.this).commit());
            CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collaps_tollbar);
            ImageView imageView = view.findViewById(R.id.profile_image);

            Log.e("phire pawa profile", "start");
            phirePawaProfileModel = (PhirePawaProfileModel) getArguments().getSerializable(getContext().getString(R.string.object));
            if (phirePawaProfileModel != null) {
                collapsingToolbarLayout.setTitle(phirePawaProfileModel.getName());
                if (!phirePawaProfileModel.getUserImageURL().equals("default")) {
                    imageView.setVisibility(View.VISIBLE);

                    Glide
                            .with(getContext())
                            .load(phirePawaProfileModel.getUserImageURL())
                            .fitCenter()
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                    imageView.setImageDrawable(resource);
                                    BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                                    Palette p = Palette.from(bitmapDrawable.getBitmap()).generate();
                                    int color = p.getDarkVibrantColor(getContext().getResources().getColor(R.color.colorPrimary1));
                                    collapsingToolbarLayout.setContentScrimColor(color);

                                }
                            });
                }
            } else {
                Log.e("phire pawa profile", "object null");
            }
        } catch (Exception e) {
            Utils.appendLog(TAG + ":iniView: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }
}

package com.vucs.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vucs.R;
import com.vucs.model.PhirePawaProfileModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class PhirePawaProfileFragment extends BottomSheetDialogFragment {

    private PhirePawaProfileModel phirePawaProfileModel;
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
        Log.e("phire pawa profile", "initView");
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(PhirePawaProfileFragment.this).commit());
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collaps_tollbar);
        ImageView imageView = view.findViewById(R.id.profile_image);
        try {

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
                                }
                            });
                }
            } else {
                Log.e("phire pawa profile", "object null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

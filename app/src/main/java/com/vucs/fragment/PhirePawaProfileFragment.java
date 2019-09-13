package com.vucs.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vucs.R;
import com.vucs.helper.Utils;
import com.vucs.model.CareerModel;
import com.vucs.model.UserModel;
import com.vucs.viewmodel.PhirePawaProfileViewModel;

import java.util.Date;
import java.util.List;

public class PhirePawaProfileFragment extends BottomSheetDialogFragment {

    LinearLayout career_layout;
    private String TAG = "phirepawaProfileFragment";
    private View view;
    private TextView batch, course, phone_no, mail, address,career_text;
    ScrollView scrollView;
    String id;
    PhirePawaProfileViewModel phirePawaProfileViewModel;
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_phire_pawa_profile, container, false);

        try {
            iniView();
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    updateCareer();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
//        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//
//                BottomSheetDialog d = (BottomSheetDialog) dialog;
//
//                View bottomSheetInternal = d.findViewById( com.google.android.material.R.id.design_bottom_sheet);
//                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetInternal);
//               stateChange(bottomSheetBehavior);
//
//
//            }
//        });

        return view;
    }

    private void stateChange(BottomSheetBehavior bottomSheetBehavior){
              bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override

                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                    switch (newState) {

                        case BottomSheetBehavior.STATE_HIDDEN:
                            getActivity().getSupportFragmentManager().beginTransaction().remove(PhirePawaProfileFragment.this).commit();
                            break;

                        case BottomSheetBehavior.STATE_EXPANDED:
                            break;

                        case BottomSheetBehavior.STATE_COLLAPSED:
                            break;

                        case BottomSheetBehavior.STATE_DRAGGING:

                            break;

                        case BottomSheetBehavior.STATE_SETTLING:

                            break;

                    }

                }




                @Override

                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    if (slideOffset!=1){

                           // scrollView.setNestedScrollingEnabled(false);

                    }
                    // React to dragging events

                }

            });

    }
    private void iniView() {
        try {
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(PhirePawaProfileFragment.this).commit());
            CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collaps_tollbar);
            ImageView imageView = view.findViewById(R.id.profile_image);
            batch = view.findViewById(R.id.batch);
            course = view.findViewById(R.id.course);
            phone_no = view.findViewById(R.id.phone_no);
            mail = view.findViewById(R.id.mail);
            address = view.findViewById(R.id.address);
            career_layout = view.findViewById(R.id.career_layout);
            scrollView = view.findViewById(R.id.scrollView);
            career_text = view.findViewById(R.id.career_text);



            id = (String) getArguments().getString(getContext().getString(R.string.user_id), "");
            if (!id.equals("")) {
                phirePawaProfileViewModel = ViewModelProviders.of(this).get(PhirePawaProfileViewModel.class);
                UserModel userModel = phirePawaProfileViewModel.getUserDetailsById(id);
                collapsingToolbarLayout.setTitle(userModel.getFirstName() + "  " + userModel.getLastName());

                if (!userModel.getImageUrl().equals("default")) {
                    imageView.setVisibility(View.VISIBLE);

                    Glide
                            .with(getContext())
                            .load(userModel.getImageUrl())
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
                batch.setText(userModel.getBatchStartDate() + " - " + userModel.getBatchEndDate());
                course.setText(userModel.getCourse());
                phone_no.setText(userModel.getPhoneNo());
                mail.setText(userModel.getMail());
                address.setText(userModel.getAddress());
                updateCareer();

            } else {

            }
        } catch (Exception e) {
            Utils.appendLog(TAG + ":iniView: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }

    }

    private void updateCareer() {
        if (!id.equals("")) {
            career_layout.removeAllViews();
            List<CareerModel> careerModels = phirePawaProfileViewModel.getCareerDetailsByUserId(id);
            career_text.setVisibility(View.GONE);
            for (CareerModel careerModel : careerModels) {
                career_text.setVisibility(View.VISIBLE);
                View view = getLayoutInflater().inflate(R.layout.item_career_layout, null);
                TextView company_name = view.findViewById(R.id.company_name);
                TextView duration = view.findViewById(R.id.company_duration);
                TextView occupation = view.findViewById(R.id.occupation);
                company_name.setText(careerModel.getCompany());
                occupation.setText(careerModel.getOccupation());
                if (careerModel.getEndDate() == -1) {
                    duration.setText(careerModel.getStartDate() + "");
                } else {
                    duration.setText(careerModel.getStartDate() + " - " + careerModel.getEndDate());
                }
                career_layout.addView(view);

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            getContext().unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onpause: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            updateCareer();
            getContext().registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.career_broadcast_receiver)));
        } catch (Exception e) {
            Utils.appendLog(TAG + ":onresume: " + e.getMessage() + "Date :" + new Date());
            e.printStackTrace();
        }
    }
}

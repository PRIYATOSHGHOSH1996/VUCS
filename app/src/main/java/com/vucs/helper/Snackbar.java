package com.vucs.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.vucs.R;
import com.vucs.activity.HomeActivity;

public class Snackbar {
    public static void withNetworkAction(Context context,View parent,String message) {
        com.google.android.material.snackbar.Snackbar snackbar = com.google.android.material.snackbar.Snackbar.make(parent, message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                .setAction("Open Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
        View view = snackbar.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary1));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        TextView textView1 = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTextAppearance(context, R.style.mySnackbarStyle);
        textView1.setTextAppearance(context, R.style.mySnackbarStyle);
        textView1.setTextColor(context.getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }
    public static void show(Context context,View parent,String message) {
        com.google.android.material.snackbar.Snackbar snackbar = com.google.android.material.snackbar.Snackbar.make(parent, message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary1));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextAppearance(context, R.style.mySnackbarStyle);
        snackbar.show();
    }


    public static void withRetryStoragePermission(Context context,View parent,String message) {
        com.google.android.material.snackbar.Snackbar snackbar = com.google.android.material.snackbar.Snackbar.make(parent, message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                .setAction("Open Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Please allow storage permission");
                        Intent i = new Intent();
                        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        i.setData(Uri.parse("package:" + context.getPackageName()));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // User selected the Never Ask Again Option
                        context.startActivity(i);
                    }
                });
        View view = snackbar.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary1));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        TextView textView1 = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTextAppearance(context, R.style.mySnackbarStyle);
        textView1.setTextAppearance(context, R.style.mySnackbarStyle);
        textView1.setTextColor(context.getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }
}

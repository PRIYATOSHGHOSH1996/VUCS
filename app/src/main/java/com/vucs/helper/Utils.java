package com.vucs.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vucs.App;
import com.vucs.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static void appendLog(String text) {
        text = text + ",,,";
        File logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static void openDialog(Activity activity, String s){
        Dialog dialog = new Dialog(activity, R.style.Theme_Design_BottomSheetDialog);
        ((ViewGroup)dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                activity,R.anim.dialog_anim));
        View view=activity.getLayoutInflater().inflate(R.layout.dialoge_forgot_password,null);
        TextView textView=view.findViewById(R.id.text);
        textView.setText(s);
        Button ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(v -> {
            dialog.dismiss();
        });
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(view,layoutParams);
        dialog.setCancelable(false);
        dialog.show();
    }
}

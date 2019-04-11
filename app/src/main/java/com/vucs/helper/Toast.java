package com.vucs.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vucs.R;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;

public class Toast {
    public static void makeText(Context context, String msg) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        AppCompatActivity appCompatActivity = (AppCompatActivity) weakReference.get();
        android.widget.Toast toast = new android.widget.Toast(weakReference.get());
        View layout = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_toast, (ViewGroup) appCompatActivity.findViewById(R.id.parenttoast));

        TextView textView = layout.findViewById(R.id.textv);
        textView.setText(msg);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}

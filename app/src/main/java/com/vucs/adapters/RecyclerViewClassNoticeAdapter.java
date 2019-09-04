package com.vucs.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.filelibrary.Utils;
import com.vucs.R;
import com.vucs.dao.NoticeDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Toast;
import com.vucs.model.ClassNoticeFileModel;
import com.vucs.model.ClassNoticeModel;
import com.vucs.model.NoticeFileModel;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecyclerViewClassNoticeAdapter extends RecyclerView.Adapter<RecyclerViewClassNoticeAdapter.MyViewHolder> {

    private List<ClassNoticeModel> classNoticeModelList = Collections.emptyList();
    private WeakReference<Context> weakReference;
    AppPreference appPreference;
    private String TAG = "classnoticeAdapter";
    private CallbackInterface mCallback;
    NoticeDAO noticeDAO;
    public SparseBooleanArray sparseBooleanArray;

    public RecyclerViewClassNoticeAdapter(Context context) {
        sparseBooleanArray = new SparseBooleanArray();
        AppDatabase db = AppDatabase.getDatabase(context);
        noticeDAO = db.noticeDAO();
        weakReference = new WeakReference<>(context);
        appPreference=new AppPreference(context);
        try {
            mCallback = (CallbackInterface) weakReference.get();
        } catch (ClassCastException ex) {
            //.. should log the error or throw and exception
            Log.e("MyAdapter", "Must implement the CallbackInterface in the Activity", ex);
        }
    }

    public void addNotice(List<ClassNoticeModel> classNoticeModels) {
        classNoticeModelList = classNoticeModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_class_notice, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            final ClassNoticeModel classNoticeModel = classNoticeModelList.get(position);
            Log.e("cls nt",classNoticeModel.toString());
            if (classNoticeModel.getFileUrl()==null||classNoticeModel.getFileUrl().equals("")){
                holder.download.setVisibility(View.GONE);
            }else {
                holder.download.setVisibility(View.VISIBLE);
            }
            if (sparseBooleanArray.get(position, false)) {
                holder.progressBar.setVisibility(View.VISIBLE);
            } else {
                holder.progressBar.setVisibility(View.GONE);
            }
            holder.notice_title.setText(classNoticeModel.getNoticeTitle());
            String date = "";

            SimpleDateFormat format = new SimpleDateFormat("MMM dd, hh:mm a  ");
            date = format.format(classNoticeModel.getDate());
            holder.notice_date.setText(date);
            if (appPreference.getUserType()==0){
                holder.notice_by.setText("To "+ classNoticeModel.getSem());
            }
            else {
                holder.notice_by.setText( "From " + classNoticeModel.getNoticeBy());
            }
            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassNoticeFileModel classNoticeFileModel = noticeDAO.getClassNoticeFile(classNoticeModel.getNoticeId());
                    if (classNoticeFileModel != null) {
                        File file = new File(classNoticeFileModel.getNoticeFileURI());
                        if (file.exists()) {
                            try {
                                Intent notificationIntent = new Intent();
                                Uri uri = Utils.fileToUri(weakReference.get(), file);
                                notificationIntent.setAction(Intent.ACTION_VIEW);
                                notificationIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                notificationIntent.setDataAndType(uri, getUriType(uri));
                                weakReference.get().startActivity(notificationIntent);
                            } catch (Exception e) {
                                Toast.makeText(weakReference.get(), "No app found for open file");
                                e.printStackTrace();
                            }
                        } else {
                            if (!sparseBooleanArray.get(position, false)) {
                                mCallback.downloadFile( position, classNoticeModel);
                            }

                        }
                    } else {
                        if (!sparseBooleanArray.get(position, false)) {
                            mCallback.downloadFile( position, classNoticeModel);
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private String getUriType(Uri uri) {
        try {

            ContentResolver cR = weakReference.get().getContentResolver();
            return cR.getType(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    @Override
    public int getItemCount() {
        return classNoticeModelList.size();
    }

    public interface CallbackInterface {

        void downloadFile(int position,ClassNoticeModel classNoticeModel);
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView notice_title, notice_date, notice_by;
        LinearLayout linearLayout;
        ImageButton download;
        public ProgressBar progressBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_date = itemView.findViewById(R.id.notice_date);
            notice_by = itemView.findViewById(R.id.notice_by);
            linearLayout = itemView.findViewById(R.id.bottom_layout);
            download = itemView.findViewById(R.id.download);
            progressBar = itemView.findViewById(R.id.progress_bar);

        }
    }
}

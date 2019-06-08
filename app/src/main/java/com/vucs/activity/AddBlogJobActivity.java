package com.vucs.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.filelibrary.Callback;
import com.filelibrary.Utils;
import com.filelibrary.exception.ActivityOrFragmentNullException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vucs.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddBlogJobActivity extends AppCompatActivity {

    List<Uri> uriList;
    LinearLayout file_layout;
    private String TAG = "add job activity";
    private int CHOOSE_MULTIPLE_FILE_REQUEST_CODE=125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog_job);
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            getSupportActionBar().setTitle("");

            TextView header_text = findViewById(R.id.header_text);
            header_text.setText("Add job");
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        file_layout=findViewById(R.id.file_layout);
        Button add_file=findViewById(R.id.add_file);
        uriList=new ArrayList<>();
        add_file.setOnClickListener(v ->{
                    Log.e(TAG,"uri list = "+uriList.toString());
            showGetFileDialog();
                }
                );


    }
    private boolean isImageUri(Uri uri){

        try {
            String [] s=uri.toString().split("\\.");
            if(s[s.length-1].equals("jpg"))
                return true;
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(uri));

            Log.e(TAG,"uri="+uri);
            Log.e(TAG,"uritype="+cR.getType(uri));
            if (cR.getType(uri).contains("image")){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    private void showGetFileDialog() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.item_get_file_layout, null);


        ImageView camera = sheetView.findViewById(R.id.camera);


        ImageView file_manager = sheetView.findViewById(R.id.file_manager);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                try {
                    Utils.with(AddBlogJobActivity.this)
                            .getImageFromCamera()
                            .getResult(new Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath) {
                                   addImage(uri);

                                }

                                @Override
                                public void onFailure(String error) {

                                }
                            })
                            .start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        file_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                Intent chooseFile;
                Intent intent;
                String [] mimeTypes = {"image/*", "application/pdf"};
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*|application/pdf");
                chooseFile.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                chooseFile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, CHOOSE_MULTIPLE_FILE_REQUEST_CODE);
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setCancelable(true);
    }

    private void addImage(Uri uri){
        try {
            View view=getLayoutInflater().inflate(R.layout.item_image_delete,null);
            uriList.add(uri);
            ImageView imageView=view.findViewById(R.id.image);
            ImageButton imageButton=view.findViewById(R.id.imageButton);
            if (isImageUri(uri)) {
                Glide.with(AddBlogJobActivity.this)
                        .load(uri)
                        .into(imageView);
            }
            else {
                imageView.setImageDrawable(getDrawable(R.drawable.pdf_icon));
            }
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<uriList.size();i++){
                        if (uri==uriList.get(i)){
                            uriList.remove(i);
                            file_layout.removeView(view);
                            break;

                        }
                    }
                }
            });
            file_layout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Utils.Builder.notifyPermissionsChange(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utils.Builder.notifyActivityChange(requestCode, resultCode, data);
        if(requestCode == CHOOSE_MULTIPLE_FILE_REQUEST_CODE) {
            if(null != data) { // checking empty selection
                if(null != data.getClipData()) { // checking multiple selection or not
                    for(int i = 0; i < data.getClipData().getItemCount(); i++) {
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        addImage(uri);
                    }
                } else {
                    Uri uri = data.getData();
                    addImage(uri);
                }
            }
        }
    }

}

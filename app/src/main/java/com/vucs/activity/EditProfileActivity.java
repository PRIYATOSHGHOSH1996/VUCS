package com.vucs.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.filelibrary.exception.ActivityOrFragmentNullException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.vucs.R;
import com.vucs.api.ApiClassNoticeResponseModel;
import com.vucs.api.ApiCredential;
import com.vucs.api.Service;
import com.vucs.dao.NoticeDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Constants;
import com.vucs.helper.ProgressRequestBody;
import com.vucs.helper.Snackbar;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;
import com.vucs.model.ClassNoticeModel;
import com.vucs.service.DataServiceGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vucs.helper.Utils.getMimeType;

public class EditProfileActivity extends AppCompatActivity {
    TextInputLayout first_name, last_name, phone_no, address;
    Uri profileImage = null;
    Spinner start_year, end_year;
    int startingyear, endYear;
    ImageView profile_pic_image_view;
    Boolean isSetBatch=false;
    AppPreference appPreference;
    private static final int REQUEST_WRITE_PERMISSIONS = 103;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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
        header_text.setText("Edit Profile");
        appPreference= new AppPreference(this);
        initView();

    }

    private void initView() {
        profile_pic_image_view = findViewById(R.id.profile_pic_image_view);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone_no = findViewById(R.id.phone_no);
        address = findViewById(R.id.address);
        start_year = findViewById(R.id.start_year);
        end_year = findViewById(R.id.end_year);
        profile_pic_image_view=findViewById(R.id.profile_pic);
        if (!appPreference.getUserFirstName().equals("")){
            first_name.getEditText().setText(appPreference.getUserFirstName()+"");
        }
        if (!appPreference.getUserLastName().equals("")){
            last_name.getEditText().setText(appPreference.getUserLastName()+"");
        }

        if (appPreference.getUserPhoneNo()!=null&&(!appPreference.getUserPhoneNo().equals(""))){
            phone_no.getEditText().setText(appPreference.getUserPhoneNo()+"");
        }
        if (appPreference.getUserAddress()!=null&&(!appPreference.getUserAddress().equals(""))){
            address.getEditText().setText(appPreference.getUserAddress()+"");

        }
        Glide
                .with(this)
                .load(appPreference.getUserImageUrl())
                .fitCenter()
                .transition(new DrawableTransitionOptions().crossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        profile_pic_image_view.setImageDrawable(resource);
                    }
                });
        Calendar calendar = Calendar.getInstance();
        List<String> startYearList = new ArrayList<>();
        startYearList.add("Batch Start Year");
        int styrpos=0;
        int count=0;
        for (int i = 2000; i <= calendar.get(Calendar.YEAR); i++) {
            startYearList.add(i + "");
            count++;
            if (i==appPreference.getUserStartBatch()){
                isSetBatch=true;
                styrpos=count;
            }

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.item_simple_text,R.id.text,
                startYearList
        );
        adapter.setDropDownViewResource(R.layout.item_simple_text);
        start_year.setAdapter(adapter);
        start_year.setSelection(styrpos);
        if (appPreference.getUserType()==Constants.CATEGORY_CURRENT_STUDENT){
            start_year.setVisibility(View.GONE);
            end_year.setVisibility(View.GONE);
        }
        start_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (position != 0) {
                        end_year.setVisibility(View.VISIBLE);
                        Integer s = Integer.parseInt((String) parent.getSelectedItem());
                        startingyear = s;
                        s = s + 2;
                        List<String> endYearList = new ArrayList<>();
                        endYearList.add("Batch End Year");
                        int styrpos=0;
                        int count=0;
                        for (int i = s; i <= s + 5; i++) {
                            count++;
                            if (i==appPreference.getUserEndBatch()){
                                styrpos=count;
                            }
                            endYearList.add(i + "");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                EditProfileActivity.this,
                                R.layout.item_simple_text,R.id.text,
                                endYearList
                        );
                        adapter.setDropDownViewResource(R.layout.item_simple_text);
                        end_year.setAdapter(adapter);
                        if (isSetBatch){
                            isSetBatch=false;
                            end_year.setSelection(styrpos);
                        }


                    } else {
                        startingyear = 0;
                        end_year.setVisibility(View.INVISIBLE);
                    }
                    if (appPreference.getUserType()==Constants.CATEGORY_CURRENT_STUDENT){
                        end_year.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        end_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {
                        endYear = Integer.parseInt((String) parent.getSelectedItem());

                    } else {
                        endYear = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);
    }

    public void addImageFile(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,}, REQUEST_WRITE_PERMISSIONS);
            }
            else {
                showGetFileDialog();
            }
        }else {
            showGetFileDialog();
        }
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
                    com.filelibrary.Utils.with(EditProfileActivity.this)
                            .getImageFromCamera()
                            .compressEnable(true)
                            .cropEnable(true)
                            .setAspectRatio(1,1)
                            .getResult(new com.filelibrary.Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath) {
                                    profileImage = uri;
                                    profile_pic_image_view.setImageURI(uri);


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
                try {
                    com.filelibrary.Utils.with(EditProfileActivity.this)
                            .getImageFile()
                            .cropEnable(true)
                            .compressEnable(true)
                            .setAspectRatio(1,1)
                            .getResult(new com.filelibrary.Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath)  {
                                    try {
                                        profileImage = uri;
                                        profile_pic_image_view.setImageURI(uri);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(String error) {

                                }
                            }).start();
                } catch (ActivityOrFragmentNullException e) {
                    e.printStackTrace();
                }

            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setCancelable(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        com.filelibrary.Utils.Builder.notifyPermissionsChange(requestCode,permissions,grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                showGetFileDialog();
            } else if (Build.VERSION.SDK_INT >= 23 && (!shouldShowRequestPermissionRationale(permissions[0])||!shouldShowRequestPermissionRationale(permissions[1]))) {
                Snackbar.withRetryStoragePermission(this,findViewById(R.id.parent),"Please give storage and camera permission.");
            } else {
                Snackbar.show(this,findViewById(R.id.parent),"Please give storage and camera permission");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        com.filelibrary.Utils.Builder.notifyActivityChange(requestCode,resultCode,data);
    }

    public void OnUpdateClick(View view) {
        String firstName=first_name.getEditText().getText().toString()+"";
        String lastName=last_name.getEditText().getText().toString()+"";
        String phoneNumber=phone_no.getEditText().getText().toString()+"";
        String address=this.address.getEditText().getText().toString()+"";


        if (startingyear==0&&endYear!=0&&appPreference.getUserType()!=Constants.CATEGORY_CURRENT_STUDENT){
            Snackbar.show(this,findViewById(R.id.parent),"Please select batch start year.");
            return;
        }
        if (startingyear!=0&&endYear==0&&appPreference.getUserType()!=Constants.CATEGORY_CURRENT_STUDENT){
            Snackbar.show(this,findViewById(R.id.parent),"Please select batch end year.");
            return;
        }
        if (startingyear==0&&endYear==0&&appPreference.getUserType()!=Constants.CATEGORY_CURRENT_STUDENT){
            Snackbar.show(this,findViewById(R.id.parent),"Please select batch.");
            return;
        }
        if (!Utils.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.no_internet_connection));
        } else if (firstName.isEmpty()){
           Snackbar.show(this,findViewById(R.id.parent),"Please enter your first name.");
        }else if (lastName.isEmpty()){
            Snackbar.show(this,findViewById(R.id.parent),"Please enter your last name.");
        }else {
            new UpdateProfile(this,firstName,lastName,phoneNumber,address,startingyear,endYear).execute();
        }

    }

    public static class UpdateProfile extends AsyncTask<Void, Void, Void> implements ProgressRequestBody.UploadCallbacks{
        private static WeakReference<EditProfileActivity> weakReference;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String address;
        private int startYear;
        private int endYear;

        private AppPreference appPreference;
        TextView progressText;
        ProgressBar progressBar;
        Dialog dialog;


        public UpdateProfile(EditProfileActivity context,String firstName, String lastName, String phoneNumber, String address, int startYear, int endYear) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.startYear = startYear;
            this.endYear = endYear;
            weakReference = new WeakReference<>(context);
            appPreference = new AppPreference(context);
            dialog = new Dialog(weakReference.get(), R.style.Theme_Design_BottomSheetDialog);
            ((ViewGroup)dialog.getWindow().getDecorView())
                    .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                    weakReference.get(),R.anim.dialog_anim));
            View view=weakReference.get().getLayoutInflater().inflate(R.layout.dialoge_loading,null);
            progressText=view.findViewById(R.id.text);
            progressBar=view.findViewById(R.id.progress_bar);
            progressText.setText("Sending ...");
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.addContentView(view,layoutParams);
            dialog.setCancelable(false);
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            EditProfileActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /*LoginActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }*/

            // progressDialog.dismiss();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            final EditProfileActivity activity = weakReference.get();
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                ApiCredential apiCredential=new ApiCredential();
                RequestBody apiLogin = RequestBody.create(MultipartBody.FORM, apiCredential.getApiLogin());
                RequestBody apiPass = RequestBody.create(MultipartBody.FORM, apiCredential.getApiPass());
                RequestBody userId = RequestBody.create(MultipartBody.FORM, appPreference.getUserId() + "");
                RequestBody firstNameR = RequestBody.create(MultipartBody.FORM,firstName );
                RequestBody lastNameR = RequestBody.create(MultipartBody.FORM, lastName);
                RequestBody phoneNumberR = RequestBody.create(MultipartBody.FORM, phoneNumber);
                RequestBody addressR = RequestBody.create(MultipartBody.FORM, address);
                RequestBody startYearR = RequestBody.create(MultipartBody.FORM, startYear + "");
                RequestBody endYearR = RequestBody.create(MultipartBody.FORM, endYear + "");

                Call<ApiClassNoticeResponseModel> call;
                if (weakReference.get().profileImage!=null){
                    File directory = weakReference.get().getDir("temp_file", Context.MODE_PRIVATE);
                    directory.mkdir();
                    InputStream in = weakReference.get().getContentResolver().openInputStream(weakReference.get().profileImage);
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);
                    File file;

                    file = new File(directory, "prpfileimage" + "."+getMimeType(weakReference.get(),weakReference.get().profileImage));

                    OutputStream outStream = new FileOutputStream(file);
                    outStream.write(buffer);
                    outStream.close();
                    in.close();
                    ProgressRequestBody fileBody1 = new ProgressRequestBody(file, "*/*", EditProfileActivity.UpdateProfile.this, "Uploading File ..." );
                    MultipartBody.Part jobFile =
                            MultipartBody.Part.createFormData("file", file.getName(), fileBody1);

                    call = service.updateProfile(apiLogin,apiPass,userId,firstNameR,lastNameR,phoneNumberR,addressR,startYearR,endYearR,jobFile);

                }else {
                    call = service.updateProfile(apiLogin,apiPass,userId,firstNameR,lastNameR,phoneNumberR,addressR,startYearR,endYearR);
                }

                call.enqueue(new Callback<ApiClassNoticeResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiClassNoticeResponseModel> call, Response<ApiClassNoticeResponseModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                final ApiClassNoticeResponseModel apiResponseModel = response.body();
                                if (apiResponseModel.getCode() == 1) {
                                    ;
                                    if (activity == null || activity.isFinishing())
                                        return;

                                    try {
                                        appPreference.setUserFirstName(firstName);
                                        appPreference.setUserLastName(lastName);
                                        appPreference.setUserStartBatch(startYear);
                                        appPreference.setUserEndBatch(endYear);
                                        appPreference.setUserPhoneNo(phoneNumber);
                                        appPreference.setUserAddress(address);
                                        if (apiResponseModel.getFileUrl()!=null&&(!apiResponseModel.getFileUrl().equals(""))){
                                            appPreference.setUserImageUrl(apiResponseModel.getFileUrl());
                                        }

                                        activity.onBackPressed();
                                        Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                        dialog.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    if (activity == null || activity.isFinishing()) {
                                        return;
                                    }
                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                    dialog.dismiss();
                                    //Failure
                                }
                            }
                        } else {
                            if (activity == null || activity.isFinishing()) {
                                return;
                            }
                            Toast.makeText(weakReference.get(), weakReference.get().getString(R.string.server_error));
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiClassNoticeResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        Toast.makeText(weakReference.get(), weakReference.get().getString(R.string.server_error));
                        dialog.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(int percentage, String title) {

            if (weakReference.get()!=null){
                progressBar.setProgress(percentage);
                progressText.setText(title);
                progressBar.setIndeterminate(false);
            }

        }

        @Override
        public void onError() {

        }

        @Override
        public void onFinish(String title) {

        }
    }
}

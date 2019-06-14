package com.vucs.activity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.filelibrary.Callback;
import com.filelibrary.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.vucs.R;
import com.vucs.api.ApiAddJobFileResponseModel;
import com.vucs.api.ApiAddJobModel;
import com.vucs.api.ApiAddJobResponseModel;
import com.vucs.api.ApiCredential;
import com.vucs.api.Service;
import com.vucs.dao.JobDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.ProgressRequestBody;
import com.vucs.helper.Toast;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.service.DataServiceGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class AddBlogJobActivity extends AppCompatActivity {

    private static String TAG = "add job activity";
    List<Uri> uriList;
    LinearLayout file_layout;
    TextInputLayout title, description;
    FrameLayout progress_layout;
    Dialog dialog;
    private int CHOOSE_MULTIPLE_FILE_REQUEST_CODE = 125;
    private TextView dialog_text, percentage;
    private ProgressBar progressBar;

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
            dialog = new Dialog(this, R.style.Theme_Design_BottomSheetDialog);
            View view = getLayoutInflater().inflate(R.layout.dialoge_uploading_file, null);
            dialog_text = view.findViewById(R.id.text);
            dialog_text.setText("Please wait");
            percentage = view.findViewById(R.id.percentage);
            percentage.setText("24%");
            progressBar = view.findViewById(R.id.progress_bar);
            progress_layout = view.findViewById(R.id.progress_layout);
            progressBar.setProgress(25);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.addContentView(view, layoutParams);
            dialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        file_layout = findViewById(R.id.file_layout);
        Button add_file = findViewById(R.id.add_file);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        uriList = new ArrayList<>();
        add_file.setOnClickListener(v -> {
                    Log.e(TAG, "uri list = " + uriList.toString());
                    showGetFileDialog();
                }
        );


    }

    private boolean isImageUri(Uri uri) {

        try {
            String[] s = uri.toString().split("\\.");
            if (s[s.length - 1].equals("jpg"))
                return true;
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(uri));

            Log.e(TAG, "uri=" + uri);
            Log.e(TAG, "uritype=" + cR.getType(uri));
            if (cR.getType(uri).contains("image")) {
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
                String[] mimeTypes = {"image/*", "application/pdf"};
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

    private void addImage(Uri uri) {
        try {
            View view = getLayoutInflater().inflate(R.layout.item_image_delete, null);
            uriList.add(uri);
            ImageView imageView = view.findViewById(R.id.image);
            ImageButton imageButton = view.findViewById(R.id.imageButton);
            if (isImageUri(uri)) {
                Glide.with(AddBlogJobActivity.this)
                        .load(uri)
                        .into(imageView);
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.pdf_icon));
            }
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < uriList.size(); i++) {
                        if (uri == uriList.get(i)) {
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
        if (requestCode == CHOOSE_MULTIPLE_FILE_REQUEST_CODE) {
            if (null != data) { // checking empty selection
                if (null != data.getClipData()) { // checking multiple selection or not
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
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

    public void onSubmitClick(View view) {

        if (title.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter title");
        } else if (description.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter description");
        } else if (!com.vucs.helper.Utils.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.no_internet_connection));
        } else {
            new UploadJob(this, title.getEditText().getText().toString(), description.getEditText().getText().toString(), uriList).execute();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);
    }

    private static class UploadJob extends AsyncTask<Void, Void, String> implements ProgressRequestBody.UploadCallbacks {
        int totalFile;
        JobDAO jobDAO;

        private WeakReference<AddBlogJobActivity> homeWeakReference;
        private String jobTitle, jobDescription;
        private List<Uri> uriList;


        UploadJob(AddBlogJobActivity home, String jobTitle, String jobDescription, List<Uri> uriList) {
            homeWeakReference = new WeakReference<>(home);
            this.jobDescription = jobDescription;
            this.jobTitle = jobTitle;
            this.uriList = uriList;
            totalFile = uriList.size();
            AppDatabase db = AppDatabase.getDatabase(homeWeakReference.get());
            jobDAO = db.jobDAO();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (homeWeakReference.get() == null)
                return;

            homeWeakReference.get().dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (homeWeakReference.get() == null)
                    return null;
                final Service service = DataServiceGenerator.createService(Service.class);
                AppPreference appPreference = new AppPreference(homeWeakReference.get());
                ApiCredential apiCredential = new ApiCredential();
                final ApiAddJobModel apiAddJobModel = new ApiAddJobModel(appPreference.getUserId(), jobTitle, jobDescription);
                Call<ApiAddJobResponseModel> call = service.addJob(apiAddJobModel);
                call.enqueue(new retrofit2.Callback<ApiAddJobResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiAddJobResponseModel> call, Response<ApiAddJobResponseModel> response) {
                        try {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    final ApiAddJobResponseModel apiAddJobResponseModel = response.body();
                                    Log.e(TAG, "add job response:\n" + apiAddJobResponseModel.toString());

                                    if (apiAddJobResponseModel.getCode() == 1) {

                                        jobDAO.insertJob(new JobModel(apiAddJobResponseModel.getJobId(), jobTitle, appPreference.getUserId(), appPreference.getUserName(), new Date(),
                                                jobDescription, 1));
                                        RequestBody apiLogin = RequestBody.create(MultipartBody.FORM, apiCredential.getApiLogin());
                                        RequestBody apiPass = RequestBody.create(MultipartBody.FORM, apiCredential.getApiPass());
                                        RequestBody jobId = RequestBody.create(MultipartBody.FORM, apiAddJobResponseModel.getJobId() + "");
                                        RequestBody userId = RequestBody.create(MultipartBody.FORM, appPreference.getUserId() + "");
                                        Log.e("total file", uriList.size() + "");
                                        for (int i = 0; i < uriList.size(); i++) {
                                            if (homeWeakReference.get() == null)
                                                return;
                                            int count = i;
                                            File directory = homeWeakReference.get().getDir("temp_file", Context.MODE_PRIVATE);
                                            directory.mkdir();
                                            InputStream in = homeWeakReference.get().getContentResolver().openInputStream(uriList.get(i));
                                            byte[] buffer = new byte[in.available()];
                                            in.read(buffer);
                                            File file;
                                            if (homeWeakReference.get().isImageUri(uriList.get(i))) {
                                                file = new File(directory, i + ".jpg");
                                            } else {
                                                file = new File(directory, i + ".pdf");
                                            }
                                            OutputStream outStream = new FileOutputStream(file);
                                            outStream.write(buffer);
                                            outStream.close();
                                            in.close();
                                            Log.e("files length", file.length() + "");
                                            ProgressRequestBody fileBody1 = new ProgressRequestBody(file, "*/*", UploadJob.this, "Uploading File " + (i + 1) + " of " + totalFile);
                                            MultipartBody.Part jobFile =
                                                    MultipartBody.Part.createFormData("file", file.getName(), fileBody1);
                                            Call<ApiAddJobFileResponseModel> call1 = service.uploadJobFile(apiLogin, apiPass, jobId, userId, jobFile);
                                            call1.enqueue(new retrofit2.Callback<ApiAddJobFileResponseModel>() {
                                                @Override
                                                public void onResponse(Call<ApiAddJobFileResponseModel> call, Response<ApiAddJobFileResponseModel> response) {
                                                    if (homeWeakReference.get() == null)
                                                        return;
                                                    if (response.body() != null) {
                                                        final ApiAddJobFileResponseModel apiAddJobFileResponseModel = response.body();
                                                        Log.e(TAG, "add job file:\n" + apiAddJobFileResponseModel.toString());

                                                        if (apiAddJobResponseModel.getCode() == 1) {
                                                            jobDAO.insertJobFile(new JobFileModel(apiAddJobResponseModel.getJobId(), apiAddJobFileResponseModel.getFileUrl()));


                                                        }
                                                        if (file.exists()){
                                                            file.delete();
                                                        }
                                                        if (count == uriList.size() - 1) {
                                                            if (homeWeakReference.get() == null)
                                                                return;
                                                            homeWeakReference.get().dialog.dismiss();
                                                            Toast.makeText(homeWeakReference.get(), apiAddJobResponseModel.getMessage());
                                                            homeWeakReference.get().onBackPressed();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ApiAddJobFileResponseModel> call, Throwable t) {
                                                    t.printStackTrace();

                                                }
                                            });

                                        }


                                    } else {
                                        if (homeWeakReference.get() == null)
                                            return;
                                        homeWeakReference.get().dialog.dismiss();
                                        Toast.makeText(homeWeakReference.get(), apiAddJobResponseModel.getMessage());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (homeWeakReference.get() == null)
                                return;
                            homeWeakReference.get().dialog.dismiss();
                            Toast.makeText(homeWeakReference.get(), homeWeakReference.get().getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiAddJobResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        if (homeWeakReference.get() == null)
                            return;
                        homeWeakReference.get().dialog.dismiss();
                        Toast.makeText(homeWeakReference.get(), homeWeakReference.get().getString(R.string.server_error));
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        public void onProgressUpdate(int percentage1, String title) {
            try {
                if (homeWeakReference.get() == null)
                    return;

                Log.e("progrees opdate", percentage1 + "");
                homeWeakReference.get().progress_layout.setVisibility(View.VISIBLE);
                homeWeakReference.get().percentage.setText(percentage1 + "%");
                homeWeakReference.get().dialog_text.setText(title);
                homeWeakReference.get().progressBar.setProgress(percentage1);
            } catch (Exception e) {
                e.printStackTrace();
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

package com.vucs.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.filelibrary.exception.ActivityOrFragmentNullException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vucs.R;
import com.vucs.api.ApiClassNoticeModel;
import com.vucs.api.ApiClassNoticeResponseModel;
import com.vucs.api.ApiCredential;
import com.vucs.api.ApiResponseModel;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vucs.helper.Utils.getMimeType;

public class AddClassNoticeActivity extends AppCompatActivity {

    private static String TAG = "Add Class Notice Activity";
    private int sem = 0;
    private int course = 0;
private Uri fileUri;
private ImageView fileView;
    private static final int REQUEST_WRITE_PERMISSIONS = 103;
    private static final int REQUEST_GET_FILE = 109;
    TextView header_text;
    Spinner spinner;
    Spinner courseSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_notice);

        spinner = findViewById(R.id.sem);
        courseSp = findViewById(R.id.course);
        fileView = findViewById(R.id.show_file);
        EditText text = findViewById(R.id.text);
        Button submit = findViewById(R.id.submit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddClassNoticeActivity.this,
                R.layout.item_simple_text,R.id.text,
                getResources().getStringArray(R.array.category_name)
        );
        courseSp.setAdapter(adapter);

        courseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String[] a;
                    spinner.setVisibility(View.VISIBLE);
                    if (position == 1) {
                        a = getResources().getStringArray(R.array.semesters);
                    } else {
                        a = getResources().getStringArray(R.array.semesters4th);
                    }

                    course = position;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            AddClassNoticeActivity.this,
                            R.layout.item_simple_text,R.id.text,
                            a
                    );

                    spinner.setAdapter(adapter);
                } else {
                    course = 0;
                    sem = 0;
                    spinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sem = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(v -> {
            if (course == 0) {
                Toast.makeText(this, "Please select course");
            } else if (sem == 0) {
                Toast.makeText(this, "Please select semester");
            } else if (text.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter messages");
            } else if (!Utils.isNetworkAvailable()) {
                Toast.makeText(this, getString(R.string.no_internet_connection));
            } else {
                new SendMessage(this, text.getText().toString(), course, sem).execute();
            }
        });
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
        header_text = findViewById(R.id.header_text);
        header_text.setText("Send Class Notice");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);
        finish();

    }

    public void addFile(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,}, REQUEST_WRITE_PERMISSIONS);
            }
            else {
                getFile();
            }
        }else {
            getFile();
        }
    }

    private void getFile() {
        showGetFileDialog();
    }

    public void deleteFile(View view) {
        fileUri=null;
        fileView.setImageResource(R.drawable.box_shadow1);
    }
    private String getUriType(Uri uri){
        try {

            ContentResolver cR = getContentResolver();
            return cR.getType(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private boolean isImageUri(Uri uri) {

        try {
            String[] s = uri.toString().split("\\.");
            if (s[s.length - 1].equals("jpg"))
                return true;
            if (getUriType(uri).contains("image")) {
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
                    com.filelibrary.Utils.with(AddClassNoticeActivity.this)
                            .getImageFromCamera()
                            .compressEnable(true)
                            .cropEnable(true)
                            .getResult(new com.filelibrary.Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath) {
                                    fileUri = uri;
                                    fileView.setImageURI(uri);


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
                    com.filelibrary.Utils.with(AddClassNoticeActivity.this)
                            .getFile()
                            .cropEnable(true)
                            .compressEnable(true)
                            .getResult(new com.filelibrary.Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath)  {
                                    try {
                                        InputStream in = getContentResolver().openInputStream(uri);
                                        if (in.available()< (Constants.MAX_FILE_SIZE_SELECT*1024*1024)) {
                                            fileUri = uri;

                                            if (isImageUri(uri)) {
                                                fileView.setImageURI(uri);
                                            } else {
                                                fileView.setImageResource(R.drawable.ic_insert_drive_file_gray_24dp);
                                            }
                                        }
                                        else {
                                            Utils.openDialog(AddClassNoticeActivity.this,"You can select maximum "+Constants.MAX_FILE_SIZE_SELECT+"MB file.");
                                        }
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

    public void onImageClick(View view) {
        if (fileUri!=null){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri, getUriType(fileUri));
            startActivity(intent);
        }
        else {
            Snackbar.show(this,findViewById(R.id.parent),"File not set");
        }
    }

    public static class SendMessage extends AsyncTask<Void, Void, Void> implements ProgressRequestBody.UploadCallbacks{
        private static WeakReference<AddClassNoticeActivity> weakReference;
        private String message;
        private int sem;
        private int course;
        private AppPreference appPreference;

        private NoticeDAO noticeDAO;
        TextView progressText;
        ProgressBar progressBar;
        Dialog dialog;


        SendMessage(AddClassNoticeActivity context, String message, int course, int sem) {
            weakReference = new WeakReference<>(context);
            this.message = message;
            this.sem = sem;
            this.course = course;
            appPreference = new AppPreference(context);
            AppDatabase db = AppDatabase.getDatabase(context);
            noticeDAO = db.noticeDAO();

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
            AddClassNoticeActivity activity = weakReference.get();
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
            final AddClassNoticeActivity activity = weakReference.get();
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                ApiCredential apiCredential=new ApiCredential();
                RequestBody apiLogin = RequestBody.create(MultipartBody.FORM, apiCredential.getApiLogin());
                RequestBody apiPass = RequestBody.create(MultipartBody.FORM, apiCredential.getApiPass());
                RequestBody userId = RequestBody.create(MultipartBody.FORM, appPreference.getUserId() + "");
                RequestBody userName = RequestBody.create(MultipartBody.FORM, appPreference.getUserFirstName()+" "+appPreference.getUserLastName());
                RequestBody courseR = RequestBody.create(MultipartBody.FORM, course + "");
                RequestBody semR = RequestBody.create(MultipartBody.FORM, sem + "");
                RequestBody messageR = RequestBody.create(MultipartBody.FORM, message + "");
                Call<ApiClassNoticeResponseModel> call;
                final File[] file = new File[1];
                if (weakReference.get().fileUri!=null){
                    File directory = weakReference.get().getDir("temp_file", Context.MODE_PRIVATE);
                    directory.mkdir();
                    InputStream in = weakReference.get().getContentResolver().openInputStream(weakReference.get().fileUri);
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);


                        file[0] = new File(directory, "classnotice" + "."+getMimeType(weakReference.get(),weakReference.get().fileUri));

                    OutputStream outStream = new FileOutputStream(file[0]);
                    outStream.write(buffer);
                    outStream.close();
                    in.close();
                    ProgressRequestBody fileBody1 = new ProgressRequestBody(file[0], "*/*", AddClassNoticeActivity.SendMessage.this, "Uploading File ..." );
                    MultipartBody.Part jobFile =
                            MultipartBody.Part.createFormData("file", file[0].getName(), fileBody1);

                    call = service.sendClassNoticeWithFile(apiLogin,apiPass,userId,userName,courseR,semR,messageR,jobFile);

                }else {
                    call = service.sendClassNotice(apiLogin,apiPass,userId,userName,courseR,semR,messageR);
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
                                        String[] courses = weakReference.get().getResources().getStringArray(R.array.category_name);
                                        String[] semester = weakReference.get().getResources().getStringArray(R.array.semesters);
                                        if (file[0]!=null){
                                            file[0].delete();
                                        }
                                        noticeDAO.insertClassNotice(new ClassNoticeModel(message, new Date(), appPreference.getUserFirstName()+" "+appPreference.getUserLastName(), courses[course] + "(" + semester[sem] + ")", apiResponseModel.getFileUrl()));
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
                progressBar.setIndeterminate(false);
                progressBar.setProgress(percentage);
                progressText.setText(title);
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

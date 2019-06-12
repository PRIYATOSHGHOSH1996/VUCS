package com.vucs.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.vucs.R;
import com.vucs.api.ApiClassNoticeModel;
import com.vucs.api.ApiResponseModel;
import com.vucs.api.Service;
import com.vucs.dao.NoticeDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;
import com.vucs.model.ClassNoticeModel;
import com.vucs.service.DataServiceGenerator;

import java.lang.ref.WeakReference;
import java.util.Date;

import dagger.Binds;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddClassNoticeActivity extends AppCompatActivity {

    private static String TAG = "Add Class Notice Activity";
    private int sem = 0;

    TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_notice);

        Spinner spinner = findViewById(R.id.sem);
        EditText text = findViewById(R.id.text);
        Button submit = findViewById(R.id.submit);

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
            if (sem == 0) {
                Toast.makeText(this, "Please select semester");
            } else if (text.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter messages");
            } else if (!Utils.isNetworkAvailable()) {
                Toast.makeText(this, getString(R.string.no_internet_connection));
            } else {
                new SendMessage(this, text.getText().toString(), sem).execute();
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

    private static class SendMessage extends AsyncTask<Void, Void, Void> {
        private static WeakReference<AddClassNoticeActivity> weakReference;
        private String message;
        private int sem;
        private AppPreference appPreference;
        private ProgressDialog progressDialog;
        private NoticeDAO noticeDAO;


        SendMessage(AddClassNoticeActivity context, String message, int sem) {
            weakReference = new WeakReference<>(context);
            this.message = message;
            this.sem = sem;
            appPreference = new AppPreference(context);
            progressDialog = new ProgressDialog(context);
            AppDatabase db = AppDatabase.getDatabase(context);
            noticeDAO = db.noticeDAO();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AddClassNoticeActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            progressDialog.setMessage("Sending ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
                final ApiClassNoticeModel apiClassNoticeModel = new ApiClassNoticeModel(appPreference.getUserId(),
                        appPreference.getUserName(), sem, message);
                Call<ApiResponseModel> call = service.sendClassNotice(apiClassNoticeModel);
                call.enqueue(new Callback<ApiResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code = " + response.code() + "");
                            if (response.body() != null) {
                                final ApiResponseModel apiResponseModel = response.body();
                                Log.e(TAG, "Response:\n" + apiResponseModel.toString());

                                if (apiResponseModel.getCode() == 1) {
                                    ;
                                    if (activity == null || activity.isFinishing())
                                        return;

                                    try {
                                        noticeDAO.insertClassNotice(new ClassNoticeModel(message, new Date(), appPreference.getUserName(), sem));
                                        activity.onBackPressed();
                                        Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                        progressDialog.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Log.e(TAG, apiResponseModel.getMessage());

                                } else {
                                    if (activity == null || activity.isFinishing()) {
                                        return;
                                    }
                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                    progressDialog.dismiss();
                                    //Failure
                                    Log.e(TAG, apiResponseModel.getMessage());
                                }
                            }
                        } else {
                            Log.e(TAG, "Error Response Code = " + response.code() + "");
                            if (activity == null || activity.isFinishing()) {
                                return;
                            }
                            Toast.makeText(weakReference.get(), weakReference.get().getString(R.string.server_error));
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                        Log.e(TAG, "fail " + t.getMessage());
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        Toast.makeText(weakReference.get(), weakReference.get().getString(R.string.server_error));
                        progressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

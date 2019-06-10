package com.vucs.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.vucs.R;
import com.vucs.api.ApiAddCareerModel;
import com.vucs.api.ApiAddCareerResponseModel;
import com.vucs.api.ApiChangePasswordModel;
import com.vucs.api.ApiDeleteCareerModel;
import com.vucs.api.ApiResponseModel;
import com.vucs.api.Service;
import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Toast;
import com.vucs.helper.Utils;
import com.vucs.model.CareerModel;
import com.vucs.service.DataServiceGenerator;
import com.vucs.viewmodel.PhirePawaProfileViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView name, mail, course, batch, dob;
    CircleImageView profile_pic;
    EditText ph_no, address;
    LinearLayout career_layout;
    AppPreference appPreference;
    PhirePawaProfileViewModel phirePawaProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        course = findViewById(R.id.course);
        batch = findViewById(R.id.batch);
        dob = findViewById(R.id.dob);
        profile_pic = findViewById(R.id.profile_pic);
        ph_no = findViewById(R.id.ph_no);
        address = findViewById(R.id.address);
        career_layout = findViewById(R.id.career_layout);
        appPreference = new AppPreference(this);
        phirePawaProfileViewModel = ViewModelProviders.of(this).get(PhirePawaProfileViewModel.class);
        initView();
    }

    private void initView() {
        try {
            name.setText(appPreference.getUserName());
            mail.setText(appPreference.getUserEmail());
            course.setText(appPreference.getUserCourse());
            batch.setText(appPreference.getUserBatch());
            dob.setText(appPreference.getUserDob());
            ph_no.setText(appPreference.getUserPhoneNo());
            address.setText(appPreference.getUserAddress());
            Glide.with(this)
                    .load(appPreference.getUserImageUrl())
                    .into(profile_pic);
            career_layout.removeAllViews();
            List<CareerModel> careerModels = phirePawaProfileViewModel.getCareerDetailsByUserId(appPreference.getUserId());
            for (CareerModel careerModel : careerModels) {
                View view = getLayoutInflater().inflate(R.layout.item_profile_career_layout, null);
                EditText company_name = view.findViewById(R.id.company_name);
                EditText start_year = view.findViewById(R.id.start_year);
                EditText end_year = view.findViewById(R.id.end_year);
                EditText occupation = view.findViewById(R.id.occupation);
                end_year.setVisibility(View.GONE);
                company_name.setText(careerModel.getCompany());
                if (careerModel.getEndDate() == -1) {
                    start_year.setText(careerModel.getStartDate() + "");
                } else {
                    start_year.setText(careerModel.getStartDate() + " - " + careerModel.getEndDate());
                }
                occupation.setText(careerModel.getOccupation());
                ImageButton delete;
                delete = view.findViewById(R.id.delete);
                delete.setOnClickListener(v -> {
                    if (Utils.isNetworkAvailable()) {
                        new DeleteCareer(ProfileActivity.this, careerModel.getId()).execute();
                    } else {
                        Toast.makeText(ProfileActivity.this, "No internet connection.");
                    }
                });
                career_layout.addView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onAddCareerClick(View view1) {
        View view = getLayoutInflater().inflate(R.layout.item_profile_career_layout, null);
        EditText company_name = view.findViewById(R.id.company_name);
        EditText start_year = view.findViewById(R.id.start_year);
        EditText end_year = view.findViewById(R.id.end_year);
        EditText occupation = view.findViewById(R.id.occupation);
        company_name.setEnabled(true);
        start_year.setEnabled(true);
        end_year.setEnabled(true);
        occupation.setEnabled(true);
        Button submit;
        ImageButton delete;
        submit = view.findViewById(R.id.submit);
        delete = view.findViewById(R.id.delete);
        submit.setVisibility(View.VISIBLE);
        delete.setOnClickListener(v -> {
            career_layout.removeView(view);
        });
        career_layout.addView(view, 0);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (company_name.getText().toString().isEmpty() || start_year.getText().toString().isEmpty() || occupation.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please fill data");
                } else if (!Utils.isNetworkAvailable()) {
                    Toast.makeText(ProfileActivity.this, "No internet connection");
                } else {
                    String companyName = company_name.getText().toString();
                    String occupationString = occupation.getText().toString();
                    int startYear = Integer.parseInt(start_year.getText().toString());
                    int endYear = -1;
                    if (!end_year.getText().toString().isEmpty()) {
                        endYear = Integer.parseInt(end_year.getText().toString());
                    }
                    new AddCareer(ProfileActivity.this, companyName, occupationString, startYear, endYear).execute();
                }
            }
        });
    }

    public void onChangePasswordClick(View view) {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.change_password_layout, null);
        TextInputLayout current_password, enterPassword, reEnterPassword;
        Button cancel, update;
        current_password = sheetView.findViewById(R.id.current_password);
        enterPassword = sheetView.findViewById(R.id.enter_password);
        reEnterPassword = sheetView.findViewById(R.id.reenter_password);
        update = sheetView.findViewById(R.id.update);
        cancel = sheetView.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
        });
        current_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                current_password.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        enterPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enterPassword.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        reEnterPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reEnterPassword.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        update.setOnClickListener(v -> {
            if (current_password.getEditText().getText().toString().isEmpty()) {
                current_password.setError("Please enter current password.");
            } else if (enterPassword.getEditText().getText().toString().isEmpty()) {
                enterPassword.setError("Please enter new password.");
            } else if (reEnterPassword.getEditText().getText().toString().isEmpty()) {
                reEnterPassword.setError("Please re enter new password.");
            } else if (!reEnterPassword.getEditText().getText().toString().equals(enterPassword.getEditText().getText().toString())) {
                reEnterPassword.setError("Passwords does not match.");
            } else if (!appPreference.getPassword().equals(current_password.getEditText().getText().toString())) {
                current_password.setError("enter correct password.");
            } else if (!Utils.isNetworkAvailable()) {
                Toast.makeText(ProfileActivity.this, "Internet connection not available");
            } else {
                mBottomSheetDialog.dismiss();
                new ChangePassword(ProfileActivity.this,enterPassword.getEditText().getText().toString()).execute();
            }
        });

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setCancelable(false);
    }

    private static class ChangePassword extends AsyncTask<Void, Void, Void> {
        private static WeakReference<ProfileActivity> weakReference;
        PhirePawaProfileDAO phirePawaProfileDAO;
        ProgressDialog progressDialog;
        private String password;
        private AppPreference appPreference;


        ChangePassword(ProfileActivity context, String password) {
            weakReference = new WeakReference<>(context);
            this.password = password;
            ProfileActivity activity = weakReference.get();
            AppDatabase db = AppDatabase.getDatabase(activity);
            phirePawaProfileDAO = db.phirePawaProfileDAO();
            progressDialog = new ProgressDialog(weakReference.get());
            appPreference=new AppPreference(weakReference.get());

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
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
            try {

                final Service service = DataServiceGenerator.createService(Service.class);
                final ApiChangePasswordModel apiChangePasswordModel = new ApiChangePasswordModel(appPreference.getUserId(),appPreference.getPassword(), password);
                Log.e("chnge pass",apiChangePasswordModel.toString());
                Call<ApiResponseModel> call = service.changePassword(apiChangePasswordModel);
                call.enqueue(new Callback<ApiResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                        try {
                            if (weakReference.get() == null)
                                return;

                            progressDialog.dismiss();
                            if (response.body() != null) {
                                ApiResponseModel apiResponseModel = response.body();
                                if (apiResponseModel.getCode() == 1) {
                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                    appPreference.setUserPassword(password);
                                } else {

                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(weakReference.get(), "Server Error");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        if (weakReference.get() != null) {
                            Toast.makeText(weakReference.get(), "Server Error");
                            progressDialog.dismiss();
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class DeleteCareer extends AsyncTask<Void, Void, Void> {
        private static WeakReference<ProfileActivity> weakReference;
        private final int id;
        PhirePawaProfileDAO phirePawaProfileDAO;
        ProgressDialog progressDialog;

        DeleteCareer(ProfileActivity context, int id) {
            weakReference = new WeakReference<>(context);
            this.id = id;
            ProfileActivity activity = weakReference.get();
            AppDatabase db = AppDatabase.getDatabase(activity);
            phirePawaProfileDAO = db.phirePawaProfileDAO();
            progressDialog = new ProgressDialog(weakReference.get());

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
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
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                final ApiDeleteCareerModel apiDeleteCareerModel = new ApiDeleteCareerModel(id);
                Call<ApiResponseModel> call = service.deleteCareer(apiDeleteCareerModel);
                call.enqueue(new Callback<ApiResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                        try {
                            if (weakReference.get() == null)
                                return;

                            progressDialog.dismiss();
                            if (response.body() != null) {
                                ApiResponseModel apiResponseModel = response.body();
                                if (apiResponseModel.getCode() == 1) {
                                    phirePawaProfileDAO.deleteCareerById(id);
                                    weakReference.get().initView();
                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                } else {

                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage());
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(weakReference.get(), "Server Error");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        if (weakReference.get() != null) {
                            Toast.makeText(weakReference.get(), "Server Error");
                            progressDialog.dismiss();
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class AddCareer extends AsyncTask<Void, Void, Void> {
        private static WeakReference<ProfileActivity> weakReference;
        PhirePawaProfileDAO phirePawaProfileDAO;
        ProgressDialog progressDialog;
        private String companyName, occupation;
        private int startYear, endYear;

        AddCareer(ProfileActivity context, String companyName, String occupation, int startYear, int endYear) {
            weakReference = new WeakReference<>(context);
            ProfileActivity activity = weakReference.get();
            this.companyName = companyName;
            this.occupation = occupation;
            this.startYear = startYear;
            this.endYear = endYear;
            AppDatabase db = AppDatabase.getDatabase(activity);
            phirePawaProfileDAO = db.phirePawaProfileDAO();
            progressDialog = new ProgressDialog(weakReference.get());

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
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
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                final ApiAddCareerModel apiAddCareerModel = new ApiAddCareerModel(companyName, startYear, endYear, occupation);
                Call<ApiAddCareerResponseModel> call = service.addCareer(apiAddCareerModel);
                call.enqueue(new Callback<ApiAddCareerResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiAddCareerResponseModel> call, Response<ApiAddCareerResponseModel> response) {
                        try {
                            AppPreference appPreference = new AppPreference(weakReference.get());
                            if (weakReference.get() == null)
                                return;

                            progressDialog.dismiss();
                            if (response.body() != null) {
                                ApiAddCareerResponseModel apiAddCareerResponseModel = response.body();
                                if (apiAddCareerResponseModel.getCode() == 1) {
                                    CareerModel careerModel = new CareerModel(apiAddCareerResponseModel.getCareerId(),
                                            appPreference.getUserId(), startYear, endYear, companyName, occupation);
                                    phirePawaProfileDAO.insertCareer(careerModel);
                                    weakReference.get().initView();
                                    Toast.makeText(weakReference.get(), apiAddCareerResponseModel.getMessage());
                                } else {

                                    Toast.makeText(weakReference.get(), apiAddCareerResponseModel.getMessage());
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(weakReference.get(), "Server Error");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (weakReference.get() != null)
                                progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiAddCareerResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        if (weakReference.get() != null) {
                            Toast.makeText(weakReference.get(), "Server Error");
                            progressDialog.dismiss();
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

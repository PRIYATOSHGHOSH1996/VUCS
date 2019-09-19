package com.vucs.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.filelibrary.Callback;
import com.filelibrary.Utils;
import com.filelibrary.exception.ActivityOrFragmentNullException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.vucs.R;
import com.vucs.api.ApiCredential;
import com.vucs.api.ApiResponseModel;
import com.vucs.api.Service;
import com.vucs.helper.ProgressRequestBody;
import com.vucs.helper.Toast;
import com.vucs.service.DataServiceGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    TextView date_field, header_text;
    Spinner start_year, end_year, courseSpinner;
    String TAG = "Registration Activity";

    TextInputLayout first_name, last_name, mail, phone_no, address;
    File profileImage = null;
    File supportFile = null;
    Integer studentTypeCode = 0, semCode = 0, courseCode = 0;
    String dateString = "";
    FrameLayout profile_pic_layout, support_pic_layout;
    ImageView profile_pic_image_view, support_pic_image_view;
    String startingyear, endYear, course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
        header_text.setText(getString(R.string.registration));
        intView();
    }

    private void intView() {
        profile_pic_layout = findViewById(R.id.profile_pic_layout);
        support_pic_layout = findViewById(R.id.support_pic_layout);
        profile_pic_image_view = findViewById(R.id.profile_pic_image_view);
        support_pic_image_view = findViewById(R.id.support_pic_image_view);
        date_field = (TextView) findViewById(R.id.input_date);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        mail = findViewById(R.id.mail);
        phone_no = findViewById(R.id.phone_no);
        address = findViewById(R.id.address);
        start_year = findViewById(R.id.start_year);
        end_year = findViewById(R.id.end_year);
        courseSpinner = findViewById(R.id.course);
        Calendar calendar = Calendar.getInstance();
        List<String> startYearList = new ArrayList<>();
        startYearList.add("Batch Start Year");
        for (int i = 2000; i <= calendar.get(Calendar.YEAR); i++) {
            startYearList.add(i + "");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                startYearList
        );
        start_year.setAdapter(adapter);
        start_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (position != 0) {
                        end_year.setVisibility(View.VISIBLE);
                        Integer s = Integer.parseInt((String) parent.getSelectedItem());
                        startingyear = s + "";
                        s = s + 2;
                        List<String> endYearList = new ArrayList<>();
                        endYearList.add("Batch End Year");
                        for (int i = s; i <= s + 5; i++) {
                            endYearList.add(i + "");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                RegistrationActivity.this,
                                android.R.layout.simple_spinner_item,
                                endYearList
                        );
                        end_year.setAdapter(adapter);

                    } else {
                        startingyear = "";
                        end_year.setVisibility(View.INVISIBLE);
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
                        Integer s = Integer.parseInt((String) parent.getSelectedItem());
                        endYear = s + "";

                    } else {
                        endYear = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {
                        course = (String) parent.getSelectedItem();
                        courseCode=position;


                    } else {
                        course = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        date_field.setFocusable(false); // disable editing of this field
        date_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                chooseDate();
            }
        });

        profile_pic_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMilletDetailsDialog(1,true);
            }
        });
        support_pic_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMilletDetailsDialog(2,false);
            }
        });
        first_name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                first_name.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        last_name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                last_name.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mail.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone_no.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone_no.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        address.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void showMilletDetailsDialog(int option,boolean crop) {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        //Dialog mBottomSheetDialog = new Dialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.item_get_file_layout, null);


        ImageView camera = sheetView.findViewById(R.id.camera);


        ImageView file_manager = sheetView.findViewById(R.id.file_manager);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utils.with(RegistrationActivity.this)
                            .getImageFromCamera()
                            .compressEnable(true)
                            .cropEnable(true)
                            .setSquareAspectRatio(crop)
                            .getResult(new Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath) {
                                    if (option == 1) {
                                        profile_pic_image_view.setImageURI(uri);
                                        setFile(uri, 1);
                                    } else {
                                        support_pic_image_view.setImageURI(uri);
                                        setFile(uri, 2);
                                    }
                                    mBottomSheetDialog.dismiss();
                                }

                                @Override
                                public void onFailure(String error) {

                                }
                            })
                            .start();
                } catch (ActivityOrFragmentNullException e) {
                    e.printStackTrace();
                }
            }
        });
        file_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utils.with(RegistrationActivity.this)
                            .getImageFile()
                            .compressEnable(true)
                            .cropEnable(true)
                            .setSquareAspectRatio(crop)
                            .getResult(new Callback() {
                                @Override
                                public void onSuccess(Uri uri, String filePath) {
                                    if (option == 1) {
                                        profile_pic_image_view.setImageURI(uri);
                                        setFile(uri, 1);
                                    } else {
                                        support_pic_image_view.setImageURI(uri);
                                        setFile(uri, 2);
                                    }
                                    mBottomSheetDialog.dismiss();
                                }

                                @Override
                                public void onFailure(String error) {

                                }
                            })
                            .start();
                } catch (ActivityOrFragmentNullException e) {
                    e.printStackTrace();
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setCancelable(true);
    }

    private void setFile(Uri uri, int option) {
        try {
            File directory = getDir("temp_file", Context.MODE_PRIVATE);
            if (option == 1) {
                InputStream in = getContentResolver().openInputStream(uri);
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                profileImage = new File(directory, "profile_pic.jpg");
                OutputStream outStream = new FileOutputStream(profileImage);
                outStream.write(buffer);
                outStream.close();
                in.close();
            } else {
                InputStream in = getContentResolver().openInputStream(uri);
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                supportFile = new File(directory, "support_pic.jpg");
                OutputStream outStream = new FileOutputStream(supportFile);
                outStream.write(buffer);
                outStream.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseDate() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year, month, dayOfMonth);
                        dateString = sdf.format(calendar.getTime());

                        date_field.setText(dateString); // set the date
                    }
                }, year, month, day); // set date picker to current date

        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private boolean checkValidation() {

        if (first_name.getEditText().getText().toString().isEmpty() || first_name.getEditText().getText().toString().equals("")) {
            first_name.setError("Please enter your first name.");
            return false;
        }
        if (last_name.getEditText().getText().toString().isEmpty() || last_name.getEditText().getText().toString().equals("")) {
            last_name.setError("Please enter your last name.");
            return false;
        }
        if (mail.getEditText().getText().toString().isEmpty() || mail.getEditText().getText().toString().equals("")) {
            mail.setError("Please enter your email address.");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail.getEditText().getText()).matches()) {
            mail.setError("Please enter valid email address.");
            return false;
        }
        if (phone_no.getEditText().getText().toString().isEmpty() || phone_no.getEditText().getText().toString().equals("")) {
            phone_no.setError("Please enter your phone number.");
            return false;
        }
        if (address.getEditText().getText().toString().isEmpty() || address.getEditText().getText().toString().equals("")) {
            address.setError("Please enter your address.");
            return false;
        }
        if (dateString.equals("")) {
            Toast.makeText(this, "Please select your date of birth.");
            return false;
        }
        if (course.equals("")) {
            Toast.makeText(this, "Please select your course.");
            return false;
        }
        if (startingyear.equals("")) {
            Toast.makeText(this, "Please select your batch start year.");
            return false;
        }
        if (endYear.equals("")) {
            Toast.makeText(this, "Please select your batch end year.");
            return false;
        }
        if (profileImage == null) {
            Toast.makeText(this, "Please add your profile picture.");
            return false;
        }
        if (supportFile == null) {
            Toast.makeText(this, "Please add your last marksheet from VU.");
            return false;
        }

        return true;
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
    }

    public void onSubmitClick(View view) {

        if (checkValidation()) {

            if (com.vucs.helper.Utils.isNetworkAvailable()) {
                try {
                    new Submit(this, startingyear, endYear, courseCode, dateString, first_name.getEditText().getText().toString(),
                            last_name.getEditText().getText().toString(), mail.getEditText().getText().toString(), phone_no.getEditText().getText().toString(),
                            address.getEditText().getText().toString(), profileImage, supportFile).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Please check your internet connection");
            }
        }
        else {
        }

    }

    private static class Submit extends AsyncTask<String, String, String> implements ProgressRequestBody.UploadCallbacks {
        String startYear, endYear, dateString, first_name, last_name, mail, phone_no, address;
        File profileImage, supportImage;
        int course;
        ProgressDialog progressDialog;
        private WeakReference<RegistrationActivity> weakReference;

        public Submit(RegistrationActivity registrationActivity, String startYear, String endYear, int course, String dateString, String first_name, String last_name, String mail, String phone_no, String address, File profileImage, File supportImage) {
            this.weakReference = new WeakReference<>(registrationActivity);
            this.startYear = startYear;
            this.endYear = endYear;
            this.course = course;
            this.dateString = dateString;
            this.first_name = first_name;
            this.last_name = last_name;
            this.mail = mail;
            this.phone_no = phone_no;
            this.address = address;
            this.profileImage = profileImage;
            this.supportImage = supportImage;
            progressDialog = new ProgressDialog(weakReference.get());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ApiCredential apiCredential = new ApiCredential();
                RequestBody apiLogin = RequestBody.create(MultipartBody.FORM, apiCredential.getApiLogin());
                RequestBody apiPass = RequestBody.create(MultipartBody.FORM, apiCredential.getApiPass());
                RequestBody firstName = RequestBody.create(MultipartBody.FORM, this.first_name);
                RequestBody lastName = RequestBody.create(MultipartBody.FORM, this.last_name);
                RequestBody mail = RequestBody.create(MultipartBody.FORM, this.mail);
                RequestBody phoneNo = RequestBody.create(MultipartBody.FORM, this.phone_no);
                RequestBody address = RequestBody.create(MultipartBody.FORM, this.address);
                RequestBody dob = RequestBody.create(MultipartBody.FORM, this.dateString);
                RequestBody course = RequestBody.create(MultipartBody.FORM, this.course+"");
                RequestBody startYear = RequestBody.create(MultipartBody.FORM, this.startYear);
                RequestBody endYear = RequestBody.create(MultipartBody.FORM, this.endYear);

                ProgressRequestBody fileBody1 = new ProgressRequestBody(supportImage, "*/*", this, "Uploading SupportFile...");

                MultipartBody.Part supportFile =
                        MultipartBody.Part.createFormData("supportImage", supportImage.getName(), fileBody1);
                ProgressRequestBody fileBody2 = new ProgressRequestBody(profileImage, "*/*", this, "Uploading Profile Picture...");

                MultipartBody.Part profilePic =
                        MultipartBody.Part.createFormData("profilePicture", profileImage.getName(), fileBody2);
                final Service service = DataServiceGenerator.createService(Service.class);
                Call<ApiResponseModel> call = service.registrationWithProfilePic(apiLogin, apiPass, firstName, lastName, mail, phoneNo, address, dob, course, startYear, endYear, profilePic, supportFile);
                call.enqueue(new retrofit2.Callback<ApiResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                        if (weakReference != null) {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                ApiResponseModel apiResponseModel = response.body();
                                if (apiResponseModel.getCode() == 1) {
                                    weakReference.get().startActivity(new Intent(weakReference.get(), LoginActivity.class));
                                        weakReference.get().finish();
                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage() + "");

                                } else {
                                    Toast.makeText(weakReference.get(), apiResponseModel.getMessage() + "");
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        if (weakReference != null)
                            progressDialog.dismiss();
                        Toast.makeText(weakReference.get(), "Server Error.");
                    }
                });
            } catch (Exception e) {
                if (weakReference != null)
                    progressDialog.dismiss();
                e.printStackTrace();
            }


            return null;
        }

        @Override
        public void onProgressUpdate(int percentage, String title) {
            progressDialog.setMessage(title);
            progressDialog.setProgress(percentage);
        }

        @Override
        public void onError() {

        }

        @Override
        public void onFinish(String title) {

        }
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.scale_fade_down);
        finish();

    }
}

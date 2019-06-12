package com.vucs.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vucs.R;
import com.vucs.api.ApiForgotPasswordModel;
import com.vucs.api.ApiLoginModel;
import com.vucs.api.ApiLoginResponseModel;
import com.vucs.api.ApiResponseModel;
import com.vucs.api.Service;
import com.vucs.db.AppDatabase;
import com.vucs.helper.AppPreference;
import com.vucs.helper.Utils;
import com.vucs.service.DataServiceGenerator;
import com.vucs.service.GetDataService;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static String TAG = "Login Activity";
    private final int DURATION = 300;
    LinearLayout linearLayout;
    LinearLayout parent;
    ImageView imageView;
    Button login;
    ProgressBar progressBar;
    FrameLayout frameLayout;
    TextInputLayout user_name, password;
    AppPreference appPreference;
    private boolean forgotPassword=false;
    TextView forgot_password;
    BroadcastReceiver serviceBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e(TAG, "onReceive");

            String action = intent.getStringExtra(getString(R.string.dashboard_receiver_action));
            if (action.equals(getString(R.string.get_data_action))) {
                openHomeActivity();

            } else if (action.equals(getString(R.string.get_data_on_failure_action))) {

                showSnackBar("Error ocurred while updating data");
            }
        }
    };
    private int progressBarWidth, buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        frameLayout = findViewById(R.id.transitions_container);
        parent = findViewById(R.id.parent);
     /*   Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.logo_transition);
        Scene aScene = Scene.getSceneForLayout(viewGroup, R.layout.activity_login, this);
        TransitionManager.go(aScene, transition);*/
        linearLayout = findViewById(R.id.login_layout);
        imageView = findViewById(R.id.logo);
        Animation connectingAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        imageView.startAnimation(connectingAnimation);
        connectingAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (appPreference.getUserId() != -1) {
                    openHomeActivity();
                } else
                    initLoginview();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        login = findViewById(R.id.login_button1);
        progressBar = findViewById(R.id.progress_bar);
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        forgot_password = findViewById(R.id.forgot_password);
        appPreference = new AppPreference(this);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            parent.setOrientation(LinearLayout.HORIZONTAL);

        } else {
            parent.setOrientation(LinearLayout.VERTICAL);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!forgotPassword) {
                    if (user_name.getEditText().getText().toString().isEmpty()) {
                        showSnackBar("Please enter user name");
                    } else if (password.getEditText().getText().toString().isEmpty()) {
                        showSnackBar("Please enter password");
                    } else if (Utils.isNetworkAvailable()) {
                        buttonToProgressBar();
                        new CheckingUser(LoginActivity.this, user_name.getEditText().getText().toString(), password.getEditText().getText().toString()).execute();
                    }
                    else {
                        showSnackBarWithNetworkAction("No internet connection");
                    }
                } else {
                    if (user_name.getEditText().getText().toString().isEmpty()) {
                        showSnackBar("Please enter user name");
                    }
                    else if (Utils.isNetworkAvailable()) {
                       buttonToProgressBar();
                        new ForgetPassword(LoginActivity.this, user_name.getEditText().getText().toString()).execute();
                    }
                    else {
                        showSnackBarWithNetworkAction("No internet connection");
                    }
                }


            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forgotPassword){
                    login.setText("Login");
                    forgot_password.setText("Forgot password");
                    password.setVisibility(View.VISIBLE);
                    forgotPassword=false;
                }
                else {
                    login.setText("Get password");
                    forgot_password.setText("Login");
                    password.setVisibility(View.GONE);
                    forgotPassword=true;
                }
            }
        });

    }


    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.scale_up, R.anim.no_anim);
        finish();

    }

    private void initLoginview() {
        linearLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml("<u>Registration</u>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml("<u>Registration</u>"));
        }
        frameLayout.post(new Runnable() {
            @Override
            public void run() {
                progressBarWidth = progressBar.getWidth();
                buttonWidth = login.getWidth();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.login);

        linearLayout.setAnimation(animation);
        animation.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    private void buttonToProgressBar() {
        final int from = login.getWidth();
        final LinearInterpolator interpolator = new LinearInterpolator();

        login.setAlpha(1.0f);
        login.setVisibility(View.GONE);


        ValueAnimator secondAnimator = ValueAnimator.ofInt(from, progressBarWidth);
        secondAnimator.setTarget(progressBar);
        secondAnimator.setInterpolator(interpolator);
        secondAnimator.setDuration(DURATION);

        final ViewGroup.LayoutParams params = progressBar.getLayoutParams();
        secondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (Integer) animation.getAnimatedValue();
                progressBar.requestLayout();
            }
        });

        secondAnimator.start();
    }

    private void progressBarToButton() {
        final int from = progressBar.getWidth();
        final LinearInterpolator interpolator = new LinearInterpolator();

        progressBar.setAlpha(1.0f);


        login.setVisibility(View.VISIBLE);

        ValueAnimator secondAnimator = ValueAnimator.ofInt(from, buttonWidth);
        secondAnimator.setTarget(login);
        secondAnimator.setInterpolator(interpolator);
        secondAnimator.setDuration(DURATION);

        final ViewGroup.LayoutParams params = login.getLayoutParams();
        secondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (Integer) animation.getAnimatedValue();
                login.requestLayout();
            }
        });

        secondAnimator.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(serviceBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(serviceBroadcastReceiver, new IntentFilter(getString(R.string.fetch_all_data_broad_cast)));
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextAppearance(this, R.style.mySnackbarStyle);
        snackbar.show();
    }

    private void showSnackBarWithNetworkAction(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG)
                .setAction("Open Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                });
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        TextView textView1 = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTextAppearance(this, R.style.mySnackbarStyle);
        textView1.setTextAppearance(this, R.style.mySnackbarStyle);
        textView1.setTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    private void openDialog( String s){
        Dialog dialog = new Dialog(this,R.style.Theme_Design_BottomSheetDialog);
        View view=getLayoutInflater().inflate(R.layout.dialoge_forgot_password,null);
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

    private static class CheckingUser extends AsyncTask<Void, Void, Void> {
        private static WeakReference<LoginActivity> weakReference;
        private final String userName;
        private final String passWord;


        CheckingUser(LoginActivity context, String userName, String passWord) {
            weakReference = new WeakReference<>(context);
            this.userName = userName;
            this.passWord = passWord;
            LoginActivity activity = weakReference.get();
            AppDatabase db = AppDatabase.getDatabase(activity);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoginActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            // activity.buttonToProgressBar();
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
            final LoginActivity activity = weakReference.get();
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                final ApiLoginModel apiLoginModel = new ApiLoginModel(userName, passWord);
                Call<ApiLoginResponseModel> call = service.getUserLogin(apiLoginModel);
                call.enqueue(new Callback<ApiLoginResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiLoginResponseModel> call, @NonNull Response<ApiLoginResponseModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code = " + response.code() + "");
                            if (response.body() != null) {
                                final ApiLoginResponseModel apiLoginResponseModel = response.body();
                                Log.e(TAG, "Response:\n" + apiLoginResponseModel.toString());

                                if (apiLoginResponseModel.getCode() == 1) {
                                    AppPreference appPreference = new AppPreference(weakReference.get());
                                    if (activity == null || activity.isFinishing())
                                        return;

                                    try {
                                        Log.e(TAG, "user_id = " + apiLoginResponseModel.getUserId() + "");
                                        appPreference.setUserId(apiLoginResponseModel.getUserId());
                                        appPreference.setUserType(apiLoginResponseModel.getType());
                                        appPreference.setUserPassword(passWord);
                                        appPreference.setUserName(apiLoginResponseModel.getName());
                                        appPreference.setUserEmail(apiLoginResponseModel.getMail());
                                        appPreference.setUserPhoneNo(apiLoginResponseModel.getPhoneNo());
                                        appPreference.setUserAddress(apiLoginResponseModel.getAddress());
                                        appPreference.setUserImageUrl(apiLoginResponseModel.getImage());
                                        appPreference.setUserDob(apiLoginResponseModel.getDob());
                                        appPreference.setUserCourse(apiLoginResponseModel.getCourse());
                                        appPreference.setUserBatch(apiLoginResponseModel.getBatch());
                                        appPreference.setUserSem(apiLoginResponseModel.getSem());
                                        Thread workThread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                GetDataService.updateData(activity);
                                            }

                                        });
                                        workThread.start();
                                        workThread.join();
                                        Log.e(TAG, "COMPLETE");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Log.e(TAG, apiLoginResponseModel.getMessage());

                                } else {
                                    if (activity == null || activity.isFinishing()) {
                                        return;
                                    }

                                    activity.openDialog(apiLoginResponseModel.getMessage());
                                    activity.progressBarToButton();
                                    //Failure
                                    Log.e(TAG, apiLoginResponseModel.getMessage());
                                }
                            }
                        } else {
                            Log.e(TAG, "Error Response Code = " + response.code() + "");
                            if (activity == null || activity.isFinishing()) {
                                return;
                            }
                            activity.showSnackBar(weakReference.get().getString(R.string.server_error));
                            activity.progressBarToButton();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiLoginResponseModel> call, @NonNull Throwable t) {
                        Log.e(TAG, "fail " + t.getMessage());
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        activity.showSnackBar(weakReference.get().getString(R.string.server_error));
                        activity.progressBarToButton();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class ForgetPassword extends AsyncTask<Void, Void, Void> {
        private static WeakReference<LoginActivity> weakReference;
        private String userName;


        ForgetPassword(LoginActivity context, String userName) {
            weakReference = new WeakReference<>(context);
            this.userName = userName;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoginActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            // activity.buttonToProgressBar();
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
            final LoginActivity activity = weakReference.get();
            try {
                final Service service = DataServiceGenerator.createService(Service.class);
                final ApiForgotPasswordModel apiForgotPasswordModel = new ApiForgotPasswordModel(userName);
                Call<ApiResponseModel> call = service.forgotPassword(apiForgotPasswordModel);
                call.enqueue(new Callback<ApiResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Response code = " + response.code() + "");
                            if (response.body() != null) {
                                final ApiResponseModel apiResponseModel = response.body();
                                Log.e(TAG, "Response:\n" + apiResponseModel.toString());

                                if (apiResponseModel.getCode() == 1) { ;
                                    if (activity == null || activity.isFinishing())
                                        return;

                                    try {
                                        activity.openDialog(apiResponseModel.getMessage());
                                        activity.progressBarToButton();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Log.e(TAG, apiResponseModel.getMessage());

                                } else {
                                    if (activity == null || activity.isFinishing()) {
                                        return;
                                    }

                                    activity.openDialog(apiResponseModel.getMessage());
                                    activity.progressBarToButton();
                                    //Failure
                                    Log.e(TAG, apiResponseModel.getMessage());
                                }
                            }
                        }
                        else {
                            Log.e(TAG, "Error Response Code = " + response.code() + "");
                            if (activity == null || activity.isFinishing()) {
                                return;
                            }
                            activity.showSnackBar(weakReference.get().getString(R.string.server_error));
                            activity.progressBarToButton();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                        Log.e(TAG, "fail " + t.getMessage());
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        activity.showSnackBar(weakReference.get().getString(R.string.server_error));
                        activity.progressBarToButton();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (forgotPassword){
            login.setText("Login");
            forgot_password.setText("Forgot password");
            password.setVisibility(View.VISIBLE);
            forgotPassword=false;
        }
        else {
            super.onBackPressed();
        }

    }
}


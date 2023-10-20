package com.app.treatEasy.feature.login_module.otp_verification;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;

import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.new_feature.login.LoginResponse;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.app.treatEasy.views.pinview.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends BaseActivity {

    @Inject
    public BaseViewModelFactory mViewModelFactory;

    private OtpVerifyViewModel mViewModel;
    private UserProfileModel mVerifyOTPModel;
    private PinView mPinView;
    private TextView mOtpError,mResendOtp,tvTimer;
    private String mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(OtpVerifyViewModel.class);
      //  mVerifyOTPModel = new UserProfileModel();

        initializeView();

        tvTimer=findViewById(R.id.tvTimer);

        /* count timer */
       TimeCount();
    }

    private void initializeView() {
        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);
        if (bundle != null) {
             mobileNumber = bundle.getString(MOBILE_NUMBER);
           // mVerifyOTPModel.mobileNumber = mobileNumber;
            ((TextView) findViewById(R.id.txt_pin)).setText(String.format(getString(R.string.enter_the_5_digit_code_sent_via_sms_on_91_1_s), mobileNumber));
        }
        setUpToolBar(null, true);
        findViewById(R.id.toolbar_up_btn_activity).setOnClickListener(this);
        spanClick();

        ((TextView) findViewById(R.id.txt_next)).setText(getString(R.string.continue_str));

        mPinView = findViewById(R.id.pin_view);
       // mPinView.setText("12345");
        mOtpError = findViewById(R.id.tv_otp_error);

        findViewById(R.id.ll_next).setOnClickListener(v -> {

            otpVerification(mobileNumber,mPinView.getText().toString());
           /* mVerifyOTPModel.otp = mPinView.getText().toString();
            if (mVerifyOTPModel.isOTPValid()) {
                mOtpError.setVisibility(View.GONE);
                verifyOTPAction();
            } else{
                mOtpError.setVisibility(View.VISIBLE);
            }*/
        });
      //  observeData();
    }

    private void verifyOTPAction() {
        BaseUtils.hideKeyBoard(OtpVerificationActivity.this);
        mViewModel.verifyOtpAction(mVerifyOTPModel);
    }

    /*Click spannable string on Resend OTP*/
    private void spanClick() {
         mResendOtp = findViewById(R.id.tv_resend_otp);
        View.OnClickListener resendOtpListner = v -> {

            otpResend(mobileNumber);
           /* BaseUtils.hideKeyBoard(OtpVerificationActivity.this);
            UserProfileModel.PostOtpModel model = new UserProfileModel.PostOtpModel();
            model.mobile = mVerifyOTPModel.mobileNumber;
            mViewModel.resendOTPAction(model);*/
        };
        onSpannableClick(getString(R.string.resend_pin), mResendOtp, resendOtpListner);
    }

    /**
     * Listen live data from login view model
     */
    private void observeData() {
        mViewModel.getVerifyOTPData().observe(this, this::observeVerifyOTPData);
        mViewModel.getResendOTPData().observe(this, this::observeResendOTPData);
    }

    private void observeVerifyOTPData(Resource<UserProfileModel> otpVerifyModelResource) {
        switch (otpVerifyModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                saveUserDataInPreference(otpVerifyModelResource.mData.data);
                AppPreferences.getPreferenceInstance(this).setUserLogin(true);
               // AppPreferences.getPreferenceInstance(this).setUserLocation(otpVerifyModelResource.mData.data.cityId);
                AppPreferences.getPreferenceInstance(this).setUserId(otpVerifyModelResource.mData.data.uid);
                AppPreferences.getPreferenceInstance(this).setMobile(otpVerifyModelResource.mData.data.mobileNo);
                finishAffinity();
                switchActivity(HomeActivity.class);
                finish();
                break;
            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                break;

            default:
                break;
        }
    }


    private void observeResendOTPData(Resource<UserProfileModel> otpVerifyModelResource) {
        switch (otpVerifyModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();

//                if (loginModel.mServerStatus == 1) {
//                    AppPreferences.getPreferenceInstance(this).setUserLogin(true);
//                    switchActivity(HomeActivity.class);
//                    finish();
//                } else {
//                    View.OnClickListener onClickListener = v -> switchActivity(SignUpActivity.class);
//                    showAlertMessageDialog(getString(R.string.failure), loginModel.mMessage,
//                            onClickListener, getString(R.string.tv_ok), null, null);
//                }
                break;

            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                break;

            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.toolbar_up_btn_activity:
                onBackPressed();
                break;
        }
    }

    public void otpVerification(String mobile,String otp){
        showProgressDialog();
        Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().otpVerification(mobile,otp);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserLocation(response.body().getData().getCityId());
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserId(response.body().getData().getUid());
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setMobile(response.body().getData().getMobileNo());
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserName(response.body().getData().getPatientName());
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserImage(response.body().getData().getProfileImage());
                    finishAffinity();
                    Toast.makeText(OtpVerificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    switchActivity(HomeActivity.class);
                }else{
                    showAlertMessageDialog(getString(R.string.failure),response.body().getMessage(),
                            null, getString(R.string.tv_ok), null, null);
                 //   Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void otpResend(String mobile){
        showProgressDialog();
        Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().otpResend(mobile);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    TimeCount();
                    Toast.makeText(OtpVerificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   /* AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserLocation(response.body().getData().getCityId());
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserId(response.body().getData().getUid());
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserName(response.body().getData().getPatientName());
                    AppPreferences.getPreferenceInstance(OtpVerificationActivity.this).setUserImage(response.body().getData().getProfileImage());
                    finishAffinity();
                    switchActivity(HomeActivity.class);*/
                }else{
                    showAlertMessageDialog(getString(R.string.failure),response.body().getMessage(),
                            null, getString(R.string.tv_ok), null, null);
                    //   Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void TimeCount(){
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                mResendOtp.setVisibility(View.GONE);
                tvTimer.setVisibility(View.VISIBLE);
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                tvTimer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }
            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                mResendOtp.setVisibility(View.VISIBLE);
                tvTimer.setVisibility(View.GONE);
                tvTimer.setText("00:00:00");
            }
        }.start();
    }
}

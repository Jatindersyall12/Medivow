package com.app.treatEasy.feature.login_module.login_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProviders;
import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.login_module.otp_verification.OtpVerificationActivity;
import com.app.treatEasy.feature.login_module.signup_newuser.SignUpActivity;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.new_feature.login.LoginResponse;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private TextView mMobileError;
    private EditText mMobileNumber;
    private LoginViewModel mLoginViewModel;

    @Inject
    public BaseViewModelFactory mViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        mLoginViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);

        initializeView();

      //  observeData();
    }
    private void initializeView() {
        mMobileNumber = findViewById(R.id.et_phone_number);
        mMobileError = findViewById(R.id.tv_phone_error);
        findViewById(R.id.ll_next).setOnClickListener(this);

        ((TextView) findViewById(R.id.txt_next)).setText(getString(R.string.send_otp));

        setClickSpan();
    }


    /**
     * post login data to call login api
     *
     * @param loginUser
     */
    private void loginAction(LoginModel loginUser) {
        mLoginViewModel.loginAction(loginUser);
    }
    /**
     * Set sign up text and click function
     */
    private void setClickSpan() {
        TextView mSignUp = findViewById(R.id.tv_sign_up);
        View.OnClickListener signUpListner = v -> switchActivity(SignUpActivity.class);
        onSpannableClick(getString(R.string.sign_up_now), mSignUp, signUpListner);
    }

    private void observeData() {
        mLoginViewModel.getUserDetail().observe(this, this::observeLoginData);
    }
    /**
     * Observe data from view model and update ui according to user field validation and api success
     * or error case
     */
    private void observeLoginData(Resource<LoginModel> loginModelResource) {
        switch (loginModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                LoginModel loginModel = loginModelResource.mData;

                if (loginModel.mServerStatus == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(MOBILE_NUMBER, mMobileNumber.getText().toString());
                    switchActivity(OtpVerificationActivity.class, bundle);
                } else {
                    View.OnClickListener onClickListener = v -> switchActivity(SignUpActivity.class);
                    showAlertMessageDialog(getString(R.string.failure), loginModel.mMessage,
                            null, getString(R.string.tv_ok), null, null);
                }
                break;

            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                break;

            case VALIDATION:
                LoginModel loginUser = loginModelResource.mData;
                assert loginUser != null;
                if (!loginUser.isPhoneNoValid()) {
                    setResetView(mMobileError, mMobileNumber);

                    /*Call login api when all condition are true*/
                    if (!BaseUtils.checkNetwork(this))
                        showInternetErrorDialog(null);
                    else
                        loginAction(loginUser);

                } else {
                    setErrorView(mMobileError, getString(R.string.wrong_credential), mMobileNumber,
                            false);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        BaseUtils.hideKeyBoard(LoginActivity.this);
        /*LoginModel model = new LoginModel();
        model.mobileNumber = mMobileNumber.getText().toString();
        mLoginViewModel.onClick(model);*/
        if (mMobileNumber.getText().toString().isEmpty()){
            mMobileNumber.setError("Please enter mobile number");
        }else if (mMobileNumber.getText().toString().length()<10){
            mMobileNumber.setError("Please enter valid mobile number");
        }else {
            login(mMobileNumber.getText().toString());
        }
    }
    public void login(String mobile){
        showProgressDialog();
        Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().login(mobile,
                AppPreferences.getPreferenceInstance(LoginActivity.this).getFCMToken());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString(MOBILE_NUMBER, mMobileNumber.getText().toString());
                    switchActivity(OtpVerificationActivity.class, bundle);

                }else{
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}

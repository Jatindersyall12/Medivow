package com.app.treatEasy.feature.login_module.signup_newuser;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.login_module.TermAndConditionActivity;
import com.app.treatEasy.feature.login_module.login_screen.LoginActivity;
import com.app.treatEasy.feature.login_module.otp_verification.OtpVerificationActivity;
import com.app.treatEasy.feature.login_module.otp_verification.UserDetailModel;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.new_feature.login.LoginResponse;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.CityAdapter;
import com.app.treatEasy.state.CityResponseModel;
import com.app.treatEasy.state.RetrofitClient;
import com.app.treatEasy.state.SpinAdapter;
import com.app.treatEasy.state.StateResponseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {

    @Inject
    public BaseViewModelFactory mViewModelFactory;

    private SignUpViewModel mViewModel;
    private UserDetailModel signUpModel;
    private Spinner spState,spCity;
    private EditText mName, mPhoneNumber, mDOB;
    private TextView mNameError, mPhoneNumberError, mDOBError,tvTerm,tvSelectState,tvSelectCity;
    List<StateResponseModel.Datum>stateList;
    List<CityResponseModel.Datum>cityList;
    String pinCode="",stateId="";
    CheckBox chTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        stateList=new ArrayList<>();
        cityList=new ArrayList<>();

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SignUpViewModel.class);
        signUpModel = new UserDetailModel();

       // observeData();

        initializeView();
        getStateList();

        tvSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectState.setVisibility(View.GONE);
                spState.performClick();
                spState.setVisibility(View.VISIBLE);

            }
        });

        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectCity.setVisibility(View.GONE);
                spCity.performClick();
                spCity.setVisibility(View.VISIBLE);

            }
        });

        tvTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(TermAndConditionActivity.class);
            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pinCode=cityList.get(i).getId();
                String  city=cityList.get(i).getId();
                AppPreferences.getPreferenceInstance(SignUpActivity.this).setCityNAme(city);
                Log.d("model","pincode"+pinCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateId=stateList.get(i).getId();
                getCityList(stateList.get(i).getValue());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initializeView() {
       // setUpToolBarBlack(getString(R.string.signup));
        findViewById(R.id.toolbar_up_btn_activity).setOnClickListener(this);

        loginSpanClick();

        mName = findViewById(R.id.et_first_name);
        mPhoneNumber = findViewById(R.id.et_phone_number);
        mDOB = findViewById(R.id.et_dob);
        tvTerm = findViewById(R.id.tvTerm);
        chTerm = findViewById(R.id.chTerm);
        tvSelectState = findViewById(R.id.tvSelectState);
         tvSelectCity = findViewById(R.id.tvSelectCity);
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);

        mNameError = findViewById(R.id.tv_first_name_error);
        mPhoneNumberError = findViewById(R.id.tv_phone_number_error);
        mDOBError = findViewById(R.id.tv_dob_error);

        findViewById(R.id.et_dob).setOnClickListener(v -> openDatePicker(mDOB));

        ((TextView) findViewById(R.id.txt_next)).setText(getString(R.string.sign_up));
        findViewById(R.id.txt_next).setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               if (mName.getText().toString().isEmpty()){
                                                                   mName.setError("please enter name");
                                                               }else if (mPhoneNumber.getText().toString().isEmpty()){
                                                                   mPhoneNumber.setError("please enter mobile number");
                                                               }else if (mDOB.getText().toString().isEmpty()){
                                                                   mDOB.setError("please enter dob");
                                                               }else if (stateId.equals("")){
                                                                   Toast.makeText(SignUpActivity.this, "Please select state ", Toast.LENGTH_SHORT).show();
                                                               }
                                                               else if (pinCode.equals("")){
                                                                   Toast.makeText(SignUpActivity.this, "Please select city ", Toast.LENGTH_SHORT).show();
                                                               }

                                                               else if (!chTerm.isChecked()){
                                                                  // mDOB.setError("please check term and condition");
                                                                   Toast.makeText(SignUpActivity.this, "please check term and condition", Toast.LENGTH_SHORT).show();
                                                               }
                                                               else {

                                                                   signUp(mName.getText().toString(),mPhoneNumber.getText().toString(),
                                                                           mDOB.getText().toString(),
                                                                           AppPreferences.getPreferenceInstance(SignUpActivity.this).getFCMToken(),
                                                                           stateId,pinCode);

                                                               }
                                                           }
                                                       }
        );
    }

    private void signUpAction() {
        BaseUtils.hideKeyBoard(SignUpActivity.this);
        signUpModel = new UserDetailModel();
        signUpModel.userName = mName.getText().toString();
        signUpModel.phone = mPhoneNumber.getText().toString();
        signUpModel.dateOfBirth = mDOB.getText().toString();
        signUpModel.pincode =pinCode ;
        mViewModel.onClick(signUpModel);
    }

    private void loginSpanClick() {
        TextView mSignIn = findViewById(R.id.tv_sign_in);
        View.OnClickListener signInListner = v -> switchActivity(LoginActivity.class);
        onSpannableClick(getString(R.string.sign_in_now), mSignIn, signInListner);
    }

    private void observeData() {
        mViewModel.getUserDetail().observe(this, this::observeSignUpData);
    }

    private void observeSignUpData(Resource<UserDetailModel> signUpModelResource) {
        switch (signUpModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                UserDetailModel model = signUpModelResource.mData;
                Bundle bundle = new Bundle();
                bundle.putString(USER_ID, model.uid);
                bundle.putString(MOBILE_NUMBER, mPhoneNumber.getText().toString());
                Log.d("model","modellvalue"+pinCode);
                AppPreferences.getPreferenceInstance(this).setUserLocation(pinCode);
                AppPreferences.getPreferenceInstance(this).setUserId(model.uid);
                switchActivity(OtpVerificationActivity.class, bundle);

                break;
            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                break;
            case VALIDATION:
                UserDetailModel signUpModel = signUpModelResource.mData;
                setResetView(mNameError, mName);
                setResetView(mPhoneNumberError, mPhoneNumber);
                setResetView(mDOBError, mDOB);

                assert signUpModel != null;
                if (!signUpModel.isUserNameValid()) {
                    setErrorView(mNameError, getString(R.string.valid_first_name), mName,
                            !TextUtils.isEmpty(mName.getText().toString()));
                } else if (signUpModel.isValidPhoneNumber()) {
                    setErrorView(mPhoneNumberError, getString(R.string.valid_phone_number), mPhoneNumber,
                            TextUtils.isEmpty(mPhoneNumber.getText().toString()));
                }else if (mDOB.getText().toString().equals("")) {
                    setErrorView(mDOBError, getString(R.string.valid_dob), mDOB,
                            !TextUtils.isEmpty(mDOB.getText().toString()));
                } else {
                    /*Call login api when all condition are true*/
                    if (!BaseUtils.checkNetwork(this))
                        showInternetErrorDialog(null);
                    else
                        mViewModel.signUpAction(signUpModel);
                }
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

/* signup*/
    public void signUp(String userName,String mobile,String dob,String token,String state,String city){
        Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().signUp(userName,mobile,dob,token,
                state,city);
        call.enqueue(new Callback<LoginResponse>() { 
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getStatusCode()==200){
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString(MOBILE_NUMBER, mPhoneNumber.getText().toString());
                    Log.d("model","modellvalue"+pinCode);
                    Log.d("token","token"+token);
                    switchActivity(OtpVerificationActivity.class,bundle);

                }else if (response.body().getStatusCode()==203){
                    Toast.makeText(getApplicationContext(), "User Already Registered For This Number", Toast.LENGTH_LONG).show();
               finish();
                }
                else {

                    showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                            null, getString(R.string.tv_ok), null, null);
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void getStateList(){
        Call<StateResponseModel> call = RetrofitClient.getInstance().getMyApi().getStateList();
        call.enqueue(new Callback<StateResponseModel>() {
            @Override
            public void onResponse(Call<StateResponseModel> call, Response<StateResponseModel> response) {
                stateList=response.body().getData();
                if (stateList!=null&& stateList.size()>0){
                    SpinAdapter adapter=new SpinAdapter(SignUpActivity.this,stateList);
                    spState.setAdapter(adapter);
                    getCityList(stateList.get(0).getValue());
                }
            }
            @Override
            public void onFailure(Call<StateResponseModel> call, Throwable t) {
               Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void getCityList(String value){
        showProgressDialog();
        cityList=new ArrayList<>();
       /* CitySendModel citySend=new CitySendModel();
        citySend.setStateid(value);*/
        Call<CityResponseModel> call = RetrofitClient.getInstance().getMyApi().getCityList(value);
        call.enqueue(new Callback<CityResponseModel>() {
            @Override
            public void onResponse(Call<CityResponseModel> call, Response<CityResponseModel> response) {
              dismissProgressDialog();
                cityList=response.body().getData();
                CityAdapter adapter=new CityAdapter(SignUpActivity.this,cityList);
                spCity.setAdapter(adapter);
              /*  if (cityList!=null&& cityList.size()>0){
                    CityAdapter adapter=new CityAdapter(SignUpActivity.this,cityList);
                    spCity.setAdapter(adapter);
                }else {
                   // Toast.makeText(SignUpActivity.this, "No city found", Toast.LENGTH_SHORT).show();
                }*/

            }
            @Override
            public void onFailure(Call<CityResponseModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }
    }


package com.app.treatEasy.feature.edit_profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.login_module.otp_verification.UserDetailModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserProfileModel;
import com.app.treatEasy.feature.profile.ProfileActivity;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.permissions.AbstractPermissionActivity;
import com.app.treatEasy.permissions.PermissionUtils;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.profile.GetProfileResponse;
import com.app.treatEasy.state.CityAdapter;
import com.app.treatEasy.state.CityResponseModel;
import com.app.treatEasy.state.CitySendModel;
import com.app.treatEasy.state.RetrofitClient;
import com.app.treatEasy.state.SpinAdapter;
import com.app.treatEasy.state.StateResponseModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AbstractPermissionActivity implements BaseActivity.OnCapturedListener {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private EditProfileViewModel mViewModel;
    private boolean mIsUserImage;
    private EditText mUserName, mMobileNumber, mEmail, mAddress, mPolicyName, mCompanyName;
    private TextView mUserNameError, mDOBError, mMobileNumberError, mEmailError,
            mGenderError, mProfessionError, mDOB, tvGender, tvSelectState, tvSelectCity;
    private Spinner mGender, mProfession, spState, spCity;
    private ImageView mProfileImage, mGovtId;
    private RadioGroup mRadioInsurance;
    List<StateResponseModel.Datum> stateList;
    List<CityResponseModel.Datum> cityList;
    String pinCode = "", profession = "", gender = "", stateId, cityId;
    Uri profileUri, govIdUri;
    MultipartBody.Part imageProfile;
    MultipartBody.Part imageGov;
    ArrayAdapter professionAdapter;
    ArrayAdapter genderAdapter;
    SpinAdapter adapter;
    String healthStatus;
    LinearLayout llUploadProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        // mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EditProfileViewModel.class);
        initView();
        //location=AppPreferences.getPreferenceInstance(getActivity()).getUserLocation();

        //  getStateList();
        //  getProfileData(AppPreferences.getPreferenceInstance(this).getUserId());
        // observeData();

        mGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (GENDER[i].equals("Male")) {
                    gender = "1";
                } else if (GENDER[i].equals("Female")) {
                    gender = "2";
                } else if (GENDER[i].equals("Others")) {
                    gender = "3";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                profession = PROFESSION[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pinCode = cityList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateId = stateList.get(i).getId();
                getCityList(stateList.get(i).getValue());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView() {

        setUpToolBar(getString(R.string.edit_profile), true);

        ((TextView) findViewById(R.id.txt_next)).setText(getString(R.string.save));
        findViewById(R.id.ll_next).setOnClickListener(v -> updateProfileData());

        tvSelectCity = findViewById(R.id.tvSelectCity);
        tvGender = findViewById(R.id.tvGender);
        tvSelectState = findViewById(R.id.tvSelectState);
        mUserName = findViewById(R.id.et_full_name);
        mDOB = findViewById(R.id.et_dob);
        mMobileNumber = findViewById(R.id.et_phone_number);
        mEmail = findViewById(R.id.et_email_address);
        mGender = findViewById(R.id.et_gender);
        mProfession = findViewById(R.id.et_profession);
        mAddress = findViewById(R.id.et_address);
        mCompanyName = findViewById(R.id.et_company_detail);
        mPolicyName = findViewById(R.id.et_policy_number);
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);

        llUploadProfile = findViewById(R.id.llUploadProfile);

        mUserNameError = findViewById(R.id.tv_full_name_error);
        mDOBError = findViewById(R.id.tv_dob_error);
        mMobileNumberError = findViewById(R.id.tv_phone_number_error);
        mEmailError = findViewById(R.id.tv_email_error);
        mGenderError = findViewById(R.id.tv_gender_error);
        mProfessionError = findViewById(R.id.tv_profession_error);

        mProfileImage = findViewById(R.id.img_user_image);
        mGovtId = findViewById(R.id.img_govt_id);

        mRadioInsurance = findViewById(R.id.radio_health_insurance);

        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectCity.setVisibility(View.GONE);
                spCity.setVisibility(View.VISIBLE);
                spCity.performClick();
            }
        });

        tvSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectState.setVisibility(View.GONE);
                spState.setVisibility(View.VISIBLE);
                spState.performClick();
            }
        });

        tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGender.setVisibility(View.GONE);
                mGender.setVisibility(View.VISIBLE);
                mGender.performClick();
            }
        });

        findViewById(R.id.govt_id_layout).setOnClickListener(this);
        findViewById(R.id.et_dob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(mDOB);
            }
        });
        findViewById(R.id.govt_id_layout).setOnClickListener(this);
        findViewById(R.id.profile_image_layout).setOnClickListener(this);

        genderAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                GENDER);
        genderAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mGender.setAdapter(genderAdapter);

        professionAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                PROFESSION);
        professionAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mProfession.setAdapter(professionAdapter);

        mRadioInsurance.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_yes:
                    healthStatus = "1";
                    findViewById(R.id.policy_layout).setVisibility(View.VISIBLE);
                    break;

                case R.id.rb_no:
                    healthStatus = "0";
                    mPolicyName.setText("");
                    mCompanyName.setText("");
                    findViewById(R.id.policy_layout).setVisibility(View.GONE);
                    break;
            }
        });
        findViewById(R.id.policy_layout).setVisibility(View.VISIBLE);
        setOnCapturedListener(this);
        // setUpData(parseUserData());

        getStateList();
    }

    private void setUpData(UserDetailModel data) {

        mUserName.setText(data.patientName);
        mDOB.setText(data.dob);
        mMobileNumber.setText(data.mobileNo);
        mMobileNumber.setText(data.email);
        // mBinding.etProfession.setText(data.profession);
        mAddress.setText(data.address);

        switch (data.gender) {
            case "Female":
                break;

            case "Others":
                mGender.setSelection(2);
                break;
            case "Male":
            default:
                mGender.setSelection(0);
                break;
        }

        if (!data.profileImage.isEmpty())
            setImage(data.profileImage, mProfileImage);
    }

    private void onUpdateProfileAction() {

        setResetView(mUserNameError, mUserName);
        setResetView(mMobileNumberError, mMobileNumber);
        //  setResetView(mDOBError, mDOB);
        setResetView(mEmailError, mEmail);
        // setResetView(mGenderError, null);
        // setResetView(mProfessionError, null);

        if (mUserName.getText().toString().isEmpty()) {
            setErrorView(mUserNameError, getString(R.string.valid_first_name), mUserName,
                    !TextUtils.isEmpty(mUserName.getText().toString()));
        } else {
            UserProfileModel.PostUpdateProfile model = new UserProfileModel.PostUpdateProfile();
            model.userid = parseUserData().uid;
            model.patientName = mUserName.getText().toString();
            model.dob = mDOB.getText().toString();
            model.email = mEmail.getText().toString();
            model.mobileNo = mMobileNumber.getText().toString();
            model.address = mAddress.getText().toString();
            model.profession = mGender.toString();
            model.companyName = mCompanyName.getText().toString();
            model.policyNumber = mPolicyName.getText().toString();
            model.healthStatus = 1;
            model.gsmToken = AppPreferences.getPreferenceInstance(this).getFCMToken();
            model.pincode = pinCode;
            mViewModel.updateProfileAction(model);
        }
    }

    private void observeData() {
        mViewModel.getUploadProfileImageData().observe(this, this::observeUploadProfileData);
        mViewModel.getUpdateProfileData().observe(this, this::observeUpdatedProfileData);
    }

    private void observeUploadProfileData(Resource<BaseResponseModel> baseResponseModelResource) {
        switch (baseResponseModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
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

    private void observeUpdatedProfileData(Resource<UserProfileModel> userProfileModelResource) {
        switch (userProfileModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();

                setResult(RESULT_OK);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.profile_image_layout:
                checkCameraPermission(true);
                break;

            case R.id.govt_id_layout:
                checkCameraPermission(false);
                break;

            case R.id.dob_layout:
                openDatePicker(mDOB);
                break;
        }
    }

    public void checkCameraPermission(boolean isImage) {
        mIsUserImage = isImage;
        String[] permissionSet = {PermissionUtils.WRITE_EXTERNAL_STORAGE, PermissionUtils.READ_EXTERNAL_STORAGE,
                PermissionUtils.CAMERA};
        requestEach(new ArrayList<>(Arrays.asList(permissionSet)), permissionResult -> {
            if (permissionResult.isPermissionGranted) {
                showPicDialog();
            } else if (permissionResult.isPermissionDenied) {
                Log.d(getClass().getSimpleName(), "Permission Denied");
            }
        });
    }

    @Override
    public View getLayoutRootView() {

        return findViewById(R.id.root_layout);
    }

    @Override
    public void onCaptured(Uri uri) {
        if (mIsUserImage) {
            profileUri = uri;
            setImageBitmap(mProfileImage, uri);
            Log.d("profile image", uri.toString());
            // mViewModel.uploadProfileImageAction(parseUserData().uid, uri);
        } else {
            setImageBitmap(mGovtId, uri);
            govIdUri = uri;
            mGovtId.setVisibility(View.VISIBLE);
            findViewById(R.id.img_camera).setVisibility(View.GONE);
            Log.d("mGovtId image", uri.toString());
            //  mViewModel.uploadGovtImageAction(parseUserData().uid, uri);
        }
    }

    public void getStateList() {
        Call<StateResponseModel> call = RetrofitClient.getInstance().getMyApi().getStateList();
        call.enqueue(new Callback<StateResponseModel>() {
            @Override
            public void onResponse(Call<StateResponseModel> call, Response<StateResponseModel> response) {
                stateList = response.body().getData();
                if (stateList != null && stateList.size() > 0) {
                    adapter = new SpinAdapter(EditProfileActivity.this, stateList);
                    spState.setAdapter(adapter);
                    getCityList(stateList.get(0).getValue());

                    getProfileData(AppPreferences.getPreferenceInstance(EditProfileActivity.this).getUserId());
                }

            }

            @Override
            public void onFailure(Call<StateResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getProfileData(String userId) {

        Call<GetProfileResponse> call = RetrofitClient.getInstance().getMyApi().getProfile(userId);
        call.enqueue(new Callback<GetProfileResponse>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.body().getStatusCode() == 200) {

                    stateId = response.body().getData().getStateId();
                    cityId = response.body().getData().getCityId();
                    healthStatus = response.body().getData().getHealthStatus();
                    //  selectState();
                    mUserName.setText(response.body().getData().getPatientName());
                    mMobileNumber.setText(response.body().getData().getMobileNo());
                    if (response.body().getData().getStateName() != null && !response.body().getData().getStateName().equals("")) {
                        tvSelectState.setText(response.body().getData().getStateName());
                    }
                   /* if (!response.body().getData().getGender().equals("")){
                        tvGender.setText(response.body().getData().getGender());
                    }*/
                    if (response.body().getData().getCityName() != null && !response.body().getData().getCityName().equals("")) {
                        tvSelectCity.setText(response.body().getData().getCityName());
                    }
                    Log.d("mobile no", "mobile number" + response.body().getData().getMobileNo());
                    mDOB.setText(response.body().getData().getDob());
                    mEmail.setText(response.body().getData().getEmail());

                   /* if (!response.body().getData().getGender().isEmpty()&&response.body().getData().getGender() != null) {
                        int spinnerPosition = genderAdapter.getPosition(response.body().getData().getGender());
                        mGender.setSelection(spinnerPosition);
                    }*/
                    if (response.body().getData().getGender().equals("1")) {
                        mGender.setSelection(0);
                    } else if (response.body().getData().getGender().equals("2")) {
                        mGender.setSelection(1);
                    } else if (response.body().getData().getGender().equals("3")) {
                        mGender.setSelection(2);
                    }
                    tvGender.setVisibility(View.GONE);
                    mGender.setVisibility(View.VISIBLE);

                    mAddress.setText(response.body().getData().getAddress());
                    mPolicyName.setText(response.body().getData().getPolicyNumber());
                    mCompanyName.setText(response.body().getData().getCompanyName());
                    if (response.body().getData().getProfileImage() != null && !response.body().getData().getProfileImage().isEmpty())
                        setImage(response.body().getData().getProfileImage(), mProfileImage);
                    if (response.body().getData().getGovtId() != null && !response.body().getData().getGovtId().isEmpty())
                        setImage(response.body().getData().getGovtId(), mGovtId);
                    if (!response.body().getData().getGovtId().equals("")) {
                        mGovtId.setVisibility(View.VISIBLE);
                        findViewById(R.id.img_camera).setVisibility(View.GONE);
                    }
                    if (response.body().getData().getProfession() != null) {
                        int spinnerPosition = professionAdapter.getPosition(response.body().getData().getProfession());
                        mProfession.setSelection(spinnerPosition);
                    }

                    if (response.body().getData().getHealthStatus().equals("1")) {
                        // int gp=mRadioInsurance.getCheckedRadioButtonId();
                        mRadioInsurance.check(R.id.rb_yes);
                        // findViewById(R.id.rb_no).setActivated(false);
                    } else {
                        mRadioInsurance.check(R.id.rb_no);
                        //  findViewById(R.id.rb_yes).setActivated(false);
                    }

                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectState() {

        for (int i = 0; i < stateList.size(); i++) {
            if (stateId.equals(stateList.get(i).getId())) {
                spState.setSelection(i);
                //selectCity();
            }
        }
    }

    private void selectCity() {
        for (int i = 0; i < cityList.size(); i++) {
            if (cityId.equals(cityList.get(i).getId())) {
                spCity.setSelection(i);
            }
        }
    }

    public void updateProfileData() {

        showProgressDialog();

        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"),
                AppPreferences.getPreferenceInstance(this).getUserId());
        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), mUserName.getText().toString());
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), mDOB.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mEmail.getText().toString());
        // RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"),mMobileNumber.getText().toString());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), mAddress.getText().toString());
        RequestBody cityId = RequestBody.create(MediaType.parse("text/plain"), pinCode);
        RequestBody state_id = RequestBody.create(MediaType.parse("text/plain"), stateId);
        RequestBody profess = RequestBody.create(MediaType.parse("text/plain"), profession);
        RequestBody hStatus = RequestBody.create(MediaType.parse("text/plain"), healthStatus);
        RequestBody gen = RequestBody.create(MediaType.parse("text/plain"), gender);

        if (profileUri != null) {
            File file = new File(profileUri.getPath());
            File finalImage = saveBitmapToFile(file);
            RequestBody profilePic = RequestBody.create(MediaType.parse("multipart/form-data"), finalImage);
            imageProfile = MultipartBody.Part.createFormData("profile_image", file.getName(), profilePic);
        }
        RequestBody cName = RequestBody.create(MediaType.parse("text/plain"), mCompanyName.getText().toString());
        RequestBody policyNumber = RequestBody.create(MediaType.parse("text/plain"), mPolicyName.getText().toString());
       /* RequestBody gsm = RequestBody.create(MediaType.parse("text/plain"),AppPreferences.getPreferenceInstance(this).getFCMToken());
        RequestBody pin = RequestBody.create(MediaType.parse("text/plain"),pinCode);*/
        if (govIdUri != null) {
            File fileGov = new File(govIdUri.getPath());
            File govImage = saveBitmapToFile(fileGov);
            RequestBody govId = RequestBody.create(MediaType.parse("multipart/form-data"), govImage);
            imageGov = MultipartBody.Part.createFormData("govt_id", govImage.getName(), govId);
        }


        Call<UpdateResponse> call = RetrofitClient.getInstance().getMyApi().updateProfile(UserId, userName, dob,
                email, gen, imageProfile, address, cityId, state_id, profess, hStatus, cName, policyNumber, imageGov);
        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {
                    AppPreferences.getPreferenceInstance(EditProfileActivity.this).setUserLocation(response.body().getData().getCityId());
                    AppPreferences.getPreferenceInstance(EditProfileActivity.this).setUserName(response.body().getData().getPatientName());
                    AppPreferences.getPreferenceInstance(EditProfileActivity.this).setMobile(response.body().getData().getMobileNo());
                    AppPreferences.getPreferenceInstance(EditProfileActivity.this).setUserImage(response.body().getData().getProfileImage());
                    Log.d("cityId", "cityId" + response.body().getData().getCityId());
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                    // switchActivity(ProfileActivity.class);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCityList(String value) {
        //  cityList=new ArrayList<>();
        showProgressDialog();
        CitySendModel citySend = new CitySendModel();
        citySend.setStateid(value);
        Call<CityResponseModel> call = RetrofitClient.getInstance().getMyApi().getCityList(value);
        call.enqueue(new Callback<CityResponseModel>() {
            @Override
            public void onResponse(Call<CityResponseModel> call, Response<CityResponseModel> response) {
                dismissProgressDialog();
                cityList = response.body().getData();
                //  if (cityList!=null&& cityList.size()>0){
                CityAdapter adapter = new CityAdapter(EditProfileActivity.this, cityList);
                spCity.setAdapter(adapter);

                // selectCity();

                /*}else {
                  //  Toast.makeText(EditProfileActivity.this, "No city found", Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<CityResponseModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }
}
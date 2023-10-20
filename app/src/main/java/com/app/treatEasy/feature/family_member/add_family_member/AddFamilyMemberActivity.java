package com.app.treatEasy.feature.family_member.add_family_member;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.family_member.AddMemberModel;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.permissions.AbstractPermissionActivity;
import com.app.treatEasy.permissions.PermissionUtils;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFamilyMemberActivity extends AbstractPermissionActivity implements BaseActivity.OnCapturedListener {

    private Spinner mRelations,mGender;
    private ImageView mProfileImage;
    private LinearLayout llUploadProfile;
    private EditText mDOB, mFullName;
    private TextView mFullNameError, mDobError, mRelationError,tvRelation,tvGender;
    private Uri mUri;
    String relation,gender;
    Button btnSaveMember;
    MultipartBody.Part imageProfile;
    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private AddFamilyViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
      //  mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddFamilyViewModel.class);

        initView();

        mGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (GENDER[i].equals("Male")){
                    gender="1";
                }else if (GENDER[i].equals("Female")){
                    gender="2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mRelations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                relation=RELATIONS[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       // observeData();
    }

    private void initView() {
        setUpToolBar(getString(R.string.add_family_member), true);

        mProfileImage = findViewById(R.id.img_user_image);
        mFullName = findViewById(R.id.et_full_name);
        mDOB = findViewById(R.id.et_dob);
        mRelations = findViewById(R.id.et_relation);
        mGender = findViewById(R.id.et_gender);
        mFullNameError = findViewById(R.id.tv_full_name_error);
        mDobError = findViewById(R.id.tv_dob_error);
        mFullNameError = findViewById(R.id.tv_full_name_error);
        mRelationError = findViewById(R.id.tv_relationship_error);
        tvRelation = findViewById(R.id.tvRelation);
        tvGender = findViewById(R.id.tvGender);
        btnSaveMember = findViewById(R.id.btnSaveMember);

        setOnCapturedListener(this);

        findViewById(R.id.dob_layout).setOnClickListener(this);
        findViewById(R.id.et_dob).setOnClickListener(this);
        findViewById(R.id.ll_next).setOnClickListener(this);
        findViewById(R.id.llUploadProfile).setOnClickListener(this);

        tvRelation.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        btnSaveMember.setOnClickListener(this);

        ArrayAdapter relationAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                RELATIONS);
        relationAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mRelations.setAdapter(relationAdapter);

     ArrayAdapter   genderAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                GENDER);
        genderAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mGender.setAdapter(genderAdapter);
    }

    private void observeData() {
        mViewModel.getAddFamilyMemberData().observe(this, this::observeAddMemberUpData);
    }


    private void observeAddMemberUpData(Resource<BaseResponseModel> baseResponseModelResource) {
        switch (baseResponseModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                Toast.makeText(this, "Member successfully added", Toast.LENGTH_SHORT).show();
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
            case R.id.llUploadProfile:
                checkCameraPermission();
                break;
            case R.id.tvRelation:
                tvRelation.setVisibility(View.GONE);
                mRelations.setVisibility(View.VISIBLE);
                mRelations.performClick();
                break;

            case R.id.tvGender:
                tvGender.setVisibility(View.GONE);
                mGender.setVisibility(View.VISIBLE);
                mGender.performClick();
                break;
            case R.id.et_dob:
                openDatePicker(mDOB);
                break;

            case R.id.btnSaveMember:
               // setResetView(mFullNameError, mFullName);
               // setResetView(mDobError, mDOB);

               /* if (mUri == null){
                    Toast.makeText(this, "Please select member image", Toast.LENGTH_SHORT).show();
                }else*/ if (TextUtils.isEmpty(mFullName.getText().toString())){
                    setErrorView(mFullNameError, getString(R.string.valid_first_name), mFullName,
                            !TextUtils.isEmpty(mFullName.getText().toString()));
                }else if (mDOB.getText().toString().equals("")) {
                    setErrorView(mDobError, getString(R.string.valid_dob), mDOB,
                            !TextUtils.isEmpty(mDOB.getText().toString()));
                } else {
                    updateProfileData();
                   /* mViewModel.addFamilyMember(parseUserData().uid, mFullName.getText().toString(),
                            mDOB.getText().toString(), mRelations.toString(), mUri);*/
                }
                break;
        }
    }

    public void checkCameraPermission() {
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
    public void onCaptured(Uri uri) {
        mUri = uri;
        setImageBitmap(mProfileImage, uri);
    }

    @Override
    public View getLayoutRootView() {
        return findViewById(R.id.root_layout);
    }
    public File saveBitmapToFile(File file){
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
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
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

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public void updateProfileData(){
        showProgressDialog();

        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"),
                AppPreferences.getPreferenceInstance(this).getUserId());
        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"),mFullName.getText().toString());

        if (mUri!=null){
            File file=new File(mUri.getPath());
            File finalImage=saveBitmapToFile(file);
            RequestBody profilePic = RequestBody.create(MediaType.parse("multipart/form-data"),finalImage);
            imageProfile = MultipartBody.Part.createFormData("member_image",file.getName(), profilePic);
        }
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"),mDOB.getText().toString());
        RequestBody relationFamily = RequestBody.create(MediaType.parse("text/plain"),relation);
        RequestBody genderMember = RequestBody.create(MediaType.parse("text/plain"),gender);

        Call<AddMemberModel> call = RetrofitClient.getInstance().getMyApi().addMember(UserId,userName,
                imageProfile,dob,relationFamily,genderMember);
        call.enqueue(new Callback<AddMemberModel>() {
            @Override
            public void onResponse(Call<AddMemberModel> call, Response<AddMemberModel> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<AddMemberModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}
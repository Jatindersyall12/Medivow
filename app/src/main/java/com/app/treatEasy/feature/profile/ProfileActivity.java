package com.app.treatEasy.feature.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.edit_profile.EditProfileActivity;
import com.app.treatEasy.feature.family_member.FamilyMemberAdapter;
import com.app.treatEasy.feature.family_member.add_family_member.AddFamilyMemberActivity;
import com.app.treatEasy.feature.login_module.otp_verification.OtpVerificationActivity;
import com.app.treatEasy.feature.login_module.otp_verification.UserDetailModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserProfileModel;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.profile.GetProfileResponse;
import com.app.treatEasy.state.RetrofitClient;
import com.app.treatEasy.views.circularimageview.CircularImageView;
import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private ProfileViewModel mViewModel;
    private TextView mUserName,tvUserName, mDOB, mMobileNumber, mEmail, mGender,mProfession,mAddress,mPolicyName,
            tv_mobile,tv_city,tv_govId,tv_insurance;
    private CircleImageView mProfileImage;
    private ImageView imgGovId;
    private RecyclerView mRecycler;
    private TextView tvNoRecord;


    private Button btnAddMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
      //  mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProfileViewModel.class);
        initView();
        getProfileData(AppPreferences.getPreferenceInstance(this).getUserId());

        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(AddFamilyMemberActivity.class);
            }
        });

        //mViewModel.userProfileAction(parseUserData().uid);
      //  observeData();
    }

    private void initView() {

        setUpToolBarProfile("My Profile",true);

        ((TextView) findViewById(R.id.txt_next)).setText(getString(R.string.edit_profile));

        findViewById(R.id.ll_next).setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              switchActivityForResult(EditProfileActivity.class, UPDATE_PROFILE_REQUEST_CODE);
                                                              finish();
                                                          }
                                                      }
               );

       /* findViewById(R.id.toolbar_up_btn_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                switchActivity(HomeActivity.class);
            }
        });*/

        tvUserName= findViewById(R.id.tv_name);
        tv_city= findViewById(R.id.tv_city);
        mUserName = findViewById(R.id.tv_user_name);
        mDOB = findViewById(R.id.tv_dob);
        mMobileNumber = findViewById(R.id.tv_mobile_number);
        mEmail = findViewById(R.id.tv_email);
        mGender = findViewById(R.id.tv_gender);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_govId = findViewById(R.id.tv_govId);
        tv_insurance = findViewById(R.id.tv_insurance);
        mRecycler = findViewById(R.id.recycler_view);
        btnAddMore = findViewById(R.id.btnAddMore);
        tvNoRecord=findViewById(R.id.tvNoRecord);
        mProfileImage = findViewById(R.id.img_user_image);
        imgGovId = findViewById(R.id.imgGovId);
        tvNoRecord.setVisibility(View.GONE);

    }
    public void getProfileData(String userId) {
        showProgressDialog();
       /*GetProfileSend model=new GetProfileSend();
        model.setUserid(userId);*/
        Call<GetProfileResponse> call = RetrofitClient.getInstance().getMyApi().getProfile(userId);
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.body().getStatusCode()==200){
                    dismissProgressDialog();
                    mUserName.setText(response.body().getData().getPatientName());
                    tvUserName.setText(response.body().getData().getPatientName());
                    mMobileNumber.setText(response.body().getData().getMobileNo());
                    tv_mobile.setText(response.body().getData().getMobileNo());
                  //  Log.d("mobile no","mobile number"+response.body().getData().getMobileNo());
                    mDOB.setText(response.body().getData().getDob());
                    mEmail.setText(response.body().getData().getEmail());
                    if(response.body().getData().getGender().equals("1"))
                        mGender.setText("Male");
                    else if(response.body().getData().getGender().equals("2"))
                        mGender.setText("FeMale");
                    if(response.body().getData().getGender().equals("3"))
                        mGender.setText("Other");
                    //mGender.setText(response.body().getData().getGender());
                   // mAddress.setText(response.body().getData().getAddress());
                    tv_govId.setText(response.body().getData().getGovtId());
                    tv_insurance.setText(response.body().getData().getPolicyNumber());
                    //mProfession.setText(response.body().getData().getProfession());
                    tv_city.setText(response.body().getData().getCityName()+","+
                            response.body().getData().getStateName());
                     if (!response.body().getData().getProfileImage().equals("")){
                         AppPreferences.getPreferenceInstance(ProfileActivity.this).setUserImage(response.body().getData().getProfileImage());
                         setImage(response.body().getData().getProfileImage(),mProfileImage);
                     }
                    ProfileMemberAdapter mAdapter = new ProfileMemberAdapter(ProfileActivity.this,
                            response.body().getData().getFamilyMembers());
                    mRecycler.setLayoutManager(new LinearLayoutManager(ProfileActivity.this,RecyclerView.HORIZONTAL,false));
                    mRecycler.setAdapter(mAdapter);
                    setImage(response.body().getData().getGovtId(),imgGovId);
                }else {
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void observeData() {
        mViewModel.getUserProfileData().observe(this, this::observeProfileData);
    }

    private void observeProfileData(Resource<UserProfileModel> userProfileModelResource) {
        switch (userProfileModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                UserProfileModel model = userProfileModelResource.mData;

                saveUserDataInPreference(model.data);

                setUpData(model.data);
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

    private void setUpData(UserDetailModel data) {
        mUserName.setText(data.patientName);
        ((TextView) findViewById(R.id.tv_name)).setText(data.patientName);
        mDOB.setText(data.dob);
        mMobileNumber.setText(data.mobileNo);
        mEmail.setText(data.email);
        mGender.setText(data.gender);
        mProfession.setText(data.profession);

        mPolicyName.setText(data.policyNumber);
        mAddress.setText(data.address);

       // if (!data.profileImage.isEmpty())
            //setImage(data.profileImage, mProfileImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK){
            mViewModel.userProfileAction(parseUserData().uid);
        }
    }
}
package com.app.treatEasy.profile.doctorprofile;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.doctorsDetail.DoctorReviewActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfileActivity extends BaseActivity  {
   private LinearLayout llWriteReview;
   private ImageView img_user_image;
   private TextView tv_name,tv_speciality,tvHospitalName,tvRating,tvReview,tvAbout,
           tvDescription;
   private RecyclerView rvHospital;
   String doctorId,name,imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        setUpToolBarProfile("Doctor Profile",true);
        doctorId=getIntent().getStringExtra("doctor_id");

        init();

        llWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DoctorProfileActivity.this,DoctorReviewActivity.class);

                intent.putExtra("name",name);
                intent.putExtra("imageUrl",imageUrl);
                intent.putExtra("doctorId",doctorId);
                startActivity(intent);
               // switchActivity(DoctorReviewActivity.class);
            }
        });
    }

    public void init(){

        llWriteReview=findViewById(R.id.llWriteReview);
        img_user_image=findViewById(R.id.img_user_image);
        tv_name=findViewById(R.id.tv_name);
        tv_speciality=findViewById(R.id.tv_speciality);
        tvHospitalName=findViewById(R.id.tvHospitalName);
        tvRating=findViewById(R.id.tvRating);
        tvReview=findViewById(R.id.tvReview);
        tvAbout=findViewById(R.id.tvAbout);
        tvDescription=findViewById(R.id.tvDescription);
        rvHospital=findViewById(R.id.rvHospital);

        /*getProfile(AppPreferences.getPreferenceInstance(DoctorProfileActivity.this).getUserId(),
                doctorId);*/

    }

    public void getProfile(String userId,String doctorID){

        showProgressDialog();

        Call<DoctorProfileRes> call = RetrofitClient.getInstance().getMyApi().getDoctorProfile(userId,doctorID);
        call.enqueue(new Callback<DoctorProfileRes>(){
            @Override
            public void onResponse(Call<DoctorProfileRes> call, Response<DoctorProfileRes> response) {
                dismissProgressDialog();
                tvDescription.setText(response.body().getData().get(0).getDescription());
                tvAbout.setText("About "+response.body().getData().get(0).getDoctorName());
                tvReview.setText("("+response.body().getData().get(0).getReviews()+" Reviews"+")");
                tvRating.setText(""+response.body().getData().get(0).getRating());
                tv_speciality.setText(response.body().getData().get(0).getSpecialities());
                tv_name.setText(response.body().getData().get(0).getDoctorName());

                name=response.body().getData().get(0).getDoctorName();
                imageUrl=response.body().getData().get(0).getProfileImage();

                if (response.body().getStatusCode()==200){
                    BaseUtils.setImage(response.body().getData().get(0).getProfileImage(),img_user_image,
                            R.mipmap.medivow_logo);

                    rvHospital.setLayoutManager(new LinearLayoutManager(DoctorProfileActivity.this,RecyclerView.HORIZONTAL,false));
                    ProfileHospitalAdapter mAdapter = new ProfileHospitalAdapter(DoctorProfileActivity.this,
                            response.body().getData().get(0).getHospitals());
                    rvHospital.setAdapter(mAdapter);
                }
            }
            @Override
            public void onFailure(Call<DoctorProfileRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getProfile(AppPreferences.getPreferenceInstance(DoctorProfileActivity.this).getUserId(),
                doctorId);
    }
}
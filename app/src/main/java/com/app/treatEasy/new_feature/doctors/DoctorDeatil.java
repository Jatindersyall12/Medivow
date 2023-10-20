package com.app.treatEasy.new_feature.doctors;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDeatil extends BaseActivity {

    private ImageView img_doctor;
    private  TextView txt_doctor_name,txt_hospital_name,txt_doctor_speciality,tv_description;
    String id,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_deatil);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);

        initView();

    }

    private void initView() {

        txt_doctor_name=findViewById(R.id.txt_doctor_name);
        txt_hospital_name=findViewById(R.id.txt_hospital_name);
        txt_doctor_speciality=findViewById(R.id.txt_doctor_speciality);
        tv_description=findViewById(R.id.tv_description);
        img_doctor=findViewById(R.id.img_doctor);

                id = getIntent().getStringExtra("id");
                // mViewModel.getPackageDetailList(id);
                setUpToolBar("Doctor Detail", true);
                getDoctorDetail(AppPreferences.getPreferenceInstance(this).getUserId(),id);

    }

    public void getDoctorDetail(String userId,String catId){
        showProgressDialog();

        Log.d("userId","userId....."+userId);
        Log.d("catId","catId....."+catId);
       /* HospitalSendModel catIdSend=new HospitalSendModel();
        catIdSend.setPkgid(catId);*/
        Call<DoctorsDetail> call = RetrofitClient.getInstance().getMyApi().doctorDeatil(userId,catId);
        call.enqueue(new Callback<DoctorsDetail>() {
            @Override
            public void onResponse(Call<DoctorsDetail> call, Response<DoctorsDetail> response) {
                dismissProgressDialog();

                BaseUtils.setImage(response.body().getData().get(0).getProfileImage(),img_doctor, R.mipmap.medivow_logo);
                txt_doctor_name.setText(response.body().getData().get(0).getDoctorName());
                tv_description.setText(response.body().getData().get(0).getDescription());
                txt_hospital_name.setText(response.body().getData().get(0).getHospitals());
                txt_doctor_speciality.setText(response.body().getData().get(0).getSpecialities());

                Log.d("tagg","data....."+response.body().getData());

            }
            @Override
            public void onFailure(Call<DoctorsDetail> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }
}
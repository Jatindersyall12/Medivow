package com.app.treatEasy.appointmentlist;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.appointment_success.AppointmentSuccessModel;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDetailActivity extends BaseActivity {

    ImageView img_Hospital_image;
    TextView tvHospitalName;
    TextView tvAppoitmentNo, tvDoctorName, tvDate, tvTime, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        setUpToolBar(getString(R.string.appointment_detail), true);

        init();

        getAppointmentDetail(AppPreferences.getPreferenceInstance(this).getUserId(),"5");

    }

    public void init() {
        img_Hospital_image = findViewById(R.id.img_Hospital_image);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvAppoitmentNo = findViewById(R.id.tvAppointmentNo);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvAddress = findViewById(R.id.tvAddress);
    }

    public void getAppointmentDetail(String userId, String appointmentId) {

        showProgressDialog();

        Call<AppointmentSuccessModel> call = RetrofitClient.getInstance().getMyApi().getAppointmentDetail(userId, appointmentId);

        call.enqueue(new Callback<AppointmentSuccessModel>() {
            @Override
            public void onResponse(Call<AppointmentSuccessModel> call, Response<AppointmentSuccessModel> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {
                    setDataOnUI(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<AppointmentSuccessModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDataOnUI(AppointmentSuccessModel.Data data) {
        tvAppoitmentNo.setText("102222");
        tvDoctorName.setText(data.getDoctor_name());
        tvDate.setText(data.getAppointment_date());
        tvTime.setText("676767");
        tvAddress.setText("635866");
    }
}
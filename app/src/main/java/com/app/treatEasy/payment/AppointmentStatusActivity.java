package com.app.treatEasy.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.appointment_success.AppointmentSuccessModel;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppointmentStatusActivity extends BaseActivity {
    TextView tvAppoitmentNo, tvDoctorName, tvDate, tvTime, tvAddress,tvPatientName;
    String appointmentId,tokenNumber;
    Button btnBackToDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_status);
        appointmentId = getIntent().getStringExtra("appointmentId");
        tokenNumber= getIntent().getStringExtra("tokenNumber");
        Log.e("appoii",appointmentId);
        getAppointmentDetail(AppPreferences.getPreferenceInstance(this).getUserId(), appointmentId);
        setUpToolBar(getString(R.string.appointment_status), true);
        init();
        setOnClickListner();
    }

    private void setOnClickListner() {
        btnBackToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(HomeActivity.class);
            }
        });
    }

    private void setDataOnUI(AppointmentSuccessModel.Data data) {
        tvAppoitmentNo.setText(tokenNumber);
        tvDoctorName.setText(data.getDoctor_name());
        tvDate.setText(data.getAppointment_date());
        tvTime.setText(data.getApproximate_time());
        tvAddress.setText(data.getAddress());
        tvPatientName.setText(data.getMember_name());
    }

    public void init() {

        tvAppoitmentNo = findViewById(R.id.tvAppointmentNo);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvAddress = findViewById(R.id.tvAddress);
        btnBackToDashboard = findViewById(R.id.btnBackToDashBoard);
        tvPatientName=findViewById(R.id.tvPatientName);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
}
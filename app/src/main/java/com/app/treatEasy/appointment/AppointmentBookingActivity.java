package com.app.treatEasy.appointment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.doctorsDetail.PrimeDoctorAdapter;
import com.app.treatEasy.feature.family_member.MemberDetailResponse;
import com.app.treatEasy.feature.home_screen.SeeAllDoctorsActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.login_module.otp_verification.OtpVerificationActivity;
import com.app.treatEasy.feature.packages.BookingMemberAdapter;
import com.app.treatEasy.new_feature.login.LoginResponse;
import com.app.treatEasy.payment.AppointmentPaymentDetailActivity;
import com.app.treatEasy.payment.PackageFeeActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentBookingActivity extends BaseActivity {
    private Button btnConfirm;

    private RelativeLayout layDateAndTime;
    private Spinner spMember;
    List<ClientDetailRes.Datum> clientDoctorLIst;
    List<MemberDetailResponse.Datum> memberList;
    List<TokenModel.Datum> tokenList;
    String userId;
    private TextView tvDoctorName, tvSelectDoctor, tvDate;

    private EditText etxDesc;
    private String doctorId, memberId, doctorName,tokenNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appointment_booking);

        setUpToolBar(getString(R.string.appointment_booking), true);
        doctorId = getIntent().getStringExtra("doctorId");
        doctorName = getIntent().getStringExtra("doctorName");

        ids();
        tvDoctorName.setText(doctorName);
        clientDoctorLIst = new ArrayList<>();
        tokenList = new ArrayList<>();
        tvDoctorName.setText(doctorName);
        userId = AppPreferences.getPreferenceInstance(this).getUserId();

        getMemberData(AppPreferences.getPreferenceInstance(this).getUserId());

        spMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                memberId = memberList.get(i).getId();
                getTokenData(userId, memberId, doctorId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvSelectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectDoctor.setVisibility(View.GONE);
                spMember.setVisibility(View.VISIBLE);
                spMember.performClick();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenDialog();
            }
        });

        layDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(tvDate);
            }
        });

    }

    public void ids() {

        btnConfirm = findViewById(R.id.btnConfirm);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvSelectDoctor = findViewById(R.id.tvSelectDoctor);
        spMember = findViewById(R.id.spMember);
        layDateAndTime = findViewById(R.id.layDateAndTime);
        tvDate = findViewById(R.id.tvDate);
        etxDesc = findViewById(R.id.etxDesc);
    }

    public void tokenDialog() {

        final BottomSheetDialog dialog = new BottomSheetDialog(AppointmentBookingActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_appointment);

        RecyclerView rvToken = dialog.findViewById(R.id.rvToken);
        Button btnPartialPayment = dialog.findViewById(R.id.btnPartialPayment);

        rvToken.setLayoutManager(new GridLayoutManager(AppointmentBookingActivity.this, 5));
        TokenAdapter mAdapter = new TokenAdapter(AppointmentBookingActivity.this, tokenList);
        rvToken.setAdapter(mAdapter);

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        btnPartialPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("doctorId", doctorId);
                Log.e("doctorName", doctorName);
                Log.e("clientId", memberId);
                Log.e("date", tvDate.getText().toString());
                Log.e("desc", etxDesc.getText().toString());
                for (int i = 0; i < tokenList.size(); i++) {
                    if (tokenList.get(i).getSelectedStatus()) {
                        Log.e("tokennumber", tokenList.get(i).getTokenNo().toString());
                        tokenNumber=tokenList.get(i).getTokenNo().toString();
                        break;
                    }
                }

                Intent intent = new Intent(getApplicationContext(), AppointmentPaymentDetailActivity.class);
                intent.putExtra("doctorId", doctorId);
                intent.putExtra("doctorName", doctorName);
                intent.putExtra("clientId", memberId);
                intent.putExtra("date", tvDate.getText().toString());
                intent.putExtra("desc", etxDesc.getText().toString());
                intent.putExtra("tokenNumber", tokenNumber);
                startActivity(intent);
            }
        });

    }

    public void getMemberData(String userId) {

        Call<MemberDetailResponse> call = RetrofitClient.getInstance().getMyApi().getFamilyMember(userId);
        call.enqueue(new Callback<MemberDetailResponse>() {
            @Override
            public void onResponse(Call<MemberDetailResponse> call, Response<MemberDetailResponse> response) {
                if (response.body().getStatusCode() == 200) {


                    memberList = response.body().getData();
                    MemberDetailResponse.Datum self = new MemberDetailResponse.Datum();
                    self.setId(userId);
                    self.setMemberName("Self");
                    memberList.add(0, self);
                    if (memberList.size() > 0) {
                        BookingMemberAdapter adapter = new BookingMemberAdapter(AppointmentBookingActivity.this,
                                memberList);
                        spMember.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MemberDetailResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getTokenData(String userId, String clientId, String doctorId) {

        Log.d("userId", userId);
        Log.d("clientId", clientId);
        Log.d("doctorId", doctorId);

        userId = "25";
        clientId = "4";
        doctorId = "4";

        Call<TokenModel> call = RetrofitClient.getInstance().getMyApi().getToken(userId, doctorId, clientId);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.body().getStatusCode() == 200) {

                    // dismissProgressDialog();

                    tokenList = response.body().getData();

                } else {
                    //  dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                //  dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openDatePicker(TextView mTvDate) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    mTvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }, mYear, mMonth, mDay);
        //   datePickerDialog.getDatePicker().setMaxDate();
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        // Disable future dates (e.g., up to one year from today)
        c.add(Calendar.YEAR, 1);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();

    }
}
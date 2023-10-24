package com.app.treatEasy.payment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.appointment.GetAmountToPayRes;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentPaymentDetailActivity extends BaseActivity {
    TextView tvDoctorFee, tvSubTotal, tvDiscountedAmount, tvTokenNumber, tvConvenienceFees, tvWalletAmount, tvApproxTime;
    String clientId, hospitalName, amount, paymentStatus, doctorId, tokenNumber, tokenTime,desc,memberId;
    Button btnProcess, btnAddMore;
    LinearLayout layWallet;
    int totalFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail_appointment);

        //clientId = getIntent().getStringExtra("clientId");
        clientId = "4";

        doctorId = getIntent().getStringExtra("doctorId");
        hospitalName = getIntent().getStringExtra("hospital");
        paymentStatus = getIntent().getStringExtra("status");
        tokenNumber = getIntent().getStringExtra("tokenNumber");
        tokenTime = getIntent().getStringExtra("tokenTime");
        desc = getIntent().getStringExtra("desc");
        memberId = getIntent().getStringExtra("memberId");

        setUpToolBar(getString(R.string.payment_detail), true);

        init();

        paymentDoctorList(AppPreferences.getPreferenceInstance(this).getUserId(),
                doctorId, clientId);

        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("need to implement phonepay", "phone pay");
            }
        });
        tvApproxTime.setText(tokenTime);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePayment(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getUserId(),
                        clientId, doctorId, totalFee+"", "0", "6", "1");
            }
        });

    }

    public void init() {

        tvTokenNumber = findViewById(R.id.tvTokenNumber);
        tvDoctorFee = findViewById(R.id.tvDoctorFee);
        tvConvenienceFees = findViewById(R.id.tvConvenienceFees);
        tvDiscountedAmount = findViewById(R.id.tvDiscountedAmount);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        btnProcess = findViewById(R.id.btnProcess);
        tvWalletAmount = findViewById(R.id.tvWalletAmount);
        layWallet = findViewById(R.id.layWallet);
        btnAddMore = findViewById(R.id.btnAddMore);
        tvApproxTime = findViewById(R.id.tvApproxTime);

    }

    public void paymentDoctorList(String userId, String doctorID, String clientId) {

        showProgressDialog();

        Call<GetAmountToPayRes> call = RetrofitClient.getInstance().getMyApi().getAmountToPayAppointment(userId, clientId, doctorID, "1");

        call.enqueue(new Callback<GetAmountToPayRes>() {
            @Override
            public void onResponse(Call<GetAmountToPayRes> call, Response<GetAmountToPayRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {
                    setDataOnTheView(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GetAmountToPayRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDataOnTheView(GetAmountToPayRes.Data data) {
        tvTokenNumber.setText(tokenNumber);
        tvWalletAmount.setText(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getPayment());
        tvDoctorFee.setText(data.fee);
        tvConvenienceFees.setText(data.convenience_fee);
        tvDiscountedAmount.setText(data.discounted_fee);
        totalFee = ((Integer.parseInt(data.fee) + Integer.parseInt(data.convenience_fee)) - Integer.parseInt(data.discounted_fee));
        tvSubTotal.setText(totalFee + "");

        AppPreferences.getPreferenceInstance(this).setPayment("20000");


        if (Integer.parseInt(totalFee + "") > Integer.parseInt(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getPayment())) {
            layWallet.setVisibility(View.VISIBLE);
            btnProcess.setEnabled(false);
            btnProcess.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_medium)));

        } else {
            layWallet.setVisibility(View.GONE);
            btnProcess.setEnabled(true);
            btnProcess.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green_light)));
        }
    }

    public void makePayment(String userId, String clientId, String doctorID, String amount, String bookingId,
                            String paymentFor, String paymentStatus) {

        showProgressDialog();

        Call<MakePaymentRes> call = RetrofitClient.getInstance().getMyApi().makePayment(userId, clientId, doctorID,
                bookingId, paymentFor, paymentStatus, amount, tokenNumber, memberId, desc, tokenTime);
        call.enqueue(new Callback<MakePaymentRes>() {
            @Override
            public void onResponse(Call<MakePaymentRes> call, Response<MakePaymentRes> response) {
                dismissProgressDialog();

                if (response.body().getStatusCode() == 200) {

                    Intent intent = new Intent(getApplicationContext(), AppointmentStatusActivity.class);
                    intent.putExtra("appointmentId", response.body().getData().getAppointment_id());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<MakePaymentRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showSuccessDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(AppointmentPaymentDetailActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_payment_success);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        TextView tvSuccessMsg = dialog.findViewById(R.id.tvSuccessMsg);
        tvSuccessMsg.setText("Your transaction Rs " + amount + " is successful. Thank you for using TreatEasy!");
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentPaymentDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //tv_total_amount.setText(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getPayment());
    }
}
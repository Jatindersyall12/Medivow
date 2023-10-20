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
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.payment.surgery_package.PaymentPackageFeeRes;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentPaymentDetailActivity extends BaseActivity {
    TextView tvDoctorFee, tvSubTotal, tvDiscountedAmount, tvTokenNumber, tvConvenienceFees, tvWalletAmount;
    String clientId, bookingId = "0", hospitalName, amount, paymentStatus, doctorId, tokenNumber;
    Button btnProcess, btnAddMore;
    LinearLayout layWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail_appointment);

        clientId = getIntent().getStringExtra("clientId");
        doctorId = getIntent().getStringExtra("doctorId");
        hospitalName = getIntent().getStringExtra("hospital");
        paymentStatus = getIntent().getStringExtra("status");
        tokenNumber = getIntent().getStringExtra("tokenNumber");
        setUpToolBar(getString(R.string.payment_detail), true);

        init();

        paymentDoctorList(AppPreferences.getPreferenceInstance(this).getUserId(),
                clientId, bookingId, doctorId, paymentStatus);

        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("need to implement phonepay", "phone pay");
            }
        });

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppointmentStatusActivity.class);
                intent.putExtra("appointmentId", "2");
                startActivity(intent);
               /* int walletAmount = Integer.valueOf(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getPayment());
                int Amount = Integer.valueOf((int) Double.parseDouble(amount));
                if (Amount < walletAmount) {

                    makePayment(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getUserId(),
                            clientId, "0", amount, bookingId, "2", paymentStatus);

                } else {
                    showPaymentAlertDialog();
                }*/
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

    }

    public void paymentDoctorList(String userId, String clientId, String bookingId, String doctorID, String paymentStatus) {

        showProgressDialog();

        Call<PaymentPackageFeeRes> call = RetrofitClient.getInstance().getMyApi().getPaymentPackageFeeDetail(userId, clientId,
                bookingId, doctorID, "2", paymentStatus);

        call.enqueue(new Callback<PaymentPackageFeeRes>() {
            @Override
            public void onResponse(Call<PaymentPackageFeeRes> call, Response<PaymentPackageFeeRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {


                    //tv_name.setText(response.body().getData().getTitle());
                    /*if (paymentStatus.equals("2")){
                        tvDoctorFee.setText(response.body().getData().getAmount());
                        tvSubTotal.setText(response.body().getData().getAmount());
                       // tvPaidAmount.setText(""+response.body().getData().getPartialAmount());
                        amount=String.valueOf(response.body().getData().getPartialAmount());
                    }else{
                        tvDoctorFee.setText(response.body().getData().getAmount());
                        tvSubTotal.setText(response.body().getData().getAmount());
                       // tvPaidAmount.setText(""+response.body().getData().getAmount());
                        amount=String.valueOf(response.body().getData().getAmount());
                    }*/
                    //tv_total_amount.setText(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getPayment());
                    setDataOnTheView(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<PaymentPackageFeeRes> call, Throwable t) {
                dismissProgressDialog();
                setDataOnTheView(null);

                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDataOnTheView(PaymentPackageFeeRes.Data data) {
        tvTokenNumber.setText(tokenNumber);
        tvWalletAmount.setText(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getPayment());

        if (Integer.parseInt("2000".toString()) > Integer.parseInt(AppPreferences.getPreferenceInstance(AppointmentPaymentDetailActivity.this).getPayment())) {
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
                bookingId, paymentFor, paymentStatus, amount);
        call.enqueue(new Callback<MakePaymentRes>() {
            @Override
            public void onResponse(Call<MakePaymentRes> call, Response<MakePaymentRes> response) {
                dismissProgressDialog();

                if (response.body().getStatusCode() == 200) {
                    showSuccessDialog();
                }
            }

            @Override
            public void onFailure(Call<MakePaymentRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showPaymentAlertDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(AppointmentPaymentDetailActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_amount);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentPaymentDetailActivity.this, WalletActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
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
package com.app.treatEasy.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.add_money.AddMoneyToWalletActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorFeeActivity extends BaseActivity {
    ImageView img_user_image;
    TextView tv_name,tvSpecilaity,tvHospitalName,tvRating,tvDoctorFee,tvDiscountAmount,tvSubTotal,tv_total_amount;
    String clientId,doctorId,hospitalName,amount;
    Button btnProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_fee);

        clientId=getIntent().getStringExtra("clientId");
        doctorId=getIntent().getStringExtra("doctorId");
        hospitalName=getIntent().getStringExtra("hospital");
        setUpToolBarProfile("Doctor Fee",true);

        init();

        paymentDoctorList(AppPreferences.getPreferenceInstance(this).getUserId(),
                clientId,doctorId);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int walletAmount=Integer.valueOf(AppPreferences.getPreferenceInstance(DoctorFeeActivity.this).getPayment());
                int Amount=Integer.valueOf(amount);
                if (Amount<walletAmount){
                    makePayment(AppPreferences.getPreferenceInstance(DoctorFeeActivity.this).getUserId(),
                            clientId, doctorId,amount,"0","1");
                }else {
                   showPaymentAlertDialog();
                }

            }
        });

    }

    public void paymentDoctorList(String userId,String clientId,String doctorId){

        Log.d("userId","userId"+userId);
        Log.d("clientId","clientId"+clientId);

        showProgressDialog();

        Call<DoctorFeeDetailRes> call = RetrofitClient.getInstance().getMyApi().getPaymentDoctorFeeDetail(userId,clientId,
                doctorId,"1");
        call.enqueue(new Callback<DoctorFeeDetailRes>(){
            @Override
            public void onResponse(Call<DoctorFeeDetailRes> call, Response<DoctorFeeDetailRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {

                    tv_name.setText(response.body().getData().getDoctorName());
                    tvSpecilaity.setText(""+response.body().getData().getSpecialities());
                    tvHospitalName.setText(hospitalName);
                    tvDoctorFee.setText(response.body().getData().getFee());
                    tvDiscountAmount.setText(response.body().getData().getDiscountedFee());
                    tvSubTotal.setText(response.body().getData().getDiscountedFee());
                    amount=response.body().getData().getDiscountedFee();
                    tv_total_amount.setText(AppPreferences.getPreferenceInstance(DoctorFeeActivity.this).getPayment());

                    tvRating.setText(""+response.body().getData().getRating()+" ("+
                            ""+response.body().getData().getReviews()+" Reviews)");
                    Glide.with(DoctorFeeActivity.this)
                            .load(response.body().getData().getProfileImage())
                            .error(R.mipmap.medivow_logo)
                            .into(img_user_image);
                }
            }
            @Override
            public void onFailure(Call<DoctorFeeDetailRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void init(){

        img_user_image=findViewById(R.id.img_user_image);
        tv_name=findViewById(R.id.tv_name);
        tvSpecilaity=findViewById(R.id.tvSpecilaity);
        tvHospitalName=findViewById(R.id.tvHospitalName);
        tvRating=findViewById(R.id.tvRating);
        tvDoctorFee=findViewById(R.id.tvDoctorFee);
        tvDiscountAmount=findViewById(R.id.tvDiscountAmount);
        tvSubTotal=findViewById(R.id.tvSubTotal);
        tv_total_amount=findViewById(R.id.tv_total_amount);

        btnProcess=findViewById(R.id.btnProcess);

    }

    public void makePayment(String userId,String clientId,String doctorID,String amount,String bookingId,
                            String paymentFor){

        showProgressDialog();

        Call<MakePaymentRes> call = RetrofitClient.getInstance().getMyApi().makePayment(userId,clientId,doctorID,
                bookingId,paymentFor,"1",amount);
        call.enqueue(new Callback<MakePaymentRes>(){
            @Override
            public void onResponse(Call<MakePaymentRes> call, Response<MakePaymentRes> response) {
                dismissProgressDialog();

                if (response.body().getStatusCode()==200){
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
    public void showPaymentAlertDialog(){
        final BottomSheetDialog dialog = new BottomSheetDialog(DoctorFeeActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_amount);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorFeeActivity.this, WalletActivity.class);
                 startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showSuccessDialog(){
        final BottomSheetDialog dialog = new BottomSheetDialog(DoctorFeeActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_payment_success);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        TextView tvSuccessMsg =  dialog.findViewById(R.id.tvSuccessMsg);

        tvSuccessMsg.setText("Your transaction Rs "+amount+" is successful. Thank you for using TreatEasy!");
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorFeeActivity.this, HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_total_amount.setText(AppPreferences.getPreferenceInstance(DoctorFeeActivity.this).getPayment());
    }
}
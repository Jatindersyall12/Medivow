package com.app.treatEasy.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.payment.surgery_package.PaymentPackageFeeRes;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageFeeActivity extends BaseActivity {
    ImageView img_user_image;
    TextView tv_name,tvDoctorFee,tvDiscountAmount,tvSubTotal,tv_total_amount,tvPaidAmount;
    String clientId,bookingId,hospitalName,amount,paymentStatus,doctorId;
    Button btnProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_fee);

        clientId=getIntent().getStringExtra("clientId");
        bookingId=getIntent().getStringExtra("bookingId");
        doctorId=getIntent().getStringExtra("doctorId");
        hospitalName=getIntent().getStringExtra("hospital");
        paymentStatus=getIntent().getStringExtra("status");
        setUpToolBarProfile("Surgery Package Fee",true);
        init();

        paymentDoctorList(AppPreferences.getPreferenceInstance(this).getUserId(),
                clientId,bookingId,doctorId,paymentStatus);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int walletAmount=Integer.valueOf(AppPreferences.getPreferenceInstance(PackageFeeActivity.this).getPayment());
                int Amount=Integer.valueOf((int) Double.parseDouble(amount));
                if (Amount<walletAmount){

                    makePayment(AppPreferences.getPreferenceInstance(PackageFeeActivity.this).getUserId(),
                            clientId, "0",amount,bookingId,"2",paymentStatus);

                     }else {
                    showPaymentAlertDialog();
                }
            }
        });

    }

    public void init(){

        img_user_image=findViewById(R.id.img_user_image);
        tv_name=findViewById(R.id.tv_name);
        tvDoctorFee=findViewById(R.id.tvDoctorFee);
        tvPaidAmount=findViewById(R.id.tvPaidAmount);
        tvSubTotal=findViewById(R.id.tvSubTotal);
        tv_total_amount=findViewById(R.id.tv_total_amount);

        btnProcess=findViewById(R.id.btnProcess);

    }

    public void paymentDoctorList(String userId,String clientId,String bookingId,String doctorID,String paymentStatus){

        Log.d("userId","userId"+userId);
        Log.d("clientId","clientId"+clientId);
        Log.d("clientId","bookingId"+bookingId);
        Log.d("clientId","doctorId"+doctorID);

        showProgressDialog();

        Call<PaymentPackageFeeRes> call = RetrofitClient.getInstance().getMyApi().getPaymentPackageFeeDetail(userId,clientId,
                bookingId,doctorID,"2",paymentStatus);

        call.enqueue(new Callback<PaymentPackageFeeRes>(){
            @Override
            public void onResponse(Call<PaymentPackageFeeRes> call, Response<PaymentPackageFeeRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {

                    tv_name.setText(response.body().getData().getTitle());
                    if (paymentStatus.equals("2")){
                        tvDoctorFee.setText(response.body().getData().getAmount());
                        tvSubTotal.setText(response.body().getData().getAmount());
                        tvPaidAmount.setText(""+response.body().getData().getPartialAmount());
                        amount=String.valueOf(response.body().getData().getPartialAmount());
                    }else{
                        tvDoctorFee.setText(response.body().getData().getAmount());
                        tvSubTotal.setText(response.body().getData().getAmount());
                        tvPaidAmount.setText(""+response.body().getData().getAmount());
                        amount=String.valueOf(response.body().getData().getAmount());
                    }
                    tv_total_amount.setText(AppPreferences.getPreferenceInstance(PackageFeeActivity.this).getPayment());
                }
            }
            @Override
            public void onFailure(Call<PaymentPackageFeeRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void makePayment(String userId,String clientId,String doctorID,String amount,String bookingId,
                            String paymentFor,String paymentStatus){

        showProgressDialog();

        Call<MakePaymentRes> call = RetrofitClient.getInstance().getMyApi().makePayment(userId,clientId,doctorID,
                bookingId,paymentFor,paymentStatus,amount,"","","","");
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
        final BottomSheetDialog dialog = new BottomSheetDialog(PackageFeeActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_amount);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PackageFeeActivity.this, WalletActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showSuccessDialog(){
        final BottomSheetDialog dialog = new BottomSheetDialog(PackageFeeActivity.this, R.style.CustomDialogTheme);
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
                Intent intent = new Intent(PackageFeeActivity.this, HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_total_amount.setText(AppPreferences.getPreferenceInstance(PackageFeeActivity.this).getPayment());
    }
}
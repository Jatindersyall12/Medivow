package com.app.treatEasy.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabFeeActivity extends BaseActivity {
   private EditText et_amount;
   TextView tv_total_amount,tv_name;
   String clientId,hospitalName,amount;
   Button btnProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_fee);
        clientId=getIntent().getStringExtra("clientId");
        hospitalName=getIntent().getStringExtra("hospitalName");
        setUpToolBarProfile("Lab Test Fee",true);

        init();

        tv_name.setText(hospitalName);
        tv_total_amount.setText(AppPreferences.getPreferenceInstance(LabFeeActivity.this).getPayment());

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_amount.getText().toString().equals("")){
                    int walletAmount=Integer.valueOf(AppPreferences.getPreferenceInstance(LabFeeActivity.this).getPayment());
                    int Amount=Integer.valueOf(et_amount.getText().toString());
                    if (Amount<walletAmount){

                        amount=et_amount.getText().toString();
                        makePayment(AppPreferences.getPreferenceInstance(LabFeeActivity.this).getUserId(),
                                clientId, "0",et_amount.getText().toString(),"0","3");
                    }else {
                        showPaymentAlertDialog();
                    }

                }else {
                    et_amount.setError("please enter amount");
                }
            }
        });
    }
    public void init(){

        et_amount=findViewById(R.id.et_amount);
        tv_total_amount=findViewById(R.id.tv_total_amount);
        btnProcess=findViewById(R.id.btnProcess);
        tv_name=findViewById(R.id.tv_name);

    }

    public void makePayment(String userId,String clientId,String doctorID,String amount,String bookingId,
                            String paymentFor){

        showProgressDialog();

        Call<MakePaymentRes> call = RetrofitClient.getInstance().getMyApi().makePayment(userId,clientId,doctorID,
                bookingId,paymentFor,amount,"1","","","","");
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
        final BottomSheetDialog dialog = new BottomSheetDialog(LabFeeActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_amount);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabFeeActivity.this, WalletActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showSuccessDialog(){
        final BottomSheetDialog dialog = new BottomSheetDialog(LabFeeActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_payment_success);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        TextView tvSuccessMsg = (TextView) dialog.findViewById(R.id.tvSuccessMsg);
        tvSuccessMsg.setText("Your transaction Rs "+amount+" is successful. Thank you for using TreatEasy!");
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabFeeActivity.this, HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_total_amount.setText(AppPreferences.getPreferenceInstance(LabFeeActivity.this).getPayment());
    }
}
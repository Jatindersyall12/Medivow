package com.app.treatEasy.feature.wallet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.listeners.ItemPaymentListenr;
import com.app.treatEasy.payment.LabFeeActivity;
import com.app.treatEasy.payment.MakePaymentRes;
import com.app.treatEasy.payment.PaymentDetailRes;
import com.app.treatEasy.payment.PaymentDoctorActivity;
import com.app.treatEasy.payment.PaymentFharmacyActivity;
import com.app.treatEasy.payment.surgery_package.PaymentPackageActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailActivity extends BaseActivity implements ItemPaymentListenr {
    String clientId;
    ImageView imgClientImage,imgDoctorFee,imgLabTest,imgHospital,imgSurgery,imgPharmacy;
    TextView tvClientName;
    RelativeLayout rlDoctorFee,rlLabTest,rlSurgeryPackage,rlPharmacy,rlHospitalBills;
    String hospitalName,typeId,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        clientId=getIntent().getStringExtra("clientId");
        setUpToolBarProfile("Client Detail", true);

        init();

        paymentDetail(AppPreferences.getPreferenceInstance(this).getUserId(),clientId);

        rlLabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentDetailActivity.this, LabFeeActivity.class);
                intent.putExtra("clientId",clientId);
                intent.putExtra("hospitalName",hospitalName);
                startActivity(intent);
            }
        });

        rlPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentDetailActivity.this, PaymentFharmacyActivity.class);
                intent.putExtra("clientId",clientId);
                intent.putExtra("hospitalName",hospitalName);
                startActivity(intent);
            }
        });

        rlSurgeryPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PaymentDetailActivity.this, PaymentPackageActivity.class);
                intent.putExtra("typeid",typeId);
                intent.putExtra("hospitalName",hospitalName);
                intent.putExtra("image",image);
                startActivity(intent);

            }
        });

        rlDoctorFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PaymentDetailActivity.this, PaymentDoctorActivity.class);
                intent.putExtra("typeid",typeId);
                intent.putExtra("hospitalName",hospitalName);
                intent.putExtra("image",image);
                startActivity(intent);

            }
        });
    }

    public void paymentDetail(String userId,String clientId){

        Log.d("userId","userId"+userId);
        Log.d("clientId","clientId"+clientId);

        showProgressDialog();

        Call<PaymentDetailRes> call = RetrofitClient.getInstance().getMyApi().paytmDetail(userId,clientId);
        call.enqueue(new Callback<PaymentDetailRes>(){
            @Override
            public void onResponse(Call<PaymentDetailRes> call, Response<PaymentDetailRes> response) {
                dismissProgressDialog();
              if (response.body().getStatusCode()==200){

                  hospitalName=response.body().getData().getClientName();
                  typeId=response.body().getData().getId();
                  image=response.body().getData().getImage();
                  tvClientName.setText(response.body().getData().getClientName());

                  Log.d("type","type"+clientId);

                  Glide.with(PaymentDetailActivity.this)
                          .load(response.body().getData().getImage())
                          .error(R.mipmap.medivow_logo)
                          .into(imgClientImage);

                  if (response.body().getData().getClientType().equals("2")){

                      rlDoctorFee.setVisibility(View.VISIBLE);
                      rlLabTest.setVisibility(View.VISIBLE);
                      rlSurgeryPackage.setVisibility(View.VISIBLE);
                      rlPharmacy.setVisibility(View.VISIBLE);
                      rlHospitalBills.setVisibility(View.VISIBLE);

                  }else if (response.body().getData().getClientType().equals("1")) {

                      rlDoctorFee.setVisibility(View.VISIBLE);
                      rlLabTest.setVisibility(View.VISIBLE);
                      rlHospitalBills.setVisibility(View.GONE);
                      rlSurgeryPackage.setVisibility(View.GONE);
                      rlPharmacy.setVisibility(View.VISIBLE);

                  }else if (response.body().getData().getClientType().equals("3")) {

                      rlDoctorFee.setVisibility(View.GONE);
                      rlLabTest.setVisibility(View.VISIBLE);
                      rlHospitalBills.setVisibility(View.GONE);
                      rlSurgeryPackage.setVisibility(View.GONE);
                      rlPharmacy.setVisibility(View.GONE);

                  }else if (response.body().getData().getClientType().equals("4")) {

                      rlDoctorFee.setVisibility(View.GONE);
                      rlLabTest.setVisibility(View.GONE);
                      rlHospitalBills.setVisibility(View.GONE);
                      rlSurgeryPackage.setVisibility(View.GONE);
                      rlPharmacy.setVisibility(View.VISIBLE);

                  }
              }
            }
            @Override
            public void onFailure(Call<PaymentDetailRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void makePayment(String userId,String clientId,String doctorID,String amount,String bookingId,
                            String paymentFor){
        showProgressDialog();

        Call<MakePaymentRes> call = RetrofitClient.getInstance().getMyApi().makePayment(userId,clientId,doctorID,
                bookingId,paymentFor,amount,"1");
        call.enqueue(new Callback<MakePaymentRes>(){
            @Override
            public void onResponse(Call<MakePaymentRes> call, Response<MakePaymentRes> response) {
                dismissProgressDialog();

            }
            @Override
            public void onFailure(Call<MakePaymentRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

   public void init(){

       imgClientImage=findViewById(R.id.imgClientImage);
       tvClientName=findViewById(R.id.tvClientName);
       imgDoctorFee=findViewById(R.id.imgDoctorFee);
       imgLabTest=findViewById(R.id.imgLabTest);
       imgHospital=findViewById(R.id.imgHospital);
       imgSurgery=findViewById(R.id.imgSurgery);
       imgPharmacy=findViewById(R.id.imgPharmacy);
       rlDoctorFee=findViewById(R.id.rlDoctorFee);
       rlLabTest=findViewById(R.id.rlLabTest);
       rlSurgeryPackage=findViewById(R.id.rlSurgeryPackage);
       rlPharmacy=findViewById(R.id.rlPharmacy);
       rlHospitalBills=findViewById(R.id.rlHospitalBills);

   }

    @Override
    public void OnItemClick(View view, int position, String type) {

    }
}
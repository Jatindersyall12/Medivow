package com.app.treatEasy.payment.surgery_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.payment.DoctorFeeActivity;
import com.app.treatEasy.payment.PackageFeeActivity;
import com.app.treatEasy.payment.PaymentDoctorActivity;
import com.app.treatEasy.payment.PaymentDoctorListAdapter;
import com.app.treatEasy.payment.PaymentDoctorRes;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentPackageActivity extends BaseActivity implements ItemClickListener {

    ImageView img_Hospital_image;
    TextView tvHospitalName;
    List<paymentPackageRes.Datum> doctorList;
    private RecyclerView mRecycler;
    String id,hospitalName,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_package);
        doctorList=new ArrayList<>();
        id=getIntent().getStringExtra("typeid");
        hospitalName=getIntent().getStringExtra("hospitalName");
        image=getIntent().getStringExtra("image");

        setUpToolBarProfile(hospitalName,true);

        init();

        Glide.with(PaymentPackageActivity.this)
                .load(image)
                .error(R.mipmap.medivow_logo)
                .into(img_Hospital_image);

        tvHospitalName.setText(hospitalName);

        paymentDoctorList(AppPreferences.getPreferenceInstance(this).getUserId(),id);

    }

    public void init(){
        img_Hospital_image=findViewById(R.id.img_Hospital_image);
        tvHospitalName=findViewById(R.id.tvHospitalName);
        mRecycler=findViewById(R.id.recycler_view);
    }

    public void paymentDoctorList(String userId,String clientId){

        Log.d("userId","userId"+userId);
        Log.d("clientId","clientId"+clientId);

        showProgressDialog();

        Call<paymentPackageRes> call = RetrofitClient.getInstance().getMyApi().paymentPackageList(userId,clientId,"2");
        call.enqueue(new Callback<paymentPackageRes>(){
            @Override
            public void onResponse(Call<paymentPackageRes> call, Response<paymentPackageRes> response) {
                dismissProgressDialog();

                if (response.body().getStatusCode() == 200) {

                    doctorList=response.body().getData();
                    mRecycler.setLayoutManager(new LinearLayoutManager(PaymentPackageActivity.this));
                    PaymentPackageListAdapter adapter=new PaymentPackageListAdapter(PaymentPackageActivity.this,
                            doctorList,PaymentPackageActivity.this);
                    mRecycler.setAdapter(adapter);

                }
            }
            @Override
            public void onFailure(Call<paymentPackageRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnItemClick(View view, int position) {

        Intent intent=new Intent(PaymentPackageActivity.this, PackageFeeActivity.class);
        intent.putExtra("clientId",id);
        intent.putExtra("bookingId",doctorList.get(position).getBookingId());
        intent.putExtra("hospital",hospitalName);
        intent.putExtra("status","1");
        startActivity(intent);

    }
}
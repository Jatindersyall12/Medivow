package com.app.treatEasy.payment;

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
import com.app.treatEasy.feature.wallet.PaymentDetailActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDoctorActivity extends BaseActivity implements ItemClickListener {
    ImageView img_Hospital_image;
    TextView tvHospitalName;
    List<PaymentDoctorRes.Datum>doctorList;
    private RecyclerView mRecycler;
    String id,hospitalName,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_doctor);

        doctorList=new ArrayList<>();
        id=getIntent().getStringExtra("typeid");
        hospitalName=getIntent().getStringExtra("hospitalName");
        image=getIntent().getStringExtra("image");

       setUpToolBarProfile(hospitalName,true);

        init();

        Glide.with(PaymentDoctorActivity.this)
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

        Call<PaymentDoctorRes> call = RetrofitClient.getInstance().getMyApi().paymentDoctorList(userId,clientId,"1");
        call.enqueue(new Callback<PaymentDoctorRes>(){
            @Override
            public void onResponse(Call<PaymentDoctorRes> call, Response<PaymentDoctorRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {

                    doctorList=response.body().getData();
                    mRecycler.setLayoutManager(new LinearLayoutManager(PaymentDoctorActivity.this));
                    PaymentDoctorListAdapter adapter=new PaymentDoctorListAdapter(PaymentDoctorActivity.this,
                            doctorList,PaymentDoctorActivity.this);
                   mRecycler.setAdapter(adapter);

                }
            }
            @Override
            public void onFailure(Call<PaymentDoctorRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnItemClick(View view, int position) {

        Intent intent=new Intent(PaymentDoctorActivity.this,DoctorFeeActivity.class);
        intent.putExtra("clientId",id);
        intent.putExtra("doctorId",doctorList.get(position).getDoctorId());
        intent.putExtra("hospital",hospitalName);
        startActivity(intent);

    }
}
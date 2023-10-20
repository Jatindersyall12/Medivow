package com.app.treatEasy.payment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.add_money.AddMoneyToWalletActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentHistoryActivity extends BaseActivity  {
    private RecyclerView mRecycler;
    private TextView tvAddAmount,tvAmount;
    List<PaymentHistoryRes.Datum> paymentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);

        setContentView(R.layout.activity_payment_history);

        paymentList=new ArrayList<>();

        initView();

        tvAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(WalletActivity.class);
            }
        });
    }

    private void initView(){

        setUpToolBar("Payment History", true);
        mRecycler=findViewById(R.id.recycler_view);
        tvAddAmount=findViewById(R.id.tvAddAmount);
        tvAmount=findViewById(R.id.tvAmount);
        tvAmount.setText(AppPreferences.getPreferenceInstance(PaymentHistoryActivity.this).getPayment());

        getPayment(AppPreferences.getPreferenceInstance(this).getUserId());

    }

    public void getPayment(String userId) {
        showProgressDialog();

        Call<PaymentHistoryRes> call = RetrofitClient.getInstance().getMyApi().getPaymentHistory(userId);
        call.enqueue(new Callback<PaymentHistoryRes>(){
            @Override
            public void onResponse(Call<PaymentHistoryRes> call, Response<PaymentHistoryRes> response) {
                dismissProgressDialog();

                paymentList=response.body().getData();
                mRecycler.setLayoutManager(new LinearLayoutManager(PaymentHistoryActivity.this));
                PaymentHistoryAdapter mAdapter = new PaymentHistoryAdapter(PaymentHistoryActivity.this,paymentList);
                mRecycler.setAdapter(mAdapter);

            }
            @Override
            public void onFailure(Call<PaymentHistoryRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}
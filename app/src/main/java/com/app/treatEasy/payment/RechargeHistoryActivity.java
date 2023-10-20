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
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargeHistoryActivity extends BaseActivity {
    private RecyclerView mRecycler;
    List<RechargeHistoryRes.Datum> rechargeList;
    TextView tvNoRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        setContentView(R.layout.activity_recharge_history);
        rechargeList=new ArrayList<>();

        initView();
    }
    private void initView(){

        setUpToolBar("Recharge History", true);
        mRecycler=findViewById(R.id.recycler_view);
        tvNoRecord=findViewById(R.id.tvNoRecord);
        getRecharge(AppPreferences.getPreferenceInstance(this).getUserId());
    }
    public void getRecharge(String userId){
        showProgressDialog();

        Call<RechargeHistoryRes> call = RetrofitClient.getInstance().getMyApi().getRechargeHistory(userId);
        call.enqueue(new Callback<RechargeHistoryRes>(){
            @Override
            public void onResponse(Call<RechargeHistoryRes> call, Response<RechargeHistoryRes> response) {
                dismissProgressDialog();
                rechargeList=response.body().getData();
                if(rechargeList.size()>0) {
                    mRecycler.setLayoutManager(new LinearLayoutManager(RechargeHistoryActivity.this));
                    RechargeHistoryAdapter mAdapter = new RechargeHistoryAdapter(RechargeHistoryActivity.this, rechargeList);
                    mRecycler.setAdapter(mAdapter);
                }
                else {
                    tvNoRecord.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFailure(Call<RechargeHistoryRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}
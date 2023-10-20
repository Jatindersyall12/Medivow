package com.app.treatEasy.notification;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity implements ItemClickListener {
    private RecyclerView mRecycler;
    NotificationAdapter mAdapter;
    List<NotificationRes.Datum> notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        notificationList=new ArrayList<>();
        initView();
    }
    private void initView(){

        setUpToolBar("Notification", true);
        mRecycler=findViewById(R.id.recycler_view);

        getNotification(AppPreferences.getPreferenceInstance(this).getUserId());
    }
    @Override
    public void OnItemClick(View view, int position) {

    }
    public void getNotification(String userId){
        showProgressDialog();

        Call<NotificationRes> call = RetrofitClient.getInstance().getMyApi().getNotification(userId);
        call.enqueue(new Callback<NotificationRes>(){
            @Override
            public void onResponse(Call<NotificationRes> call, Response<NotificationRes> response) {
                dismissProgressDialog();

                notificationList=response.body().getData();
                if (notificationList!=null&&notificationList.size()>0){
                    AdapterSet(notificationList);
                }
            }
            @Override
            public void onFailure(Call<NotificationRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AdapterSet(List<NotificationRes.Datum> list){
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationAdapter(this,list);
        mRecycler.setAdapter(mAdapter);
    }
}
package com.app.treatEasy.appservice;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailActivity extends BaseActivity {
    private ImageView imgDetail;
    private TextView txt_package_name,txt_package_description;
    RecyclerView mRecycler;
    String id,title;
    List<ServiceDetailRes.PriceDetail>serviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        serviceList=new ArrayList<>();
        id=getIntent().getStringExtra("id");
        title=getIntent().getStringExtra("title");

        initView();

    }
    private void initView() {
        imgDetail=findViewById(R.id.imgDetail);
        txt_package_name=findViewById(R.id.txt_package_name);
        txt_package_description=findViewById(R.id.txt_package_description);

        mRecycler=findViewById(R.id.recycler_view);

        setUpToolBar(title, true);
        getDoctorListList(AppPreferences.getPreferenceInstance(this).getUserId(),id);
    }

    public void getDoctorListList(String userId,String catId) {

        showProgressDialog();

        Call<ServiceDetailRes> call = RetrofitClient.getInstance().getMyApi().getServiceDetail(userId,catId);
        call.enqueue(new Callback<ServiceDetailRes>(){
            @Override
            public void onResponse(Call<ServiceDetailRes> call, Response<ServiceDetailRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                      BaseUtils.setImage(response.body().getData().get(0).getImage(),imgDetail, R.mipmap.medivow_logo);
                    txt_package_name.setText(response.body().getData().get(0).getDisplayName());
                    txt_package_description.setText(response.body().getData().get(0).getDescription());

                    serviceList=response.body().getData().get(0).getPriceDetail();

                    if (serviceList!=null&&serviceList.size()>0){
                        mRecycler.setLayoutManager(new LinearLayoutManager(ServiceDetailActivity.this));
                        ServiceDetailAdapter mAdapter = new ServiceDetailAdapter(ServiceDetailActivity.this,serviceList);
                        mRecycler.setAdapter(mAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ServiceDetailRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}
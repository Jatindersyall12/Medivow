package com.app.treatEasy.feature.packages;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalListActivity extends BaseActivity implements ItemClickListener{
    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private RecyclerView mRecycler;
    private HospitalAdapter mAdapter;
    private List<PackageDeatil.PriceDetail> mHospitalListList = new ArrayList<>();
    String id,title;
    ImageView imgHospital;
    TextView txt_Hospital_name,tv_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        // mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DoctorsByCategoryViewModel.class);
        initView();
    }

    private void initView(){
        mRecycler = findViewById(R.id.recycler_view);
        imgHospital = findViewById(R.id.imgHospital);
        txt_Hospital_name = findViewById(R.id.txt_Hospital_name);
        tv_description = findViewById(R.id.tv_description);

        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);
        if (bundle != null) {
            id = bundle.getString(PACKAGE_ID);
            title = bundle.getString(PACKAGE_NAME);
            // mViewModel.getPackageDetailList(id);
            setUpToolBar(title, true);
            getDoctorListList(AppPreferences.getPreferenceInstance(this).getUserId(),id);
        }
    }
    @Override
    public void OnItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(PACKAGE_ID,id);
        bundle.putString(PACKAGE_NAME, title);
      //  bundle.putString(PACKAGE_Cat_ID, mHospitalListList.get(position).getId());
      //   switchActivity(DoctorsByCategoryActivity.class, bundle);        //// mohit
       // switchActivity(DoctorsByCategoryActivity.class, bundle);
    }

    public void getDoctorListList(String userId,String catId){
        showProgressDialog();

        Log.d("userId","userId....."+userId);
        Log.d("catId","catId....."+catId);
       /*HospitalSendModel catIdSend=new HospitalSendModel();
        catIdSend.setPkgid(catId);*/
        Call<PackageDeatil> call = RetrofitClient.getInstance().getMyApi().getPackageDetail(userId,catId);
        call.enqueue(new Callback<PackageDeatil>(){
            @Override
            public void onResponse(Call<PackageDeatil> call, Response<PackageDeatil> response) {
                dismissProgressDialog();

                BaseUtils.setImage(response.body().getData().getImage(),imgHospital, R.mipmap.medivow_logo);
                txt_Hospital_name.setText(response.body().getData().getTitle());
                tv_description.setText(response.body().getData().getDescription());
                mHospitalListList=response.body().getData().getPriceDetail();
                Log.d("tagg","data....."+response.body().getData());
                if (mHospitalListList!=null&&mHospitalListList.size()>0){
                    mHospitalListList=response.body().getData().getPriceDetail();
                    AdapterSet(mHospitalListList,response.body().getData().getTitle());
                }

            }
            @Override
            public void onFailure(Call<PackageDeatil> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AdapterSet(List<PackageDeatil.PriceDetail> list,String title){
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HospitalAdapter(this, this,list,title);
        mRecycler.setAdapter(mAdapter);
    }
}
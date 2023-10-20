package com.app.treatEasy;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.packages.HospitalListActivity;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryAdapter;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryModel;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryViewModel;
import com.app.treatEasy.feature.packages.package_detail.PackageCatDetail;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurgeryPackegeDetailActivity extends BaseActivity implements ItemClickListener {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private DoctorsByCategoryViewModel mViewModel;
    private DoctorsByCategoryAdapter mAdapter;
    private RecyclerView mRecycler;
    private ArrayList<DoctorsByCategoryModel> mModelList = new ArrayList<>();
    private List<PackageCatDetail.Datum> mCatList = new ArrayList<>();
    String id,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery_packege_detail);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        // mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DoctorsByCategoryViewModel.class);
        initView();
        // observeData();
    }

    private void initView() {
        mRecycler = findViewById(R.id.recycler_view);
        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);
        if (bundle != null) {
             id = bundle.getString(PACKAGE_ID);
             title = bundle.getString(PACKAGE_NAME);
            // mViewModel.getPackageDetailList(id);
            setUpToolBar(title, true);
            getDoctorListList(AppPreferences.getPreferenceInstance(this).getUserId(),id,AppPreferences.getPreferenceInstance(this).getUserLocation());
        }
       /* RecyclerView mRecycler = findViewById(R.id.recycler_view);
        mAdapter = new DoctorsByCategoryAdapter(this, this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);*/
    }
    private void observeData() {
        mViewModel.getPackageDetailListData().observe(this, this::observeServicesData);
    }

    private void observeServicesData(Resource<ArrayList<DoctorsByCategoryModel>> arrayListResource) {
        switch (arrayListResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                mModelList = arrayListResource.mData;
                //  mAdapter.updateData(arrayListResource.mData);
                findViewById(R.id.tv_no_data_found).setVisibility(mModelList.size() == 0 ? View.VISIBLE : View.GONE);
                break;

            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                break;
            default:
                break;
        }
    }
    @Override
    public void OnItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(PACKAGE_ID,mCatList.get(position).getId());
        bundle.putString(PACKAGE_NAME, title);
        bundle.putString(PACKAGE_Cat_ID, mCatList.get(position).getId());
      //  switchActivity(DoctorsByCategoryActivity.class, bundle);   // call after hospital list
        switchActivity(HospitalListActivity.class, bundle);   // call after hospital list

       /* bundle.putParcelable(PACKAGE_IMAGE, mModelList.get(position));
        switchActivity(DoctorsByCategoryDetailActivity.class, bundle);*/
    }
    public void getDoctorListList(String userId,String catId,String cityId) {
        Log.d("userId","userId....."+userId);
        Log.d("catId","catId....."+catId);
        Log.d("cityId","cityId....."+cityId);
        showProgressDialog();
      /*  DoctorsListSend catIdSend=new DoctorsListSend();
        catIdSend.setCateid(catId);*/
        Call<PackageCatDetail> call = RetrofitClient.getInstance().getMyApi().getServicePackageDetail(userId,
                catId,cityId );
        call.enqueue(new Callback<PackageCatDetail>() {
            @Override
            public void onResponse(Call<PackageCatDetail> call, Response<PackageCatDetail> response) {
                dismissProgressDialog();
                mCatList=response.body().getData();
                Log.d("tagg","data....."+response.body().getData());
                if (mCatList!=null&&mCatList.size()>0){
                    mCatList=response.body().getData();
                    AdapterSet(mCatList);
                }
               /* cityList=response.body().getData();
                if (cityList!=null&& cityList.size()>0){
                    CityAdapter adapter=new CityAdapter(EditProfileActivity.this,cityList);
                    spCity.setAdapter(adapter);
                }else {
                    Toast.makeText(EditProfileActivity.this, "No city found", Toast.LENGTH_SHORT).show();
                }*/
            }
            @Override
            public void onFailure(Call<PackageCatDetail> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AdapterSet(List<PackageCatDetail.Datum> list){
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DoctorsByCategoryAdapter(this, this,list);
        mRecycler.setAdapter(mAdapter);
    }
}
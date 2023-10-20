package com.app.treatEasy.feature.packages.doctors.package_detail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.doctorscat.SurgeryDetailsDoctorsAdapter;
import com.app.treatEasy.feature.packages.HospitalModel.DoctorsAdapterByClientId;
import com.app.treatEasy.feature.packages.HospitalModel.DoctorsSend;
import com.app.treatEasy.feature.packages.doctor_detail.DoctorsByCategoryDetailActivity;
import com.app.treatEasy.home.DoctorResponseModel;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsByCategoryActivity extends BaseActivity implements ItemClickListener {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private DoctorsByCategoryViewModel mViewModel;
    private SurgeryDetailsDoctorsAdapter mAdapter;
    private  RecyclerView mRecycler;
    private ArrayList<DoctorsByCategoryModel> mModelList = new ArrayList<>();
    private List<DoctorResponseModel.Datum> mCatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DoctorsByCategoryViewModel.class);
        initView();

      //  observeData();

    }

    private void initView() {
         mRecycler = findViewById(R.id.recycler_view);
        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);
        if (bundle != null) {
            String id = bundle.getString(PACKAGE_ID);
            String title = bundle.getString(PACKAGE_NAME);
            String catId = bundle.getString(PACKAGE_Cat_ID);
          //  mViewModel.getPackageDetailList(id,catId);
            setUpToolBar(title, true);
            getDoctorListList(id,catId);
        }
       RecyclerView mRecycler = findViewById(R.id.recycler_view);
       mAdapter = new SurgeryDetailsDoctorsAdapter(this, this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
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
                mAdapter.updateData(arrayListResource.mData);
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
        bundle.putParcelable(PACKAGE_IMAGE, mModelList.get(position));
        switchActivity(DoctorsByCategoryDetailActivity.class, bundle);
    }
    public void getDoctorListList(String pckgId,String clientId){
        showProgressDialog();
        DoctorsSend catIdSend=new DoctorsSend();
        catIdSend.setPkgid(pckgId);
        catIdSend.setClientid(clientId);
        Call<DoctorResponseModel> call = RetrofitClient.getInstance().getMyApi().getDoctorsByClientId(catIdSend);
        call.enqueue(new Callback<DoctorResponseModel>() {
            @Override
            public void onResponse(Call<DoctorResponseModel> call, Response<DoctorResponseModel> response) {
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
            public void onFailure(Call<DoctorResponseModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void AdapterSet(List<DoctorResponseModel.Datum> list){
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        DoctorsAdapterByClientId mAdapter = new DoctorsAdapterByClientId(this, this,list);
        mRecycler.setAdapter(mAdapter);
    }
}
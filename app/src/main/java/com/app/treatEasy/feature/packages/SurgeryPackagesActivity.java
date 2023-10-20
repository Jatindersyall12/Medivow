package com.app.treatEasy.feature.packages;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.SurgeryPackegeDetailActivity;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
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

public class SurgeryPackagesActivity extends BaseActivity implements ItemClickListener {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private SurgeryPackagesViewModel mViewModel;
    private SurgeryPackagesAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<SurgeryPackagesModel.Datum> mPackageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery_packages);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        //mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SurgeryPackagesViewModel.class);
         initView();
        //mViewModel.getServices();
        //observeData();
    }
    private void initView() {
        setUpToolBar(getString(R.string.surgery_package), true);

         mRecycler = findViewById(R.id.recycler_view);
        getSurgeryData(AppPreferences.getPreferenceInstance(this).getUserId());

    }

    private void observeData() {
        mViewModel.getServicesData().observe(this, this::observeServicesData);
    }

    private void observeServicesData(Resource<ArrayList<SurgeryPackagesModel>> arrayListResource) {
        switch (arrayListResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
               // mPackageList = arrayListResource.mData;
                findViewById(R.id.tv_no_data_found).setVisibility(mPackageList.size() == 0 ? View.VISIBLE : View.GONE);
                mAdapter.updateData(arrayListResource.mData);
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
        bundle.putString(PACKAGE_ID, mPackageList.get(position).getId());
        bundle.putString(PACKAGE_NAME, mPackageList.get(position).getTitle());
       // switchActivity(DoctorsByCategoryActivity.class, bundle);
        switchActivity(SurgeryPackegeDetailActivity.class, bundle);
    }

    public void getSurgeryData(String userId) {
        showProgressDialog();
       /* GetProfileSend model=new GetProfileSend();
        model.setUserid(userId);*/
        Call<SurgeryPackagesModel> call = RetrofitClient.getInstance().getMyApi().getServicePackage(userId);
        call.enqueue(new Callback<SurgeryPackagesModel>() {
            @Override
            public void onResponse(Call<SurgeryPackagesModel> call, Response<SurgeryPackagesModel> response) {
                if (response.body().getStatusCode()==200){
                    dismissProgressDialog();

                    mPackageList=response.body().getData();
                    AdapterSet(mPackageList);

                }else {
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SurgeryPackagesModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AdapterSet(List<SurgeryPackagesModel.Datum> list){
        mAdapter = new SurgeryPackagesAdapter(this, this,list);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(mAdapter);
    }
}
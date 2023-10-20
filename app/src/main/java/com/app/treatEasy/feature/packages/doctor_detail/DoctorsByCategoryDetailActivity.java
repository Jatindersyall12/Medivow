package com.app.treatEasy.feature.packages.doctor_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryModel;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

public class DoctorsByCategoryDetailActivity extends BaseActivity implements ItemClickListener {
    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private DoctorsDetailViewModel mViewModel;
    private DoctorsDetailAdapter mAdapter;
    private ArrayList<DoctorsDetailModel> mModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DoctorsDetailViewModel.class);

        initView();

     //   observeData();
    }
    private void initView() {
        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);

        if (bundle != null) {
            DoctorsByCategoryModel data = bundle.getParcelable(PACKAGE_IMAGE);

            ((TextView) findViewById(R.id.txt_package_name)).setText(data.doctorName);
            ((TextView) findViewById(R.id.txt_package_description)).setText(data.description);

            setUpToolBar(data.doctorName, true);
            setImage(data.profileImage, findViewById(R.id.item_image), R.mipmap.doctor_banner);
            mViewModel.getPackageDetailList(data.id);
        }
        RecyclerView mRecycler = findViewById(R.id.recycler_view);
        mAdapter = new DoctorsDetailAdapter(this, this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
    }
    private void observeData() {
        mViewModel.getPackageDetailListData().observe(this, this::observeServicesData);
    }

    private void observeServicesData(Resource<ArrayList<DoctorsDetailModel>> arrayListResource) {
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

    }
}
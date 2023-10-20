package com.app.treatEasy.feature.packages.package_detail;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

public class PackageDetailActivity extends BaseActivity implements ItemClickListener {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private PackageDetailViewModel mViewModel;
    private PackageDetailAdapter mAdapter;
    private ArrayList<PackageDetailModel> mModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PackageDetailViewModel.class);
        initView();

        observeData();
    }

    private void initView() {
        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);

        if (bundle != null) {
            String id = bundle.getString(PACKAGE_ID);
            String title = bundle.getString(PACKAGE_NAME);
            mViewModel.getPackageDetailList(id);
            setUpToolBar(title, true);
        }

        RecyclerView mRecycler = findViewById(R.id.recycler_view);
        mAdapter = new PackageDetailAdapter(this, this);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(mAdapter);
    }

    private void observeData() {
        mViewModel.getPackageDetailListData().observe(this, this::observeServicesData);
    }

    private void observeServicesData(Resource<ArrayList<PackageDetailModel>> arrayListResource) {
        switch (arrayListResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                mModelList = arrayListResource.mData;
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
        bundle.putString(PACKAGE_IMAGE, mModelList.get(position).image);
        bundle.putString(PACKAGE_NAME, mModelList.get(position).title);
        bundle.putString(PACKAGE_DESCRIPTION, mModelList.get(position).description);
        bundle.putString(PACKAGE_FEE, mModelList.get(position).fee);
        bundle.putString(PACKAGE_DISCOUNT_FEE, mModelList.get(position).discounted_fee);
        switchActivity(DescriptionActivity.class, bundle);
    }
}
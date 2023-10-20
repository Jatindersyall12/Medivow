package com.app.treatEasy.feature.services;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesActivity extends BaseActivity implements ItemClickListener {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private ServicesViewModel mViewModel;
    private ServicesAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<ServicesModel.Datum> mPackageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery_packages);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        //mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ServicesViewModel.class);
        initView();

     }

     private void initView() {

         setUpToolBar(getString(R.string.services), true);

         mRecycler = findViewById(R.id.recycler_view);

         getServiceData(AppPreferences.getPreferenceInstance(this).getUserId(),AppPreferences.getPreferenceInstance(this).getUserLocation());

    }

    private void observeData() {
        mViewModel.getServicesData().observe(this, this::observeServicesData);
    }

    private void observeServicesData(Resource<ArrayList<ServicesModel>> arrayListResource) {
        switch (arrayListResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
             //   mPackageList = arrayListResource.mData;
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
//        Bundle bundle = new Bundle();
//        bundle.putString(PACKAGE_ID, mPackageList.get(position).id);
//        bundle.putString(PACKAGE_NAME, mPackageList.get(position).title);
//        switchActivity(DoctorsByCategoryActivity.class, bundle);
        showCallDialog(position);
       /* showAlertMessageDialog(getString(R.string.alert), String.format(getString(R.string.book_service), mPackageList.get(position).title),
                null, getString(R.string.tv_ok), null, null);*/
    }
    public void showCallDialog(int position){
        final BottomSheetDialog dialog = new BottomSheetDialog(ServicesActivity.this,R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_service);

        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
        TextView tvService =  dialog.findViewById(R.id.tvService);
        TextView tvLocation =  dialog.findViewById(R.id.tvLocation);
        TextView tvPrice =  dialog.findViewById(R.id.tvPrice);
        TextView tvDiscount =  dialog.findViewById(R.id.tvDiscount);
        TextView tvTotal =  dialog.findViewById(R.id.tvTotal);
        TextView tvTime =  dialog.findViewById(R.id.tvTime);

        tvService.setText(mPackageList.get(position).getTitle());
        tvLocation.setText(mPackageList.get(position).getCityName());
        tvPrice.setText(mPackageList.get(position).getPrice());
        tvDiscount.setText(mPackageList.get(position).getDiscountPrice());
        tvTime.setText(mPackageList.get(position).getTiming());

        if (!mPackageList.get(position).getPrice().equals("")&&!mPackageList.get(position).getDiscountPrice().equals("")){
            double price=Double.parseDouble(mPackageList.get(position).getPrice());
            double discount=Double.parseDouble(mPackageList.get(position).getDiscountPrice());
            double total=price-discount;
            tvTotal.setText(""+total);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9643691869"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getServiceData(String userId,String cityId) {
        showProgressDialog();
       /* GetProfileSend model=new GetProfileSend();
        model.setUserid(userId);*/
        Call<ServicesModel> call = RetrofitClient.getInstance().getMyApi().getService(userId,cityId);
        call.enqueue(new Callback<ServicesModel>() {
            @Override
            public void onResponse(Call<ServicesModel> call, Response<ServicesModel> response) {
                if (response.body().getStatusCode()==200){
                    dismissProgressDialog();
                    mPackageList=response.body().getData();
                    if (mPackageList!=null&&mPackageList.size()>0){
                        AdapterSet(mPackageList);
                    }else {
                        Toast.makeText(getApplicationContext(), "No Service found", Toast.LENGTH_LONG).show();
                    }

                }else {
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ServicesModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AdapterSet(List<ServicesModel.Datum> list){
        mAdapter = new ServicesAdapter(this, this,list);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
    }
}
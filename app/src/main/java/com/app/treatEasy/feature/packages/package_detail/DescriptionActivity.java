package com.app.treatEasy.feature.packages.package_detail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.doctorsDetail.DoctorsDetailAdapter;
import com.app.treatEasy.doctorsDetail.DoctorsDetailRes;
import com.app.treatEasy.doctorsDetail.ViewPagerDoctor;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionActivity extends BaseActivity {
      String id,title;
      private ViewPager viewPagerMain;
      private int currentPage = 0;
      private SpringDotsIndicator dot1;
      private  TextView txt_package_name,txt_package_description,txt_city_name,txt_package_address;
      List<DoctorsDetailRes.Datum.Image>imageList;
      List<DoctorsDetailRes.Datum.Doctor>doctorList;
      RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        id=getIntent().getStringExtra("id");
        title=getIntent().getStringExtra("title");
        imageList=new ArrayList<>();
        doctorList=new ArrayList<>();
        initView();
    }

    private void initView() {
        viewPagerMain=findViewById(R.id.viewPagerMain);
        dot1=findViewById(R.id.dot1);
        txt_package_name=findViewById(R.id.txt_package_name);
        txt_package_description=findViewById(R.id.txt_package_description);
        txt_city_name=findViewById(R.id.txt_city_name);
        txt_package_address=findViewById(R.id.txt_package_address);
        mRecycler=findViewById(R.id.recycler_view);

      //  Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);
                // mViewModel.getPackageDetailList(id);
                setUpToolBar(title, true);
                getDoctorListList(AppPreferences.getPreferenceInstance(this).getUserId(),id);
    }

    public void getDoctorListList(String userId,String catId){
        showProgressDialog();

        Log.d("userId","userId....."+userId);
        Log.d("catId","catId....."+catId);

       /* HospitalSendModel catIdSend=new HospitalSendModel();
        catIdSend.setPkgid(catId);*/

        Call<DoctorsDetailRes> call = RetrofitClient.getInstance().getMyApi().getHospitalDetail(userId,catId);
        call.enqueue(new Callback<DoctorsDetailRes>(){
            @Override
            public void onResponse(Call<DoctorsDetailRes> call, Response<DoctorsDetailRes> response) {
                dismissProgressDialog();
               if (response.body().getStatusCode()==200){
                   //  BaseUtils.setImage(response.body().getData().getImage(),imgHospital, R.mipmap.medivow_logo);
                   txt_package_name.setText(response.body().getData().get(0).getClientName());
                   txt_package_description.setText(response.body().getData().get(0).getDescription());
                   txt_city_name.setText(response.body().getData().get(0).getCityName());
                   txt_package_address.setText(response.body().getData().get(0).getAddress());
                   imageList=response.body().getData().get(0).getImages();
                   doctorList=response.body().getData().get(0).getDoctors();
                   if (imageList!=null&&imageList.size()>0){
                       updateHomePageBanner(imageList);
                   }
                   if (doctorList!=null&&doctorList.size()>0){
                       mRecycler.setLayoutManager(new LinearLayoutManager(DescriptionActivity.this));
                       DoctorsDetailAdapter mAdapter = new DoctorsDetailAdapter(DescriptionActivity.this,doctorList);
                       mRecycler.setAdapter(mAdapter);
                   }
               }


            }
            @Override
            public void onFailure(Call<DoctorsDetailRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateHomePageBanner(List<DoctorsDetailRes.Datum.Image> mData) {
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage <= mData.size()) {
                viewPagerMain.setCurrentItem(currentPage++, true);
            } else {
                viewPagerMain.setCurrentItem(0, true);
                currentPage = 0;
            }
        };
        Timer timer = new Timer();
        long DELAY_MS = 500;
        long PERIOD_MS = 2000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        ViewPagerDoctor mViewPagerAdapter = new ViewPagerDoctor(this, mData);
        viewPagerMain.setAdapter(mViewPagerAdapter);
        dot1.setViewPager(viewPagerMain);
        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                if (mData!=null &&mData.size()>0){
                    //  addBottomDots(position);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
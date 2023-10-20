package com.app.treatEasy.feature.home_screen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.appointmentlist.AppointmentListActivity;
import com.app.treatEasy.baseui.BaseFragment;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.baseui.ViewPagerAdapter;
import com.app.treatEasy.doctorsDetail.PrimeDoctorRes;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.packages.SurgeryPackagesActivity;
import com.app.treatEasy.feature.scan_qr.ScanQRCodeActivity;
import com.app.treatEasy.feature.services.ServicesActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.home.DoctorResponseModel;
import com.app.treatEasy.home.DoctorSendModel;
import com.app.treatEasy.home.HomeDoctorAdapter;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.new_feature.home.HomeResponse;
import com.app.treatEasy.new_feature.home.ViewPagerSecondAdapter;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.search.SearchActivity;
import com.app.treatEasy.state.CityAdapter;
import com.app.treatEasy.state.CityResponseModel;
import com.app.treatEasy.state.CitySendModel;
import com.app.treatEasy.state.RetrofitClient;
import com.app.treatEasy.state.SpinAdapter;
import com.app.treatEasy.state.StateResponseModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinod Kumar (Aug 2019).
 */

public class HomeFragment extends BaseFragment implements ItemClickListener,View.OnClickListener {
    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private LinearLayout sliderDotsPanel;
    private LinearLayout slider_dots_layout_second;
    private int currentPage = 0;
    private int currentPageSecond = 0;
    private HomeViewModel mViewModel;
    private ViewPager mViewPager;
    private ViewPager viewPagerSecond;
    private RecyclerView mRecycler;
    private HomeDoctorAdapter mAdapter;
    private Spinner spState,spCity,spDoctor;
    private List<DoctorResponseModel.Datum> doctorList = new ArrayList<>();
    private List<Doctor> doctorListResponse = new ArrayList<>();
    private List<HomeResponse.TopBanner> firstBannerList;
    private List<HomeResponse.MiddleBanner> secondBannerList ;
    List<StateResponseModel.Datum> stateList;
    List<CityResponseModel.Datum>cityList;
    String location="",uId="",cityId,searchFor="",cityIdForSearch="";
    int scrollCount;
    TextView tvChangeLocation,tvHospitalBill,tvLocation,tvAmount,tvSell,tvUpcomingAppointment;
    ImageView imgbanner1,imgbanner2;
    LinearLayout user_state_layout,user_city_layout,llLocation;
    SpringDotsIndicator dot2,dot1;
    private RelativeLayout rlSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
       // mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HomeViewModel.class);
        location=AppPreferences.getPreferenceInstance(getActivity()).getUserLocation();
        uId=AppPreferences.getPreferenceInstance(getActivity()).getUserId();
        Log.d("location","location....."+location);

        sliderDotsPanel = view.findViewById(R.id.slider_dots_layout);
        slider_dots_layout_second = view.findViewById(R.id.slider_dots_layout_second);

        mViewPager = view.findViewById(R.id.viewPagerMain);
        viewPagerSecond = view.findViewById(R.id.viewPagerSecond);
        mRecycler = view.findViewById(R.id.recycler_view);
        tvChangeLocation = view.findViewById(R.id.tvChangeLocation);
        user_state_layout = view.findViewById(R.id.user_state_layout);
        user_city_layout = view.findViewById(R.id.user_city_layout);
        imgbanner1 = view.findViewById(R.id.imgbanner1);
        imgbanner2 = view.findViewById(R.id.imgbanner2);
        dot2 = view.findViewById(R.id.dot2);
        dot1 = view.findViewById(R.id.dot1);
        tvHospitalBill = view.findViewById(R.id.tvHospitalBill);
        tvSell = view.findViewById(R.id.tvSell);
        tvUpcomingAppointment=view.findViewById(R.id.tvUpcomingAppointment);

        llLocation = view.findViewById(R.id.llLocation);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvAmount = view.findViewById(R.id.tvAmount);

        rlSearch = view.findViewById(R.id.rlSearch);
        tvUpcomingAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AppointmentListActivity.class);
                startActivity(intent);
            }
        });

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationDialog();
            }
        });

        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFilterDialog();
            }
        });

        tvSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),SeeAllDoctorsActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.scan_qr_code_layout).setOnClickListener(this);
        view.findViewById(R.id.pay_layout).setOnClickListener(this);
        view.findViewById(R.id.wallet_layout).setOnClickListener(this);
        view.findViewById(R.id.surgery_package_layout).setOnClickListener(this);
        view.findViewById(R.id.services_layout).setOnClickListener(this);
               tvHospitalBill.setOnClickListener(this);

        getBannerAddList(uId,location);

       /* mRecycler.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                mRecycler.smoothScrollToPosition(mAdapter.getItemCount() - 1);
            }
        });*/
    }
    /**
     * Listen live data from home view model
     */
    private void observeData() {
        mViewModel.getAllBannerData().observe(getActivity(), this::observeBannerData);
    }

    private void observeBannerData(Resource<ArrayList<BannerModel>> arrayListResource) {
        switch (arrayListResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
             //   mBannerList = arrayListResource.mData;
               /* for (int i=0;i<arrayListResource.mData.size();i++){
                    if (arrayListResource.mData.get(i).position.equals("1")){
                        BannerModel data=new BannerModel();
                        data.setPosition(arrayListResource.mData.get(i).position);
                        data.setTitle(arrayListResource.mData.get(i).title);
                        data.setBanner(arrayListResource.mData.get(i).banner);
                        data.setStatus(arrayListResource.mData.get(i).status);
                        mBannerList.add(data);
                    }else if (arrayListResource.mData.get(i).position.equals("2")){
                        BannerModel data=new BannerModel();
                        data.setPosition(arrayListResource.mData.get(i).position);
                        data.setTitle(arrayListResource.mData.get(i).title);
                        data.setBanner(arrayListResource.mData.get(i).banner);
                        data.setStatus(arrayListResource.mData.get(i).status);
                        secondBannerList.add(data);
                    }
                }
                updateHomePageBanner(mBannerList);
                updateSecondPageBanner(secondBannerList);
                addBottomDots(0);
                addSecondBottomDots(0);*/
                break;

            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), arrayListResource.mMessage,
                        null, getString(R.string.tv_ok), null, null);
                break;
            default:
                break;
        }
    }
    private void updateHomePageBanner(List<HomeResponse.TopBanner> mData) {
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage <= mData.size()) {
                mViewPager.setCurrentItem(currentPage++, true);
            } else {
                mViewPager.setCurrentItem(0, true);
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

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getActivity(), mData);
        mViewPager.setAdapter(mViewPagerAdapter);
         dot1.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void updateSecondPageBanner(List<HomeResponse.MiddleBanner> mData) {
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPageSecond <= mData.size()) {
                viewPagerSecond.setCurrentItem(currentPageSecond++, true);
            } else {
                viewPagerSecond.setCurrentItem(0, true);
                currentPageSecond = 0;
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

        ViewPagerSecondAdapter mViewPagerAdapter = new ViewPagerSecondAdapter(getActivity(), mData);
        viewPagerSecond.setAdapter(mViewPagerAdapter);
        dot2.setViewPager(viewPagerSecond);

        viewPagerSecond.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (mData!=null &&mData.size()>0){

                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private ArrayList<DoctorModel> doctorData() {
        ArrayList<DoctorModel> list = new ArrayList<>();

        DoctorModel model = new DoctorModel("Dr Mistry", R.mipmap.doctor_1);
        list.add(model);

        model = new DoctorModel("Dr Valentine", R.mipmap.doctor_2);
        list.add(model);

        model = new DoctorModel("Dr Everhart", R.mipmap.doctor_3);
        list.add(model);

        model = new DoctorModel("Dr Truluck", R.mipmap.doctor_4);
        list.add(model);

        return list;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn((HomeActivity) getActivity());

    }
    private void addSecondBottomDots(int currentPage) {
        TextView[] dots = new TextView[secondBannerList.size()];

        slider_dots_layout_second.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            slider_dots_layout_second.addView(dots[i]);
        }

        dots[currentPage].setTextColor(Color.parseColor("#FA7100"));
    }
    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[firstBannerList.size()];

        sliderDotsPanel.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            sliderDotsPanel.addView(dots[i]);
        }
        dots[currentPage].setTextColor(Color.parseColor("#FA7100"));
    }
    @Override
    public void OnItemClick(View view, int position) {
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan_qr_code_layout:
            case R.id.pay_layout:
                startActivity(ScanQRCodeActivity.class);
                //choesePaymentDialog();
                break;

            case R.id.wallet_layout:
                startActivity(WalletActivity.class);
                break;

            case R.id.surgery_package_layout:
                startActivity(SurgeryPackagesActivity.class);
                break;
            case R.id.services_layout:
                startActivity(ServicesActivity.class);
                break;
            case R.id.tvHospitalBill:
                startActivity(ScanQRCodeActivity.class);
                break;
        }
    }
    public void getBannerAddList(String userId,String cityId){
        //mBannerList=new ArrayList<>();
        firstBannerList=new ArrayList<>();
        secondBannerList=new ArrayList<>();

        Call<HomeResponse> call = RetrofitClient.getInstance().getMyApi().getHome(userId,cityId);
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                //   mBannerList=response.body().getData();
                if (response.body().getStatusCode()==200){
                    firstBannerList=response.body().getData().getTopBanners();
                    secondBannerList=response.body().getData().getMiddleBanners();
                    doctorListResponse=response.body().getData().getDoctors();

                    tvLocation.setText(response.body().getData().getLocation().getCityName()+" ,"+
                            response.body().getData().getLocation().getState());
                    tvAmount.setText(response.body().getData().getWalletAmount());
                    AppPreferences.getPreferenceInstance(getActivity()).setPayment(response.body().getData().getWalletAmount());

                    if (doctorListResponse!=null&&doctorListResponse.size()>0) {

                        AdapterSet(doctorListResponse);

                    }
                    if (firstBannerList != null && firstBannerList.size() > 0) {
                        updateHomePageBanner(firstBannerList);
                     //   addBottomDots(0);
                        mViewPager.setVisibility(View.VISIBLE);
                        dot1.setVisibility(View.VISIBLE);
                        imgbanner1.setVisibility(View.GONE);
                    } else {
                        mViewPager.setVisibility(View.GONE);
                        dot1.setVisibility(View.GONE);
                        imgbanner1.setVisibility(View.VISIBLE);
                    }
                    if (secondBannerList != null && secondBannerList.size() > 0) {
                        updateSecondPageBanner(secondBannerList);
                        viewPagerSecond.setVisibility(View.VISIBLE);
                        dot2.setVisibility(View.VISIBLE);
                        imgbanner2.setVisibility(View.GONE);
                    } else {
                        viewPagerSecond.setVisibility(View.GONE);
                        dot2.setVisibility(View.GONE);
                        imgbanner2.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.e("issue",t.getMessage());

            }
        });
    }
    public void AdapterSet(List<Doctor>list){

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        mAdapter = new HomeDoctorAdapter(getActivity(), this,list);
        mRecycler.setAdapter(mAdapter);
        autoScrollAnother();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemViewCacheSize(1000);
        mRecycler.setDrawingCacheEnabled(true);
        mRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mRecycler.setAdapter(mAdapter);

    }

    public void autoScrollAnother() {
        scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mRecycler.smoothScrollToPosition((scrollCount++));
                if (scrollCount == mAdapter.getItemCount()-1) {
                   // stockListModels.addAll(stockListModels);
                    mAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            try {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(requireContext()) {
                    private static final float SPEED = 3500f;// Change this value (default=25f)
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void getStateList(){
        stateList=new ArrayList<>();
        Call<StateResponseModel> call = RetrofitClient.getInstance().getMyApi().getStateList();
        call.enqueue(new Callback<StateResponseModel>() {
            @Override
            public void onResponse(Call<StateResponseModel> call, Response<StateResponseModel> response) {
                stateList=response.body().getData();
                if (stateList!=null&& stateList.size()>0){
                    SpinAdapter adapter=new SpinAdapter(getActivity(),stateList);
                    spState.setAdapter(adapter);
                   // getCityList(stateList.get(0).getValue());
                }
            }
            @Override
            public void onFailure(Call<StateResponseModel> call, Throwable t) {
                Log.e("issue",t.getMessage());
                Toast.makeText(getActivity(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCityList(String value){
        cityList=new ArrayList<>();
        showProgressDialog();
        CitySendModel citySend=new CitySendModel();
        citySend.setStateid(value);
        Call<CityResponseModel> call = RetrofitClient.getInstance().getMyApi().getCityList(value);
        call.enqueue(new Callback<CityResponseModel>() {
            @Override
            public void onResponse(Call<CityResponseModel> call, Response<CityResponseModel> response) {
                dismissProgressDialog();
                cityList=response.body().getData();
                if (cityList!=null&& cityList.size()>0){
                    CityAdapter adapter=new CityAdapter(getActivity(),cityList);
                    spCity.setAdapter(adapter);
                   // user_state_layout.setVisibility(View.GONE);
                   // user_city_layout.setVisibility(View.VISIBLE);
                }else {
                    //  Toast.makeText(getActivity(), "No city found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CityResponseModel> call, Throwable t) {
                dismissProgressDialog();
                Log.e("issue",t.getMessage());
                Toast.makeText(getActivity(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void showLocationDialog(){

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_location);

        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
        TextView tvSelectState = (TextView) dialog.findViewById(R.id.tvSelectState);
        TextView tvSelectCity = (TextView) dialog.findViewById(R.id.tvSelectCity);
        spState = dialog.findViewById(R.id.spState);
        spCity = dialog.findViewById(R.id.spCity);

        tvSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectState.setVisibility(View.GONE);
                spState.setVisibility(View.VISIBLE);
                spState.performClick();
            }
        });

        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectCity.setVisibility(View.GONE);
                spCity.setVisibility(View.VISIBLE);
                spCity.performClick();
            }
        });
        getStateList();


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCityList(stateList.get(i).getValue());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String  location=cityList.get(i).getId();
                AppPreferences.getPreferenceInstance(getActivity()).setUserLocation(location);
                //  getDoctorList(location);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stateList!=null&&stateList.size()==0)
                {
                    Toast.makeText(getActivity(), "Please select state first", Toast.LENGTH_SHORT).show();
                }
                else if(cityList!=null&&cityList.size()==0)
                {
                    Toast.makeText(getActivity(), "Please select city first", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismiss();
                }
                //if (cityList!=null&&cityList.size()>0){
                  //  dialog.dismiss();
                /*else {
                    Toast.makeText(getActivity(), "City Not Found", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        dialog.show();
    }

    public void choesePaymentDialog(){

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_chooese_booking);

        ImageView imgDoctorFee=dialog.findViewById(R.id.imgDoctorFee);
        ImageView imgLabTest=dialog.findViewById(R.id.imgLabTest);
        ImageView imgHospital=dialog.findViewById(R.id.imgHospital);
        ImageView imgSurgery=dialog.findViewById(R.id.imgSurgery);
        ImageView imgPharmacy=dialog.findViewById(R.id.imgPharmacy);

        imgDoctorFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDoctorFee=new Intent(getActivity(),ScanQRCodeActivity.class);
                intentDoctorFee.putExtra("booking","booking");
                startActivity(intentDoctorFee);
            }
        });

        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();
       // getBannerAddList(uId,location);
        /*location=AppPreferences.getPreferenceInstance(getActivity()).getUserLocation();
        uId=AppPreferences.getPreferenceInstance(getActivity()).getUserId();
        getBannerAddList(uId,location);*/
    }

    public void searchFilterDialog(){

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_search_filter);
        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);

        TextView tvSelectState = (TextView) dialog.findViewById(R.id.tvSelectState);
        TextView tvSelectCity = (TextView) dialog.findViewById(R.id.tvSelectCity);
        TextView tvSelectDoctor = (TextView) dialog.findViewById(R.id.tvSelectDoctor);

        spState = dialog.findViewById(R.id.spState);
        spCity = dialog.findViewById(R.id.spCity);
        spDoctor = dialog.findViewById(R.id.spDoctor);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityIdForSearch.equals("")){
                    Snackbar.make(btnSubmit,"Please select city first",Snackbar.LENGTH_LONG).show();
                }else if (searchFor.equals("")){
                    Snackbar.make(btnSubmit,"Please select type",Snackbar.LENGTH_LONG).show();
                }else {
                    Intent intent=new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("cityId",cityIdForSearch);
                    intent.putExtra("type",searchFor);
                    startActivity(intent);
                }

            }
        });

        tvSelectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectDoctor.setVisibility(View.GONE);
                spDoctor.setVisibility(View.VISIBLE);
                spDoctor.performClick();
            }
        });

        ArrayAdapter genderAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                Specialties);
        genderAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spDoctor.setAdapter(genderAdapter);

        tvSelectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectDoctor.setVisibility(View.GONE);
                spDoctor.setVisibility(View.VISIBLE);
                spDoctor.performClick();

            }
        });

        tvSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectState.setVisibility(View.GONE);
                spState.setVisibility(View.VISIBLE);
                spState.performClick();
            }
        });

        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectCity.setVisibility(View.GONE);
                spCity.setVisibility(View.VISIBLE);
                spCity.performClick();
            }
        });

        getStateList();

        spDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               searchFor=Specialties[i];

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCityList(stateList.get(i).getValue());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityIdForSearch=cityList.get(i).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

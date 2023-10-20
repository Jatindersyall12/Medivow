package com.app.treatEasy.feature.home_screen;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.doctorsDetail.PrimeDoctorAdapter;
import com.app.treatEasy.doctorsDetail.PrimeDoctorRes;
import com.app.treatEasy.feature.family_member.add_family_member.AddFamilyMemberActivity;
import com.app.treatEasy.home.DoctorResponseModel;
import com.app.treatEasy.home.DoctorSendModel;
import com.app.treatEasy.home.HomeDoctorAdapter;
import com.app.treatEasy.new_feature.home.HomeResponse;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllDoctorsActivity extends BaseActivity{
    private RecyclerView mRecycler;
    String location,uId;
    private List<PrimeDoctorRes.Datum> doctorListResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_doctors);
        initView();

        doctorListResponse=new ArrayList<>();

        location= AppPreferences.getPreferenceInstance(SeeAllDoctorsActivity.this).getUserLocation();
        uId=AppPreferences.getPreferenceInstance(SeeAllDoctorsActivity.this).getUserId();

        getDoctorList(location,uId);

    }

    private void initView() {
        setUpToolBar("Top Listed Doctors", true);
        mRecycler = findViewById(R.id.recycler_view);
    }

    public void getDoctorList(String location,String uId ){

        /*DoctorSendModel model=new DoctorSendModel();
        model.setCity_id(location);
        model.setUserid(uId);*/

        Call<PrimeDoctorRes> call = RetrofitClient.getInstance().getMyApi().getDoctorList(uId,location);
        call.enqueue(new Callback<PrimeDoctorRes>() {
            @Override
            public void onResponse(Call<PrimeDoctorRes> call, Response<PrimeDoctorRes> response) {

                doctorListResponse=response.body().getData();

                mRecycler.setLayoutManager(new GridLayoutManager(SeeAllDoctorsActivity.this,2));
                PrimeDoctorAdapter mAdapter = new PrimeDoctorAdapter(SeeAllDoctorsActivity.this,doctorListResponse);
                mRecycler.setAdapter(mAdapter);

            }
            @Override
            public void onFailure(Call<PrimeDoctorRes> call, Throwable t) {
                //Toast.makeText(getActivity(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.app.treatEasy.appointmentlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentListActivity extends BaseActivity implements ItemClickListener {

    ImageView img_Hospital_image;
    TextView tvHospitalName;
    List<AppointmentListResponse> appointmentList;
    private RecyclerView mRecycler;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        appointmentList = new ArrayList<>();
        setUpToolBar(getString(R.string.my_appointment), true);
        init();
        getAppointmentList(AppPreferences.getPreferenceInstance(this).getUserId());
    }

    public void init() {
        img_Hospital_image = findViewById(R.id.img_Hospital_image);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        mRecycler = findViewById(R.id.recycler_view);
    }

    public void getAppointmentList(String userId) {


        showProgressDialog();

        Call<AppointmentListResponse> call = RetrofitClient.getInstance().getMyApi().getAppointmentList(userId);
        call.enqueue(new Callback<AppointmentListResponse>() {
            @Override
            public void onResponse(Call<AppointmentListResponse> call, Response<AppointmentListResponse> response) {
                dismissProgressDialog();

                if (response.body().getStatusCode() == 200) {

                    mRecycler.setLayoutManager(new LinearLayoutManager(AppointmentListActivity.this));
                    AppointmentListAdapter adapter = new AppointmentListAdapter(AppointmentListActivity.this,
                            response.body().getData(), AppointmentListActivity.this);
                    mRecycler.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<AppointmentListResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnItemClick(View view, int position) {

        if(view.getTag()=="Detail") {
            Intent intent = new Intent(AppointmentListActivity.this, AppointmentDetailActivity.class);
            intent.putExtra("appointmentId", "ff");
            startActivity(intent);
        }
        else {
            Log.e("cancel","cancel");
        }
    }
}
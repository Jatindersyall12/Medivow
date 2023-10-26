package com.app.treatEasy.appointmentlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseFragment;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelledFragment extends BaseFragment implements ItemClickListener {

    List<AppointmentListResponse> appointmentList;
    private RecyclerView mRecycler;
    private TextView tvNoRecord;

    private ItemClickListener context;

    public CancelledFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cancelled, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appointmentList = new ArrayList<>();
        context = this;
        mRecycler = view.findViewById(R.id.recycler_view);
        tvNoRecord = view.findViewById(R.id.tvNoRecord);
        getAppointmentList(AppPreferences.getPreferenceInstance(getActivity()).getUserId(), "3");
    }

    public void getAppointmentList(String userId, String type) {
        showProgressDialog();

        Call<AppointmentListResponse> call = RetrofitClient.getInstance().getMyApi().getAppointmentList(userId, type);
        call.enqueue(new Callback<AppointmentListResponse>() {
            @Override
            public void onResponse(Call<AppointmentListResponse> call, Response<AppointmentListResponse> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {
                    setAdapter(response);
                }
            }

            private void setAdapter(Response<AppointmentListResponse> response) {
                if (response.body().getData().size() == 0) {
                    tvNoRecord.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                } else {
                    tvNoRecord.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.VISIBLE);
                    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    AppointmentListAdapter adapter = new AppointmentListAdapter(getActivity(),
                            response.body().getData(), context);
                    mRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<AppointmentListResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnItemClick(View view, int position) {

        if (view.getTag() == "Detail") {
            Intent intent = new Intent(getActivity(), AppointmentDetailActivity.class);
            intent.putExtra("appointmentId", "ff");
            startActivity(intent);
        } else {
            Log.e("cancel", "cancel");
        }
    }
}

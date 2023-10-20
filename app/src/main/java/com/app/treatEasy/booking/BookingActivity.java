package com.app.treatEasy.booking;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends BaseActivity implements ItemClickListener {
    private RecyclerView mRecycler;
    BookingAdapter mAdapter;
    List<BookingResponse.Datum>bookingList;

    TextView tvNoRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        bookingList=new ArrayList<>();
        initView();
    }

    @Override
    public void OnItemClick(View view, int position) {

    }

    private void initView(){

        setUpToolBar("Booking Detail", true);
        mRecycler=findViewById(R.id.recycler_view);
        tvNoRecord=findViewById(R.id.tvNoRecord);

        getBookingList(AppPreferences.getPreferenceInstance(this).getUserId());

    }

    public void getBookingList(String userId){
        showProgressDialog();

        Call<BookingResponse> call = RetrofitClient.getInstance().getMyApi().getBookingDetail(userId);
        call.enqueue(new Callback<BookingResponse>(){
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                dismissProgressDialog();

                bookingList=response.body().getData();

                if (bookingList!=null&&bookingList.size()>0){
                    AdapterSet(bookingList);
                }
                else {
                    tvNoRecord.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void AdapterSet(List<BookingResponse.Datum> list){
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BookingAdapter(this, this,list);
        mRecycler.setAdapter(mAdapter);
    }
}
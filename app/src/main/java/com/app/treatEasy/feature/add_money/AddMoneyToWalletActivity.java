package com.app.treatEasy.feature.add_money;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.home_screen.DoctorModel;
import com.app.treatEasy.listeners.ItemClickListener;
import java.util.ArrayList;

public class AddMoneyToWalletActivity extends BaseActivity implements ItemClickListener {

    private TextView tvTotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_to_wallet);

        initView();
    }

    private void initView() {
        setUpToolBar(getString(R.string.add_money_to_wallet), true);

        RecyclerView mRecycler = findViewById(R.id.recycler_view);
        CardListAdapter mAdapter = new CardListAdapter(this, this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mAdapter.updateData(doctorData());

        tvTotalAmount = findViewById(R.id.tv_total_amount);

        tvTotalAmount.setText("INR "+parseUserData().walletAmount);

        findViewById(R.id.ll_next).setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
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
    public void OnItemClick(View view, int position) {

    }
}
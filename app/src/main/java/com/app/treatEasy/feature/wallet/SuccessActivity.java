package com.app.treatEasy.feature.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;

public class SuccessActivity extends BaseActivity {
   TextView tvAmount;
   String amount;
   Button btnAddAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        setUpToolBar("Payment Status",true);
        amount=getIntent().getStringExtra("amount");

        tvAmount=findViewById(R.id.tvAmount);
        btnAddAmount=findViewById(R.id.btnAddAmount);

        tvAmount.setText(amount);

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //switchActivity(HomeActivity.class);
            }
        });
    }
}
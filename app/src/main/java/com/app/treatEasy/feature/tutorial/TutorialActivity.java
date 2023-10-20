package com.app.treatEasy.feature.tutorial;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.login_module.login_screen.LoginActivity;
import com.app.treatEasy.preference.AppPreferences;

import java.util.Timer;
import java.util.TimerTask;


public class TutorialActivity extends BaseActivity {

    private LinearLayout sliderDotsPanel;
    private int currentPage = 0;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        sliderDotsPanel = findViewById(R.id.slider_dots_layout);

        setUpViewPager();

        addBottomDots(0);

       /* findViewById(R.id.txt_next).setOnClickListener(v -> {
            mViewPager.setCurrentItem(currentPage++, true);
        });*/
        findViewById(R.id.txt_next).setOnClickListener(v -> {
            AppPreferences.getPreferenceInstance(this).setFirstTime(true);
            switchActivity(LoginActivity.class);
            finish();
        });
    }


    private void setUpViewPager() {
        mViewPager = findViewById(R.id.viewPagerMain);

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage < 3) {
                mViewPager.setCurrentItem(currentPage++, true);
            } else {
                currentPage = 0;
            }
        };

        Timer timer = new Timer();
        long DELAY_MS = 500;
        long PERIOD_MS = 3000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        TutorialViewPagerAdapter mViewPagerAdapter = new TutorialViewPagerAdapter(this);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[3];

        sliderDotsPanel.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#ffffff"));
            sliderDotsPanel.addView(dots[i]);
        }

        dots[currentPage].setTextColor(Color.parseColor("#8ebf46"));
    }
}
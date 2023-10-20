package com.app.treatEasy.feature;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.tutorial.TutorialActivity;
import com.app.treatEasy.preference.AppPreferences;

/*Created by Vinod Kumar (Aug 2019)*/

public class SplashActivity extends BaseActivity {

    private Handler handler;
    private Runnable runnable;
    ProgressBar progressBar;
    private int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        progressBar=findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 2;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (AppPreferences.getPreferenceInstance(SplashActivity.this).getUserId()!=null&&!AppPreferences.getPreferenceInstance(SplashActivity.this).getUserId().equals(""))
                    switchActivity(HomeActivity.class);
                else {
                    switchActivity(TutorialActivity.class);
                }
                finish();
            }
        }).start();
       /* runnable = () -> {
            if (AppPreferences.getPreferenceInstance(this).getUserId()!=null&&!AppPreferences.getPreferenceInstance(this).getUserId().equals(""))
                switchActivity(HomeActivity.class);
            else {
                    switchActivity(TutorialActivity.class);
            }
            finish();
        };*/
    }
    /**
     * In this method we are applying delay of SPLASH_DISPLAY_LENGTH
     */
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable,SPLASH_DISPLAY_TIME);
    }
    /**
     * This method is called when activity is destroyed.
     * In this, callbacks of handler removed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}

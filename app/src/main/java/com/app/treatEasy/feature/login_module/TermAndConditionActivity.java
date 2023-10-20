package com.app.treatEasy.feature.login_module;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;

public class TermAndConditionActivity extends BaseActivity {

    WebView webview;
    ProgressBar bBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);

        setUpToolBar(getString(R.string.term), true);

        webview=findViewById(R.id.webview);
        webview.setWebViewClient(new MyBrowser());
        bBar=findViewById(R.id.progressBar);

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.loadUrl("https://www.treateasy.co.in/terms-conditions.html");
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("pagestaretd","pagestaretd");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("pagestaretd","end");
            bBar.setVisibility(View.GONE);
        }
    }
}
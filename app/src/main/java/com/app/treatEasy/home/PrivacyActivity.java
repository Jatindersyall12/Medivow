package com.app.treatEasy.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;

public class PrivacyActivity extends BaseActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);

        setUpToolBar(getString(R.string.privacy), true);

        webview=findViewById(R.id.webview);
        webview.setWebViewClient(new MyBrowser());

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.loadUrl("https://www.treateasy.co.in/privacy-policy.html");
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
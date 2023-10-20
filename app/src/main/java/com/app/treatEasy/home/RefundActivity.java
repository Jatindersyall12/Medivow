package com.app.treatEasy.home;

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

public class RefundActivity extends BaseActivity {
    WebView webview;

    ProgressBar bBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);

        setUpToolBar(getString(R.string.refund), true);

        webview = findViewById(R.id.webview);
        bBar=findViewById(R.id.progressBar);
        webview.setWebViewClient(new MyBrowser());

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.loadUrl("https://www.treateasy.co.in/cancellation-refund.html");
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
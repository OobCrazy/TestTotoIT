package com.totoit.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout errorView;
    private TextView errorText;
    private WebView webView;
    private SwipeRefreshLayout swipeContainer;
    private Button errorRefreshButton;
    private MovableFloatingActionButton fabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorView = (LinearLayout) findViewById(R.id.webview_error_view);
        errorText = (TextView) findViewById(R.id.webview_error_detail);
        webView = (WebView) findViewById(R.id.main_webview);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        errorRefreshButton = (Button) findViewById(R.id.webview_error_refresh);
        fabButton = (MovableFloatingActionButton) findViewById(R.id.fab);

        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                swipeContainer.setRefreshing(false);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String error = "Error "+errorCode+": "+description;
                errorText.setText(error);
                errorView.setVisibility(View.VISIBLE);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl(getString(R.string.web_url));

        swipeContainer.setOnRefreshListener(() -> {
            webView.reload();
            errorView.setVisibility(View.GONE);
        });

        errorRefreshButton.setOnClickListener((event) -> {
            swipeContainer.setRefreshing(true);
            webView.reload();
            errorView.setVisibility(View.GONE);
        });
    }
}
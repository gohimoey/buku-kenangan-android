package com.mastagohim.bukukenangan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Buku Kenangan - Native Android 16+
 * WebView wrapper untuk akses web app ZimaOS
 */
public class MainActivity extends Activity {

    private static final String SERVER_URL = "http://192.168.3.24:2343";
    
    private WebView webView;
    private ProgressBar progressBar;
    private TextView errorText;
    private LinearLayout errorView;
    private LinearLayout webViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorText = (TextView) findViewById(R.id.errorText);
        errorView = (LinearLayout) findViewById(R.id.errorView);
        webViewContainer = (LinearLayout) findViewById(R.id.webViewContainer);

        setupWebView();
        checkAndLoad();
    }

    private void setupWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewports(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_OLDER);
        
        // Enable file upload (for HTML5 file inputs)
        settings.setAllowFileUploads(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressBar.setProgress(progress);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                webViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (!webView.getUrl().equals(failingUrl)) return;
                showError("Server tidak dapat dijangkau.\nPastikan smartphone terhubung ke jaringan 192.168.3.0/24");
            }
        });
    }

    private void checkAndLoad() {
        if (isNetworkAvailable()) {
            webView.loadUrl(SERVER_URL);
            progressBar.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
            webViewContainer.setVisibility(View.GONE);
        } else {
            showError("Tidak ada koneksi jaringan.\nHubungkan ke jaringan lokal WiFi.");
        }
    }

    private void showError(String message) {
        progressBar.setVisibility(View.GONE);
        webViewContainer.setVisibility(View.GONE);
        errorText.setText(message);
        errorView.setVisibility(View.VISIBLE);
        
        errorView.setOnClickListener(v -> checkAndLoad());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        if (webView != null) webView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) webView.destroy();
        super.onDestroy();
    }
}
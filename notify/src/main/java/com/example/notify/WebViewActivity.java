package com.example.notify;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class WebViewActivity extends AppCompatActivity{

    private WebView mWebView;
    private ViewGroup mViewParent;
    private String TAG = "MainActivity";
    private static final String mHomeUrl = "https://v.qq.com/";

    private ImageButton mBack;
    private ImageButton mForward;
    private ImageButton mExit;
    private ImageButton mHome;
    private ImageButton mMore;
    private TextView mTvBack;

    private final int disable = 120;
    private final int enable = 255;
    private ProgressBar mPageLoadingProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        setContentView(R.layout.activity_main);
        mViewParent = (ViewGroup) findViewById(R.id.webViewParent);
        initBtnListenser();
        init();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(title)) {

        }
    }

    private void initProgressBar() {
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources()
                .getDrawable(R.drawable.color_progressbar));
    }

    private void init() {

        mWebView = new WebView(this, null);

        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

        initProgressBar();
        mWebView.addJavascriptInterface(new JavaScriptMetod(this, mWebView), "testAndroid");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG, "onPageFinished==" + url);
                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16) {
                    changGoForwardButton(view);
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                mPageLoadingProgressBar.setProgress(mWebView.getProgress());
            }

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        mWebView.loadUrl(mHomeUrl);

//        TbsLog.d("time-cost", "cost time: " + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }


    private void initBtnListenser() {
        mTvBack = (TextView) findViewById(R.id.tvback);
        mBack = (ImageButton) findViewById(R.id.btnBack);
        mForward = (ImageButton) findViewById(R.id.btnForward);
        mExit = (ImageButton) findViewById(R.id.btnExit);
        mHome = (ImageButton) findViewById(R.id.btnHome);
        mMore = (ImageButton) findViewById(R.id.btnMore);
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16) {
            mTvBack.setVisibility(View.GONE);
            mBack.setAlpha(disable);
            mForward.setAlpha(disable);
            mHome.setAlpha(disable);
        }
        mHome.setEnabled(false);

        mTvBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }
        });

        mForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoForward()) {
                    mWebView.goForward();
                }
            }
        });


        mMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });


        mHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null) {
                    mWebView.loadUrl(mHomeUrl);
                }
            }
        });

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Process.killProcess(Process.myPid());
            }
        });
    }


    private void changGoForwardButton(WebView view) {
        if (view.canGoBack()) {
            mBack.setAlpha(enable);
            mTvBack.setVisibility(View.VISIBLE);
        } else {
            mBack.setAlpha(disable);
            mTvBack.setVisibility(View.GONE);
        }
        if (view.canGoForward()) {
            mForward.setAlpha(enable);
        } else {
            mForward.setAlpha(disable);
        }
        if (view.getUrl() != null && view.getUrl().equalsIgnoreCase(mHomeUrl)) {
            mHome.setAlpha(disable);
            mHome.setEnabled(false);
        } else {
            mHome.setAlpha(enable);
            mHome.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16) {
                    changGoForwardButton(mWebView);
                }
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}

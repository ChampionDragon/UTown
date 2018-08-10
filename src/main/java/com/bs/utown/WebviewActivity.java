package com.bs.utown;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.Logs;

/**
 * Description: 网址类
 * AUTHOR: Champion Dragon
 * created at 2018/6/23
 **/
public class WebviewActivity extends BaseActivity {
    private WebView webView;
    private String urlStr;
    private ProgressBar mProgressBar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_webview);
        initView();
        initWebview();
    }

    private void initWebview() {
        webView.loadUrl(urlStr);
        // 优先使用缓存
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 不使用缓存
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 启用支持javascript.设置true时，会提醒可能造成XSS漏洞
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        webView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        webView.getSettings().setDomStorageEnabled(true);//DOM Storage加载不出Html5网页的解决方法


        webView.setWebViewClient(new WebViewClient() {
            //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Logs.d("web76   " + newProgress);
                if (newProgress == 100) {
                    // 网页加载完成
                    mProgressBar.setVisibility(View.GONE);//加载完网页进度条消失

                } else {
                    // 加载中
                    mProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressBar.setProgress(newProgress);//设置进度值
                }

            }
        });
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.webview_tv);
        webView = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.webview_pb);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tv.setText(bundle.getString(SpKey.webTv));
            urlStr = bundle.getString(SpKey.webUrl);
        }
        findViewById(R.id.back_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.finishActivity();
            }
        });
    }


    /*
   * 如果希望浏览的网页后退而不是退出浏览器，需要WebView覆盖URL加载，让它自动生成历史访问记录，那样就可以通过前进或后退访问已访问过的站点。
   */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回上一页面
                return true;
            } else {
                baseapp.finishActivity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}

package com.example.mvpdemo.ui.act;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;

import com.example.mvpdemo.R;
import com.example.mvpdemo.utils.TitleBar;
import com.gyf.immersionbar.ImmersionBar;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebActivity extends FragmentActivity {

    private WebView webView;
    private String url;
    private ProgressBar progressBar;
    private WebSettings webSettings;
    private TitleBar titleBar;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        url = getIntent().getStringExtra("url");
        initView();
        overridePendingTransition(0, R.anim.activity_close);

    }

    private void initView() {
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        view = findViewById(R.id.viewImmer);

        ImmersionBar
                .with(this)
                .statusBarView(view)
                .init();

        titleBar = findViewById(R.id.titleBar);
        titleBar.setTitleColor(Color.RED);
        //加载url
        if (url.startsWith("http") || url.startsWith("https")) {
            webView.loadUrl(url);
        }

        //5.1以上默认禁止了https和http混用 这是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置本页面打开链接
        webView.setWebViewClient(new WebViewClient());
    }

    /**
     * 返回键回退页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //活跃状态
        webView.onResume();
        webView.resumeTimers();

        webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //和进度条联动
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title != null || title.length() != 0) {
                    titleBar.setTitle(title);
                    titleBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
        webSettings.setJavaScriptEnabled(false);

    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            //清楚缓存&历史记录
            webView.clearCache(true);
            webView.clearHistory();
            //得到父布局,移除webview   然后销毁
            ViewGroup parent = (LinearLayout) webView.getParent();
            parent.removeView(webView);
            webView.destroy();
            webView = null;

        }
        super.onDestroy();
    }
}

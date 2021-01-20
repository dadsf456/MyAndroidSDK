package com.example.myandroidsdk.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by fxb on 2020/8/13.
 */
public class TermsWebView extends WebView {
    public TermsWebView(Context context) {
        this(context, null);
    }

    public TermsWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TermsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        getSettings().setDomStorageEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return false;
            }
        });
    }

    public void loadHtml(String html) {
        String urlHtmlHeads = "<header><meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\"><style>img{height: auto; width: auto; max-width:100%;}</style> </header>";

        loadDataWithBaseURL(null, urlHtmlHeads + html, "text/html", "UTF-8", null);
    }

    ScrollInterface mScrollInterface;

    // 复写 onScrollChanged 方法，用来判断触发判断的时机
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollInterface != null) {
            mScrollInterface.onSChanged(l, t, oldl, oldt);
        }
    }

    // 定义接口
    public void setOnCustomScrollChangeListener(ScrollInterface mInterface) {
        mScrollInterface = mInterface;
    }

    public interface ScrollInterface {
        void onSChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}

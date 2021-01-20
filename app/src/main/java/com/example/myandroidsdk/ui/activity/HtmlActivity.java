package com.example.myandroidsdk.ui.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.WebPage;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.example.myandroidsdk.ui.widget.TermsWebView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.ComplexView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fxb on 2020/7/27.
 */
public class HtmlActivity extends BaseSecondActivity implements OnRefreshListener {
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.mWebView)
    TermsWebView mWebView;
    @BindView(R.id.cv_confirm)
    ComplexView cv_confirm;

    private boolean isShowAgreeButton;//是否显示同意协议按钮

    private String api;
    private boolean isUpgrade;//是否用户升级协议

    @Override
    protected String getBarTitleText() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected int createContent() {
        return R.layout.activity_html;
    }

    @Override
    protected void initData() {
        isShowAgreeButton = getIntent().getBooleanExtra("isShowAgree", false);
        api = getIntent().getStringExtra("api");
        isUpgrade = getIntent().getBooleanExtra("isUpgrade", false);
    }

    @Override
    protected void initView() {
        mWebView.setOnCustomScrollChangeListener((scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!mWebView.canScrollVertically(1)) {
                cv_confirm.setEnabled(true); // button 可点击
            } else
                cv_confirm.setEnabled(false);
        });
        //协议文本不足一屏时
        if (!mWebView.canScrollVertically(1)) {
            cv_confirm.setEnabled(true); // button 可点击
        } else
            cv_confirm.setEnabled(false);

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        Map<String, Object> params = new HashMap<>();
        params.put("r", api);
        if (isUpgrade)
            params.put("level", getIntent().getIntExtra("level", 0));

        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getWebPage(params)
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::getData)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<WebPage>() {
                    @Override
                    public void onSuccess(WebPage data) {
                        mRefreshLayout.finishRefresh();

                        if (isShowAgreeButton)
                            cv_confirm.setVisibility(View.VISIBLE);
                        mWebView.loadHtml(data.content);
                    }

                    @Override
                    public void onFailed(String message) {
                        mRefreshLayout.finishRefresh();
                        showToast(message);
                    }
                });
    }

    @OnClick(R.id.cv_confirm)
    public void onClickConfirm() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}

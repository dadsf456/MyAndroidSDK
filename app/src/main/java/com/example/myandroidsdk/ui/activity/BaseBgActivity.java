package com.example.myandroidsdk.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myandroidsdk.R;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseActivity;

/**
 * Created by fxb on 2020/7/27.
 */
public abstract class BaseBgActivity extends BaseActivity {
    protected View mStatusBar;
    protected TextView tv_bar_title;
    protected FrameLayout fl_container;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_bg;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mStatusBar = findViewById(R.id.mStatusBar);
        tv_bar_title = findViewById(R.id.tv_bar_title);
        fl_container = findViewById(R.id.fl_container);

        View content = LayoutInflater.from(this).inflate(createContent(), fl_container, false);
        fl_container.addView(content);

        TextView tv_title = content.findViewById(R.id.tv_title);
        if (tv_title != null)
            tv_title.setText(getBarTitleText());
    }

    protected abstract String getBarTitleText();

    protected abstract int createContent();

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarView(mStatusBar)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarDarkIcon(true, 0.2f)
                .autoStatusBarDarkModeEnable(true, 0.2f)
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .init();
    }
}

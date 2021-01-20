package com.example.myandroidsdk.ui.fragment;


import android.view.View;

import com.example.myandroidsdk.R;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseFragment;

import butterknife.BindView;

/**
 * 作者：Created by dadsf456 on 2020-11-25 18:23
 * 邮箱：
 * 描述：
 */
public class MainFragment3 extends BaseFragment {

    @BindView(R.id.mStatusBar)
    View mStatusBar;

    @Override
    protected void onFragmentVisibleChange(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            ImmersionBar.with(this)
                    .statusBarView(mStatusBar)
                    .statusBarDarkFont(true, 0.2f)
                    .navigationBarDarkIcon(true, 0.2f)
                    .autoStatusBarDarkModeEnable(true, 0.2f)
                    .autoNavigationBarDarkModeEnable(true, 0.2f)
                    .init();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_03;
    }

    @Override
    protected void initialize() {

    }
}

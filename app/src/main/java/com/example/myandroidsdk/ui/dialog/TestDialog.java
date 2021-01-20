package com.example.myandroidsdk.ui.dialog;

import com.example.myandroidsdk.R;

/**
 * 作者：Created by dadsf456 on 2020-12-29 15:14
 * 邮箱：
 * 描述：
 */
public class TestDialog extends BaseDialogFragment {
    @Override
    protected int createViewByLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initDialogFragment() {

    }
    @Override
    protected boolean getOutsideFlag() {
        return false;
    }
}

package com.example.myandroidsdk.ui.activity;

import android.view.View;

import com.example.myandroidsdk.R;
import com.tool.cs.common.utils.MyToast;
import com.tool.cs.common.widget.ComplexView;

import butterknife.BindView;
import butterknife.OnClick;

public class ComplexViewActivity extends BaseSecondActivity {


    @BindView(R.id.tv__00)
    ComplexView cv00;
    @BindView(R.id.cv_01)
    ComplexView cv01;
    @BindView(R.id.cv_02)
    ComplexView cv02;

    @Override
    protected String getBarTitleText() {
        return "ComplexViewDemo";
    }

    @Override
    protected int createContent() {
        return R.layout.activity_complex_view;
    }

    @Override
    protected void initView() {
        super.initView();
    }



    @OnClick({R.id.cv_01, R.id.cv_02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_01:
                MyToast.showToast(this,"我点击了complex");
                break;
            case R.id.cv_02:
                break;
        }
    }
}

package com.example.myandroidsdk.ui.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myandroidsdk.R;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseActivity;

/**
 * Created by fxb on 2020/6/16.
 */
public abstract class BaseSecondActivity extends BaseActivity {
    protected TextView tv_bar_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        tv_bar_title = findViewById(R.id.tv_bar_title);
        TextView tv_bar_text_btn = findViewById(R.id.tv_bar_text_btn);
        FrameLayout fl_container = findViewById(R.id.fl_container);

        String text = getRightBtnText();
        if (!TextUtils.isEmpty(text)) {
            tv_bar_text_btn.setText(text);
            tv_bar_text_btn.setVisibility(View.VISIBLE);
            tv_bar_text_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickRightBtnText();
                }
            });
        }

        LinearLayout ll_right = findViewById(R.id.ll_right);
        int[] ids = getRightBtnResIds();
        if (ids != null && ids.length <= 3) {
            for (int i = ids.length - 1; i >= 0; i--) {
                int resId = ids[i];
                if (resId != 0) {
                    ImageButton btn = (ImageButton) ll_right.getChildAt(i);
                    btn.setVisibility(View.VISIBLE);
                    btn.setImageResource(resId);
                }
            }
        }
        tv_bar_title.setText(getBarTitleText());

        View content = LayoutInflater.from(this).inflate(createContent(), fl_container, false);
        fl_container.addView(content);

    }

    /**
     * title右边的text文字
     *
     * @return
     */
    protected String getRightBtnText() {
        return "";
    }

    /**
     * title右边的text点击事件
     */
    protected void clickRightBtnText() {

    }

    protected int[] getRightBtnResIds() {
        return null;
    }

    /**
     * title中间的text
     */
    protected abstract String getBarTitleText();

    protected abstract int createContent();

    @Override
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorHighlight)
                .navigationBarColor(R.color.colorHighlight)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarDarkIcon(true, 0.2f)
                .autoStatusBarDarkModeEnable(true, 0.2f)
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .init();
    }
}

package com.tool.cs.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by *** on 2019/7/28.
 * 禁用左右滑动的ViewPager
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;// 不拦截子控件的事件
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;// 消费事件 不往上回传
    }

    @Override
    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        return false;
    }
}

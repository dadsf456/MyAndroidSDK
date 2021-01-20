package com.tool.cs.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by weiyang on 2017/12/24.
 * 底部导航栏控件
 */
public class BottomNavigationBar extends LinearLayout {
    private int mCheckedTab = NO_ID;
    private BottomNavigationTab currentTab;
    private SparseArray<BottomNavigationTab> barSparseArray;//用来存放底部栏Tab的集合

    public BottomNavigationBar(Context context) {
        super(context);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        barSparseArray = new SparseArray<>();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof BottomNavigationTab) {
                BottomNavigationTab tab = (BottomNavigationTab) childAt;
                tab.setTag(barSparseArray.size());
                tab.setOnClickListener(v -> {
                    int tagIndex = (int) v.getTag();
                    if (mCheckedTab != tagIndex) {
                        mCheckedTab = tagIndex;
                        currentTab.setChecked(false);
                        ((BottomNavigationTab) v).setChecked(true);
                        if (onTabSelectListener != null) {
                            onTabSelectListener.selectTab(tagIndex);
                        }
                        currentTab = (BottomNavigationTab) v;
                    }
                });
                barSparseArray.put(barSparseArray.size(), tab);
                if (barSparseArray.size() == 1) {
                    currentTab = tab;
                    currentTab.setChecked(true);
                }
            }
        }
    }

    public void select(int index) {
        BottomNavigationTab tabAt = getTabAt(index);
        if (currentTab != tabAt) {
            currentTab.setChecked(false);
            tabAt.setChecked(true);

            currentTab = tabAt;
            mCheckedTab = index;
            if (onTabSelectListener != null) {
                onTabSelectListener.selectTab(index);
            }
        }
    }

    public void removeTabAt(int i) {
        barSparseArray.remove(i);

        removeViewAt(i);
    }

    public BottomNavigationTab getTabAt(int i) {
        return barSparseArray.get(i);
    }

    private OnTabSelectListener onTabSelectListener;

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void selectTab(int index);
    }
}

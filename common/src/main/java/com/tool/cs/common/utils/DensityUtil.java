package com.tool.cs.common.utils;

import android.content.Context;

/**
 * Created by fxb on 2020-03-19.
 */
public class DensityUtil {
    static public int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    static public int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}

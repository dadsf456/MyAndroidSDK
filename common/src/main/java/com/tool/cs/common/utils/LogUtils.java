package com.tool.cs.common.utils;

import android.util.Log;

/**
 * Created by *** on 2019/8/2.
 * 日志统一管理类
 */
public class LogUtils {
    private static final String DEFAULT_TAG = "LogUtils";
    public static boolean isDebug = true;

    private LogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 下面是传入自定义tag的函数
    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(DEFAULT_TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(DEFAULT_TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(DEFAULT_TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(DEFAULT_TAG, msg);
    }


    // 下面是传入Object的函数
    public static void i(Object obj, String msg) {
        if (isDebug)
            Log.i(obj.getClass().getSimpleName(), msg);
    }

    public static void e(Object obj, String msg) {
        if (isDebug)
            Log.e(obj.getClass().getSimpleName(), msg);
    }

    public static void d(Object obj, String msg) {
        if (isDebug)
            Log.d(obj.getClass().getSimpleName(), msg);
    }

    public static void v(Object obj, String msg) {
        if (isDebug)
            Log.v(obj.getClass().getSimpleName(), msg);
    }
}

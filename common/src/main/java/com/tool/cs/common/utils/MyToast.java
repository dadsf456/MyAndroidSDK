package com.tool.cs.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by weiyang on 2016/11/24.
 * 自定义单例吐司
 */
public class MyToast {
    private static final Object SYNC_LOCK = new Object();
    private volatile static Toast toast;
    /**
     * 上下文
     **/
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static Context getContext() {
        return context;
    }

    private static void setContext(Context context) {
        MyToast.context = context.getApplicationContext();
    }

    /**
     * toast的单一实例化,加锁保证toast的唯一性
     */
    @SuppressLint("ShowToast")
    private static void initToastInstance() {
        if (toast == null) {
            synchronized (SYNC_LOCK) {
                if (toast == null) {
                    toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    public static void showToast(Context context, String text) {
        setContext(context);
        if (getContext() != null && text != null) {
            initToastInstance();
            toast.setText(text);
            toast.show();
        }
    }
}

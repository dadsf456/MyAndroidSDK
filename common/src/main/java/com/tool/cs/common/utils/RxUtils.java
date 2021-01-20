package com.tool.cs.common.utils;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    private RxUtils() {
    }

    /**
     * 封装rxjava线程切换
     *
     * @param <T> 实体类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> switchObservableSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T, T> switchFlowableSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleTransformer<T, T> switchSingleSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> LifecycleTransformer<T> bindEvent(Object mView) {
        if (mView instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) mView).bindUntilEvent(ActivityEvent.DESTROY);
        } else if (mView instanceof RxActivity) {
            return ((RxActivity) mView).bindUntilEvent(ActivityEvent.DESTROY);
        } else if (mView instanceof RxFragment) {
            return ((RxFragment) mView).bindUntilEvent(FragmentEvent.DESTROY);
        } else if (mView instanceof RxDialogFragment) {
            return ((RxDialogFragment) mView).bindUntilEvent(FragmentEvent.DESTROY);
        } else {
            return null;
        }
    }
}

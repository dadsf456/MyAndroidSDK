package com.tool.cs.common.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.tool.cs.common.utils.MyToast;

import butterknife.ButterKnife;

/**
 * Created by fxb on 2020/5/25.
 */
public abstract class BaseFragment extends RxFragment implements ImmersionOwner {
    protected String TAG = getClass().getSimpleName();
    private View rootView;
    private boolean isInitialize;//页面初始化的标记

    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        safeInit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
        safeInit();
    }

    /**
     * 布局Id，每个子类需要返回
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 页面初始化，点击事件初始化
     */
    protected abstract void initialize();

    private void safeInit() {
        if (rootView != null) {
            if (getUserVisibleHint()) {
                if (!isInitialize) {
                    initialize();
                    isInitialize = true;
                }
            }
            onFragmentVisibleChange(getUserVisibleHint());
        }
    }

    /**
     * 当前碎片可见变化，一般用于页面可见时重新网络请求
     *
     * @param isVisibleToUser
     */
    protected void onFragmentVisibleChange(boolean isVisibleToUser) {
    }

    protected void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    protected void showToast(String text) {
        MyToast.showToast(getContext(), text);
    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity base = (BaseActivity) getActivity();
            base.startActivity(clz, bundle);
        }
    }

    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(clz, requestCode, null);
    }

    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        /*if (getActivity() instanceof BaseActivity) {
            BaseActivity base = (BaseActivity) getActivity();
            base.startActivityForResult(clz, requestCode, bundle);
        }*/
        Intent intent = new Intent();
        intent.setClass(getContext(), clz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    private final String ASYNC_TAG = "Async_Tag";//异步请求的标记

    public void showProgress(int resId) {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity base = (BaseActivity) getActivity();
            base.showProgress(resId);
        }
    }

    public void hideProgress() {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity base = (BaseActivity) getActivity();
            base.hideProgress();
        }
    }

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }
}

package com.tool.cs.common.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tool.cs.common.AppManager;
import com.tool.cs.common.R;
import com.tool.cs.common.dialog.MyProgressFragment;
import com.tool.cs.common.dialog.MyProgressFragment1;
import com.tool.cs.common.dialog.ProgressFragment;
import com.tool.cs.common.utils.MyToast;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by *** on 2019/7/26.
 * 基类Activity
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private InputMethodManager mInputMethodManager;
    private Unbinder unbinder;
    public Activity activity;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        //设置竖屏Activity
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        //设置View
        setContentView(getLayoutId());
        //绑定控件
        unbinder = ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        //是否支持EventBus
        if (isEventBusEnabled()) {
            EventBus.getDefault().register(this);
        }

        //初始化数据 intent
        initData();
        //view与数据绑定
        initView();
        //设置监听
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        /*if (isImmersionBarEnabled()) {
            ImmersionBar.destroy(this, null);
        }*/
        if (isEventBusEnabled()) {
            EventBus.getDefault().unregister(this);
        }
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().removeActivity(this);
    }

    /**
     * 子类设置布局Id
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();

    /**
     * 是否修改状态栏
     *
     * @return
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void initListener() {
    }

    /**
     * 是否注册EventBus,默认false
     */
    protected boolean isEventBusEnabled() {
        return false;
    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(clz, requestCode, null);
    }

    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    private final String ASYNC_TAG = "Async_Tag";//异步请求的标记

    public void showProgress(int resId) {
        if (getSupportFragmentManager().findFragmentByTag(ASYNC_TAG) == null) {
          //  ProgressFragment.newInstance(resId).show(getSupportFragmentManager(), ASYNC_TAG);
            ProgressFragment.newInstance(resId).showAllowingStateLoss(getSupportFragmentManager(), ASYNC_TAG);
        }
    }

    public void showProgress2( int layout ,int resId) {
        if (getSupportFragmentManager().findFragmentByTag(ASYNC_TAG) == null) {
            MyProgressFragment.newInstance(layout,resId).show(getSupportFragmentManager(), ASYNC_TAG);
        }
    }
    public void showProgress3( int layout ,int resId1,int resId2) {
        if (getSupportFragmentManager().findFragmentByTag(ASYNC_TAG) == null) {
            MyProgressFragment1.newInstance(layout,resId1,resId2).show(getSupportFragmentManager(), ASYNC_TAG);
        }
    }

    public void hideProgress() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ASYNC_TAG);
        if (fragment instanceof ProgressFragment) {
            ((ProgressFragment) fragment).dismissAllowingStateLoss();
        }
    }

    protected void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    protected void showToast(String text) {
        MyToast.showToast(this, text);
    }

    public void onBack(View v) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mInputMethodManager != null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 2);
            localView.clearFocus();
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftKeyBoard(EditText et) {
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if (et != null && this.mInputMethodManager != null) {
            et.requestFocus();
            mInputMethodManager.showSoftInput(et, 0);
        }
    }

    protected void checkPermission(String[] permissions, Action<List<String>> success) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale((context, data, executor) -> executor.execute())//拒绝未勾选不再提示回调
                .onGranted(success)
                .onDenied(data -> {
                    //拒绝并勾选不再提示，提示用户到设置界面去打开权限
                    if (AndPermission.hasAlwaysDeniedPermission(this, data)) {
                        List<String> permissionNames = Permission.transformText(this, permissions);
                        String message = getString(R.string.dlg_content_permission) + "\n" + permissionNames;

                        new AlertDialog.Builder(this)
                                .setTitle(R.string.dlg_title_permission)
                                .setMessage(message)
                                .setCancelable(false)
                                .setPositiveButton(R.string.text_setting, (dialog, which) -> {
                                    AndPermission.with(this)
                                            .runtime()
                                            .setting()
                                            .onComeback(() -> {
                                            })
                                            .start();
                                })
                                .setNegativeButton(R.string.text_cancel, (dialog, which) -> {
                                })
                                .show();
                    }
                })
                .start();
    }
}

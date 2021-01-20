package com.example.myandroidsdk.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tool.cs.common.base.BaseActivity;
import com.tool.cs.common.utils.MyToast;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import butterknife.ButterKnife;

/**
 * Created by weiyang on 2019/4/5.
 */
public abstract class BaseDialogFragment extends RxDialogFragment {
    protected final String TAG = getClass().getSimpleName();
    protected View rootView;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        //或者在onCreateView中设置以下代码
        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            if (getActivity() != null)
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                //重新设置布局参数
                resetLayoutParams(params);
                //设置宽度是屏幕宽度的%多少
                if (isFullScreen()) {
                    window.setLayout(dm.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
                } else {
                    window.setLayout((int) (dm.widthPixels * getWidthScale()), ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                //支持圆角
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }

            //点击外部是否消失
            dialog.setCanceledOnTouchOutside(getOutsideFlag());
        }
    }

    protected void resetLayoutParams(WindowManager.LayoutParams params) {

    }

    /**
     * 对话框相对屏幕的比例
     */
    protected float getWidthScale() {
        return 0.80f;
    }

    /**
     * 点击外部消失与否
     */
    protected boolean getOutsideFlag() {
        return true;
    }

    /**
     * 是否显示对话框为全屏
     */
    protected boolean isFullScreen() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(createViewByLayoutId(), container, false);
            ButterKnife.bind(this, rootView);

            initDialogFragment();
        }
        return rootView;
    }

    protected abstract int createViewByLayoutId();

    protected abstract void initDialogFragment();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}

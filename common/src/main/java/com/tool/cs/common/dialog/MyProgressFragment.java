package com.tool.cs.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import butterknife.ButterKnife;

/**
 * Created by weiyang on 2018/1/12.
 * 加载中对话框
 */
public class MyProgressFragment extends DialogFragment {
    private static final String ARG_TITLE_ID = "titleId";
    private static final String ARG_MESSAGE_ID = "messageId";
    private static final String ARG_CANCELABLE = "cancelable";
    protected View rootView;
    private static int layout1;
    private Dialog dialog;

    public static MyProgressFragment newInstance(int layout, @StringRes int messageId) {
        layout1 = layout;
        MyProgressFragment pf = new MyProgressFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE_ID, 0);
        args.putInt(ARG_MESSAGE_ID, messageId);
        args.putBoolean(ARG_CANCELABLE, false);
        pf.setArguments(args);
        return pf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            // 设置dialog布局内容，这里布局的初始化代码就不写出来了
            rootView = inflater.inflate(layout1, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
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
                   // window.setLayout((int) (dm.widthPixels * getWidthScale()), ViewGroup.LayoutParams.WRAP_CONTENT);
                    window.setLayout((int) (dm.widthPixels * getWidthScale()),(int) (dm.widthPixels * getWidthScale()));
                }
                //支持圆角
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }
            //点击外部是否消失
            dialog.setCanceledOnTouchOutside(getOutsideFlag());
        }
    }

    protected float getWidthScale() {
        return 0.3f;
    }

    protected void resetLayoutParams(WindowManager.LayoutParams params) {
     //   params.gravity = Gravity.BOTTOM;
      //  params.windowAnimations = R.style.down_top_anim;
    }
    /**
     * 是否显示对话框为全屏
     */
    protected boolean isFullScreen() {
        return false;
    }
    /**
     * 点击外部消失与否
     */
    protected boolean getOutsideFlag() {
        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCancelListener) {
            mListener = (OnCancelListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        if (mListener != null) {
            mListener.onCancel(this);
        }
    }

    private OnCancelListener mListener = null;

    public interface OnCancelListener {
        void onCancel(MyProgressFragment pf);
    }
}

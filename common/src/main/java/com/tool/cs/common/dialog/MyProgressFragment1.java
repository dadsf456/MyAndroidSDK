package com.tool.cs.common.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.tool.cs.common.utils.ScreenUtils;

import butterknife.ButterKnife;

/**
 * 作者：Created by dadsf456 on 2021-01-06 22:27
 * 邮箱：
 * 描述：
 */
public class MyProgressFragment1 extends DialogFragment {
    private static final String ARG_TITLE_ID = "titleId";
    private static final String ARG_MESSAGE_ID = "messageId";
    private static final String ARG_CANCELABLE = "cancelable";
    private static MyProgressFragment1 fragment1;
    private View view;
    private static int layout1;
    private TextView temp;

    public static synchronized MyProgressFragment1 newInstance(int layout, int string1,int string2) {
        // if (fragment1 == null) {
        layout1 = layout;
        fragment1 = new MyProgressFragment1();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TITLE_ID, string1);
        bundle.putInt(ARG_MESSAGE_ID, string2);
        bundle.putBoolean(ARG_CANCELABLE, false);
        //注意这里设置Bundle
        fragment1.setArguments(bundle);

        //   }
        return fragment1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        int titleId = args.getInt(ARG_TITLE_ID);
        int messageId = args.getInt(ARG_MESSAGE_ID);
        boolean cancelable = args.getBoolean(ARG_CANCELABLE);
        CharSequence text1 = getActivity().getText(titleId);
        CharSequence text2 = getActivity().getText(messageId);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams attributes = getDialog().getWindow().getAttributes();
            attributes.width = (int) (ScreenUtils.getScreenWidth(getContext()) * getWidthScale());
            attributes.height = (int) (ScreenUtils.getScreenWidth(getContext()) * getWidthScale());
            getDialog().getWindow().setAttributes(attributes);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            DisplayMetrics dm = new DisplayMetrics();
//            if (getActivity() != null)
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//            Window window = dialog.getWindow();
//            if (window != null) {
//                WindowManager.LayoutParams params = window.getAttributes();
//                //重新设置布局参数
//               // resetLayoutParams(params);
//                //设置宽度是屏幕宽度的%多少
//                if (isFullScreen()) {
//                    window.setLayout(dm.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
//                } else {
//                    // window.setLayout((int) (dm.widthPixels * getWidthScale()), ViewGroup.LayoutParams
//                    .WRAP_CONTENT);
//                    window.setLayout((int) (dm.widthPixels * getWidthScale()),(int) (dm.widthPixels * getWidthScale
//                    ()));
//                }
//                //支持圆角
//                window.setBackgroundDrawableResource(android.R.color.transparent);
//            }
//            //点击外部是否消失
//            dialog.setCanceledOnTouchOutside(getOutsideFlag());
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(layout1, container, false);
            ButterKnife.bind(this, view);
        }
        return view;

//        if (view == null) {
//            // 设置dialog布局内容，这里布局的初始化代码就不写出来了
//            view = inflater.inflate(layout1, container, false);
//            ButterKnife.bind(this, view);
//        }
//        return view;
    }

    protected float getWidthScale() {
        return 0.3f;
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

}

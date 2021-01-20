package com.tool.cs.common.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.Field;

/**
 * Created by weiyang on 2018/1/12.
 * 加载中对话框
 */
public class ProgressFragment extends DialogFragment {
    private static final String ARG_TITLE_ID = "titleId";
    private static final String ARG_MESSAGE_ID = "messageId";
    private static final String ARG_CANCELABLE = "cancelable";

    public static ProgressFragment newInstance(@StringRes int messageId) {
        ProgressFragment pf = new ProgressFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE_ID, 0);
        args.putInt(ARG_MESSAGE_ID, messageId);
        args.putBoolean(ARG_CANCELABLE, false);
        pf.setArguments(args);
        return pf;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int titleId = args.getInt(ARG_TITLE_ID);
        int messageId = args.getInt(ARG_MESSAGE_ID);
        boolean cancelable = args.getBoolean(ARG_CANCELABLE);

        ProgressDialog dialog = new ProgressDialog(getActivity());
        if (titleId != 0) {
            dialog.setTitle(titleId);
        }

        dialog.setMessage(getActivity().getText(messageId));
        setCancelable(cancelable);
        dialog.setIndeterminate(true);

        dialog.setCanceledOnTouchOutside(false);
        return dialog;
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
        void onCancel(ProgressFragment pf);
    }

    //如设置个人头像的时候
    //在一个activity中，有定时任务，到时间后弹出一个DialogFragment，如果弹出的时候，已经跳转到了下一个activity，就会报错：Can not perform this action after
    // onSaveInstanceState。
    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        try {
            Field dismissed = DialogFragment.class.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(this, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field shown = DialogFragment.class.getDeclaredField("mShownByMe");
            shown.setAccessible(true);
            shown.set(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try{
            super.show(manager,tag);
        }catch (IllegalStateException ignore){
        }
    }
}

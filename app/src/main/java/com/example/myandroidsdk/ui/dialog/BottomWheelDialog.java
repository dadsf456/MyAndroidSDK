package com.example.myandroidsdk.ui.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;
import com.example.myandroidsdk.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by weiyang on 2019/4/29.
 * 底部滚动View
 */
public class BottomWheelDialog extends BaseDialogFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.mWheelView)
    WheelView mWheelView;

    private WheelAdapter<String> mAdapter = new WheelAdapter<String>() {

        @Override
        public int getItemsCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public String getItem(int index) {
            return mData.get(index);
        }

        @Override
        public int indexOf(String o) {
            for (int i = 0; i < mData.size(); i++) {
                String t = mData.get(i);
                if (o.equals(t))
                    return i;
            }
            return 0;
        }
    };
    /**
     * wheelView 显示数据
     */
    private List<String> mData;

    public void setData(List<String> mData) {
        this.mData = mData;
    }

    @Override
    protected int createViewByLayoutId() {
        return R.layout.dialog_bottom_wheel;
    }

    @Override
    protected void resetLayoutParams(WindowManager.LayoutParams params) {
        super.resetLayoutParams(params);
        params.gravity = Gravity.BOTTOM;
        params.windowAnimations = R.style.AnimUp;
    }

    @Override
    protected float getWidthScale() {
        return 1.0f;
    }

    @Override
    protected void initDialogFragment() {
        Bundle args = getArguments();
        if (args != null) {
            int titleId = args.getInt("titleId");
            int cancelId = args.getInt("cancelId");
            int confirmId = args.getInt("confirmId");

            if (titleId != 0)
                tv_title.setText(titleId);
            if (cancelId != 0)
                tv_cancel.setText(cancelId);
            if (confirmId != 0)
                tv_confirm.setText(confirmId);

            int position = args.getInt("position");
            mWheelView.setAdapter(mAdapter);
            mWheelView.setCurrentItem(position);
            mWheelView.setCyclic(false);
            mWheelView.setTypeface(Typeface.DEFAULT);

            String titleText = args.getString("titleText");
            String cancelText = args.getString("cancelText");
            String confirmText = args.getString("confirmText");

            if (!TextUtils.isEmpty(titleText))
                tv_title.setText(titleText);
            if (!TextUtils.isEmpty(cancelText))
                tv_cancel.setText(cancelText);
            if (!TextUtils.isEmpty(confirmText))
                tv_confirm.setText(confirmText);

            boolean isCancelGone = args.getBoolean("isCancelGone");
            if (isCancelGone)
                tv_cancel.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_cancel)
    public void onClickCancel() {
        if (onCancelListener != null)
            onCancelListener.onCancel();

        dismiss();
    }

    @OnClick(R.id.tv_confirm)
    public void onClickConfirm() {
        if (onConfirmListener != null)
            onConfirmListener.onConfirm(mWheelView.getCurrentItem());

        dismiss();
    }

    private OnCancelListener onCancelListener;

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public interface OnCancelListener {
        void onCancel();
    }

    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnConfirmListener {
        void onConfirm(int position);
    }

    public static class Builder {
        Bundle args;
        List<String> data;
        OnCancelListener onCancelListener;
        OnConfirmListener onConfirmListener;

        public Builder() {
            args = new Bundle();
        }

        public Builder setTitleId(@StringRes int resId) {
            args.putInt("titleId", resId);
            return this;
        }

        public Builder setTitleText(String title) {
            args.putString("titleText", title);
            return this;
        }

        public Builder setCancelId(@StringRes int resId) {
            args.putInt("cancelId", resId);
            return this;
        }

        public Builder setCancelText(String text) {
            args.putString("cancelText", text);
            return this;
        }

        public Builder setCancelGone(boolean isGone) {
            args.putBoolean("isCancelGone", isGone);
            return this;
        }

        public Builder setConfirmId(@StringRes int resId) {
            args.putInt("confirmId", resId);
            return this;
        }

        public Builder setConfirmText(String text) {
            args.putString("confirmText", text);
            return this;
        }

        public Builder setData(List<String> data) {
            this.data = data;
            return this;
        }

        public Builder setPosition(int position) {
            args.putInt("position", position);
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean flag) {
            args.putBoolean("outsideFlag", flag);
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }

        public BottomWheelDialog create() {
            BottomWheelDialog dialog = new BottomWheelDialog();
            dialog.setArguments(args);
            dialog.setData(data);
            dialog.setOnCancelListener(onCancelListener);
            dialog.setOnConfirmListener(onConfirmListener);
            return dialog;
        }
    }
}

package com.example.myandroidsdk.ui.dialog;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.myandroidsdk.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by weiyang on 2019/4/8.
 * 确认对话框
 */
public class CommonDialog extends BaseDialogFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    @Override
    protected int createViewByLayoutId() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected boolean getOutsideFlag() {
        Bundle args = getArguments();
        if (args != null)
            return args.getBoolean("outsideFlag", true);
        return super.getOutsideFlag();
    }

    @Override
    protected void initDialogFragment() {
        Bundle args = getArguments();
        if (args != null) {
            tv_content.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件

            int titleId = args.getInt("titleId");
            int contentId = args.getInt("contentId");
            int cancelId = args.getInt("cancelId");
            int confirmId = args.getInt("confirmId");
            int contentTextColor = args.getInt("contentTextColor");
            int cancelTextColor = args.getInt("cancelTextColor");
            int confirmTextColor = args.getInt("confirmTextColor");
            int spacing = args.getInt("lineSpacing");

            if (titleId != 0)
                tv_title.setText(titleId);
            if (contentId != 0)
                tv_content.setText(contentId);
            if (cancelId != 0)
                tv_cancel.setText(cancelId);
            if (confirmId != 0)
                tv_confirm.setText(confirmId);
            if (contentTextColor != 0)
                tv_content.setTextColor(contentTextColor);
            if (cancelTextColor != 0)
                tv_cancel.setTextColor(cancelTextColor);
            if (confirmTextColor != 0)
                tv_confirm.setTextColor(confirmTextColor);
            if (spacing != 0)
                tv_content.setLineSpacing(spacing, 1f);

            String titleText = args.getString("titleText");
            CharSequence contentText = args.getCharSequence("contentText");
            String cancelText = args.getString("cancelText");
            String confirmText = args.getString("confirmText");

            if (!TextUtils.isEmpty(titleText))
                tv_title.setText(titleText);
            if (!TextUtils.isEmpty(contentText))
                tv_content.setText(contentText);
            if (!TextUtils.isEmpty(cancelText))
                tv_cancel.setText(cancelText);
            if (!TextUtils.isEmpty(confirmText))
                tv_confirm.setText(confirmText);

            if (TextUtils.isEmpty(tv_title.getText()))
                tv_title.setVisibility(View.GONE);

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
            onConfirmListener.onConfirm();

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
        void onConfirm();
    }

    public static class Builder {
        Bundle args;
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

        public Builder setContentId(@StringRes int resId) {
            args.putInt("contentId", resId);
            return this;
        }

        public Builder setContentText(CharSequence text) {
            setContentText(text, 0);
            return this;
        }

        public Builder setContentText(CharSequence text, @ColorInt int color) {
            args.putCharSequence("contentText", text);
            if (color != 0)
                args.putInt("contentTextColor", color);
            return this;
        }

        public Builder setLineSpacing(int spacing) {
            args.putInt("lineSpacing", spacing);
            return this;
        }

        public Builder setCancelId(@StringRes int resId) {
            args.putInt("cancelId", resId);
            return this;
        }

        public Builder setCancelText(CharSequence text) {
            setCancelText(text, 0);
            return this;
        }

        public Builder setCancelText(CharSequence text, @ColorInt int color) {
            args.putCharSequence("cancelText", text);
            if (color != 0)
                args.putInt("cancelTextColor", color);
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

        public Builder setConfirmText(CharSequence text) {
            setConfirmText(text, 0);
            return this;
        }

        public Builder setConfirmText(CharSequence text, @ColorInt int color) {
            args.putCharSequence("confirmText", text);
            if (color != 0)
                args.putInt("confirmTextColor", color);
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

        public CommonDialog create() {
            CommonDialog dialog = new CommonDialog();
            dialog.setArguments(args);
            dialog.setOnCancelListener(onCancelListener);
            dialog.setOnConfirmListener(onConfirmListener);
            return dialog;
        }
    }
}

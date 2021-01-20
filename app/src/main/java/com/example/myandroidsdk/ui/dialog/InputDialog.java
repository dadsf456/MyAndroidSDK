package com.example.myandroidsdk.ui.dialog;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myandroidsdk.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by weiyang on 2019/4/8.
 * 输入对话框
 */
public class InputDialog extends BaseDialogFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_sub_title)
    TextView tv_sub_title;
    @BindView(R.id.et_input_text)
    EditText et_input_text;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    /**
     * 原文
     */
    private String originalText;
    /**
     * 非空校验
     */
    private boolean isCheckEmpty = true;
    /**
     * 非空提示
     */
    private String promptText;

    @Override
    protected boolean getOutsideFlag() {
        Bundle args = getArguments();
        if (args != null)
            return args.getBoolean("outsideFlag");
        return super.getOutsideFlag();
    }

    @Override
    protected int createViewByLayoutId() {
        return R.layout.dialog_input;
    }

    @Override
    protected void initDialogFragment() {
        Bundle args = getArguments();
        if (args != null) {
            isCheckEmpty = args.getBoolean("isCheckEmpty");
            promptText = args.getString("promptText");

            int titleId = args.getInt("titleId");
            int subTitleId = args.getInt("subTitleId");
            int hintId = args.getInt("hintId");
            int contentId = args.getInt("contentId");
            int cancelId = args.getInt("cancelId");
            int confirmId = args.getInt("confirmId");
            int cancelTextColor = args.getInt("cancelTextColor");
            int confirmTextColor = args.getInt("confirmTextColor");

            if (titleId != 0)
                tv_title.setText(titleId);
            if (subTitleId != 0)
                tv_sub_title.setText(subTitleId);
            if (hintId != 0)
                et_input_text.setHint(hintId);
            if (contentId != 0)
                et_input_text.setText(contentId);
            if (cancelId != 0)
                tv_cancel.setText(cancelId);
            if (confirmId != 0)
                tv_confirm.setText(confirmId);
            if (cancelTextColor != 0)
                tv_cancel.setTextColor(cancelTextColor);
            if (confirmTextColor != 0)
                tv_confirm.setTextColor(confirmTextColor);

            String titleText = args.getString("titleText");
            String subTitleText = args.getString("subTitleText");
            String hintText = args.getString("hintText");
            originalText = args.getString("contentText");
            String cancelText = args.getString("cancelText");
            String confirmText = args.getString("confirmText");
            if (!TextUtils.isEmpty(titleText))
                tv_title.setText(titleText);
            if (!TextUtils.isEmpty(subTitleText))
                tv_sub_title.setText(subTitleText);
            if (!TextUtils.isEmpty(hintText))
                et_input_text.setHint(hintText);
            if (!TextUtils.isEmpty(originalText)) {
                et_input_text.setText(originalText);
                et_input_text.setSelection(originalText.length());
            }
            if (!TextUtils.isEmpty(cancelText))
                tv_cancel.setText(cancelText);
            if (!TextUtils.isEmpty(confirmText))
                tv_confirm.setText(confirmText);

            if (TextUtils.isEmpty(tv_title.getText()))
                tv_title.setVisibility(View.GONE);
            if (TextUtils.isEmpty(tv_sub_title.getText()))
                tv_sub_title.setVisibility(View.GONE);
        }
    }

    @OnTextChanged(R.id.et_input_text)
    public void onInputTextChange() {

    }

    @OnClick(R.id.tv_cancel)
    public void onClickCancel() {
        dismiss();
    }

    @OnClick(R.id.tv_confirm)
    public void onClickConfirm() {
        String inputText = et_input_text.getText().toString().trim();
        if (isCheckEmpty && TextUtils.isEmpty(inputText)) {
            Toast.makeText(getContext(), promptText, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.equals(originalText, inputText)) {
            dismiss();
            return;
        }

        if (onConfirmListener != null)
            onConfirmListener.onConfirm(inputText);

        dismiss();
    }

    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnConfirmListener {
        void onConfirm(String str);
    }

    public static class Builder {
        Bundle args;
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

        public Builder setSubTitleId(@StringRes int resId) {
            args.putInt("subTitleId", resId);
            return this;
        }

        public Builder setSubTitleText(String title) {
            args.putString("subTitleText", title);
            return this;
        }

        public Builder setHintId(@StringRes int resId) {
            args.putInt("hintId", resId);
            return this;
        }

        public Builder setHintText(String text) {
            args.putString("hintText", text);
            return this;
        }

        public Builder setContentId(@StringRes int resId) {
            args.putInt("contentId", resId);
            return this;
        }

        public Builder setContentText(String text) {
            args.putString("contentText", text);
            return this;
        }

        public Builder setCheckEmpty(boolean isCheckEmpty) {
            args.putBoolean("isCheckEmpty", isCheckEmpty);
            return this;
        }

        public Builder setEmptyPrompt(@StringRes int resId) {
            args.putInt("promptText", resId);
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

        public Builder setCancelText(CharSequence text, @ColorInt int color) {
            args.putCharSequence("cancelText", text);
            if (color != 0)
                args.putInt("cancelTextColor", color);
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

        public Builder setConfirmText(CharSequence text, @ColorInt int color) {
            args.putCharSequence("confirmText", text);
            if (color != 0)
                args.putInt("confirmTextColor", color);
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean flag) {
            args.putBoolean("outsideFlag", flag);
            return this;
        }

        public InputDialog create() {
            InputDialog dialog = new InputDialog();
            dialog.setArguments(args);
            dialog.setOnConfirmListener(onConfirmListener);
            return dialog;
        }

    }
}

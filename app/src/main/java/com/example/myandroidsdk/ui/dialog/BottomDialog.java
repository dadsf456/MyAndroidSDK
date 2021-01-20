package com.example.myandroidsdk.ui.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.myandroidsdk.R;
import com.tool.cs.common.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiyang on 2019/4/10.
 * 底部弹窗统一封装类
 */
public class BottomDialog extends BaseDialogFragment {
    /**
     * 条目文本strId
     */
    private List<Integer> itemTexts;
    /**
     * 是否高亮显示
     */
    private SparseBooleanArray highLightArray;
    private int normalTextColor = 0xff000000;
    private int highlightTextColor = 0xffff0000;

    public void setItemTexts(List<Integer> itemTexts) {
        this.itemTexts = itemTexts;
    }

    public void setHighLightArray(SparseBooleanArray highLightArray) {
        this.highLightArray = highLightArray;
    }

    @Override
    protected boolean getOutsideFlag() {
        Bundle args = getArguments();
        if (args != null)
            return args.getBoolean("outsideFlag", true);
        return super.getOutsideFlag();
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
    protected int createViewByLayoutId() {
        return R.layout.dialog_bottom;
    }

    @Override
    protected void initDialogFragment() {
        LinearLayout parent = (LinearLayout) rootView;

        parent.addView(new Space(getContext()));
        for (int i = 0; i < itemTexts.size(); i++) {
            final int position = i;

            parent.addView(createItem(itemTexts.get(i), highLightArray.valueAt(i) ? highlightTextColor : normalTextColor, v -> {
                //if (getOutsideFlag())//点击外部不消失,点击条目也不消失
                dismiss();
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position);
            }));
        }

        //分割最后一个Item与Cancel Item
        Space space = new Space(getContext());
        space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                DensityUtil.dp2px(getActivity(), 5)));
        parent.addView(space);

        parent.addView(createItem(R.string.text_cancel, 0xff333333, v -> {
            dismiss();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemTexts.size());
            }
        }));
    }

    private TextView createItem(@StringRes int resId, int color, View.OnClickListener listener) {
        TextView item = new TextView(getContext());
        item.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtil.dp2px(getActivity(), 45)));
        item.setGravity(Gravity.CENTER);
        item.setTextSize(17);
        item.setBackgroundColor(Color.WHITE);
        item.setTextColor(color);
        item.setText(resId);
        item.setOnClickListener(listener);
        return item;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class Builder {
        List<Integer> itemTexts;
        OnItemClickListener onItemClickListener;
        SparseBooleanArray highLightArray;
        Bundle args;

        public Builder() {
            itemTexts = new ArrayList<>();
            highLightArray = new SparseBooleanArray();
            args = new Bundle();
        }

        public BottomDialog.Builder setCanceledOnTouchOutside(boolean flag) {
            args.putBoolean("outsideFlag", flag);
            return this;
        }

        public Builder addItem(@StringRes int strId) {
            addItem(strId, false);
            return this;
        }

        public Builder addItem(@StringRes int strId, boolean isHighLight) {
            highLightArray.put(itemTexts.size(), isHighLight);
            itemTexts.add(strId);
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public BottomDialog create() {
            BottomDialog dialog = new BottomDialog();
            dialog.setArguments(args);
            dialog.setItemTexts(itemTexts);
            dialog.setHighLightArray(highLightArray);
            dialog.setOnItemClickListener(onItemClickListener);
            return dialog;
        }
    }
}

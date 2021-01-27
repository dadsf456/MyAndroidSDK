package com.example.myandroidsdk.ui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

import com.tool.cs.common.utils.DensityUtil;


/**
 * Created by weiyang on 2019/5/13.
 */
public class SpanTextBuilder {
    private SpannableStringBuilder ssb;

    public SpanTextBuilder() {
        ssb = new SpannableStringBuilder();
    }

    public SpanTextBuilder append(String str) {
        ssb.append(str);
        return this;
    }

    public SpanTextBuilder append(String str, @ColorInt int color) {
        SpannableString ss = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        ss.setSpan(colorSpan, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.append(ss);
        return this;
    }

    public SpanTextBuilder append(Context context, @DrawableRes int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, DensityUtil.dp2px(context, 18), DensityUtil.dp2px(context, 18));
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString ss = new SpannableString(" ");
        ss.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ss);
        return this;
    }

    public SpanTextBuilder append(Context context, @DrawableRes int resId, int size) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, DensityUtil.dp2px(context, size), DensityUtil.dp2px(context, size));
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString ss = new SpannableString(" ");
        ss.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ss);
        return this;
    }

    public SpanTextBuilder appendFormat(String text, String str, @ColorInt int color) {
        String[] split = text.split("%s");
        ssb.append(split[0]);
        append(str, color);
        ssb.append(split[1]);
        return this;
    }

    public SpanTextBuilder addClickableSpan(ClickableSpan span, int start, int end) {
        ssb.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringBuilder get() {
        return ssb;
    }
}

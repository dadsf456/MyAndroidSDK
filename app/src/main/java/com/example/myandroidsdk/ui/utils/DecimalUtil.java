package com.example.myandroidsdk.ui.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by fxb on 2020/7/28.
 */
public class DecimalUtil {
    public static String format_append(String value) {
        try {
            return "￥" + format(value, 2);
        } catch (Exception e) {
            return "￥0.00";
        }
    }

    public static String format_append(double value) {
        try {
            return "￥" + format(value, 2);
        } catch (Exception e) {
            return "￥0.00";
        }
    }

    public static String formatRounding(String value) {
        try {
            return format(value, 0);
        } catch (Exception e) {
            return "0";
        }
    }

    private static String format(String value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    private static String format(double value, int scale) {
        if (value>10000){
            return format(String.valueOf(value/10000), scale)+"万";
        }
        return format(String.valueOf(value), scale);
    }

    //用JAVA自带的函数
    public static boolean isNumericZidai(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}

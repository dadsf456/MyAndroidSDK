package com.tool.cs.common.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by *** on 2019/4/3.
 */
public class LanguageSupportUtil {
    public interface Language {
        // 简体中文
        String SIMPLIFIED_CHINESE = "zh";
        // 英文
        String ENGLISH = "en";
    }

    public static List<LanguageBean> getSupportLanguages() {
        List<LanguageBean> data = new ArrayList<>();
        data.add(new LanguageBean("简体中文", Language.SIMPLIFIED_CHINESE));
        data.add(new LanguageBean("English", Language.ENGLISH));
        return data;
    }

    private static Map<String, Locale> mSupportLanguages = new HashMap<String, Locale>(2) {{
        put(Language.ENGLISH, Locale.ENGLISH);
        put(Language.SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
    }};

    /**
     * 是否支持此语言
     *
     * @param language language
     * @return true:支持 false:不支持
     */
    public static boolean isSupportLanguage(String language) {
        return mSupportLanguages.containsKey(language);
    }

    /**
     * 获取支持语言
     *
     * @param language language
     * @return 支持返回支持语言，不支持返回系统首选语言
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mSupportLanguages.get(language);
        }
        return getSystemPreferredLanguage();
    }

    /**
     * 获取系统首选语言
     *
     * @return Locale
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Locale getSystemPreferredLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}

package com.example.myandroidsdk.ui.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fxb on 2020/7/27.
 * 带html内容返回，暂只用到content字段
 */
public class WebPage {
    @SerializedName(value = "content", alternate = "data")
    public String content;
}

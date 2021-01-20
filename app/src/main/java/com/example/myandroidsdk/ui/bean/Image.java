package com.example.myandroidsdk.ui.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fxb on 2020/7/3.
 */
public class Image {
    @SerializedName(value = "url", alternate = {"pic_url"})
    public String url;
}

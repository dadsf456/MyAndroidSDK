package com.example.myandroidsdk.ui.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by fxb on 2020/6/19.
 */
public class Address implements Serializable {
    /**
     * id : 4
     * name : 健康快乐
     * mobile : 15273161779
     * province_id : 2
     * province : 北京市
     * city_id : 3
     * city : 北京市
     * district_id : 4
     * district : 东城区
     * detail : 健康快乐
     * is_default : 0
     * article_id : 0
     * pic : null
     * address : 北京市北京市东城区健康快乐
     */
    public long id;
    public String name;
    public String mobile;
    public int province_id;
    public String province;
    public int city_id;
    public String city;
    public int district_id;
    public String district;
    public String detail;
    public String article_id;
    private String address;
    public int is_default;
    //public Object pic;

    /**
     * 确认订单bean内没有这个字段，确认订单时候调用
     */
    public String getAddress() {
        if (TextUtils.isEmpty(address))
            return province + city + district + detail;
        return address;
    }

    public boolean isDefault() {
        return is_default == 1;
    }
}

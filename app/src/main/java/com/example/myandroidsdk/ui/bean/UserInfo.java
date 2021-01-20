package com.example.myandroidsdk.ui.bean;

import android.text.TextUtils;

import com.example.myandroidsdk.ui.utils.DecimalUtil;


/**
 * Created by fxb on 2020/7/6.
 */
public class UserInfo {
    public String id;
    public String username;
    public String level;
    public String nickname;
    private String shop_address;
    private String door_photo;
    private String licences;
    public String binding;
    public String avatar_url;
    public String bond;
    public String total_price;
    public String all_total_price;
    public String integral;
    public String money;
    public String price;
    public String parent_id;
    public String just_card;
    public String back_card;
    public String card_num;
    public Object qrcode_url;
    public String invite;
    public String level_name;
    public long plan_time;
    public String total_buy_price;
    public String save_total_price;
    public String coupon_num;
    public String recommend_name;
    public String superior;
    public String group_num;
    public String profit;
    public String sales;
    public String rebate;
    /**
     * 其余全用String接收
     */
    public int is_check;
    private String is_card;
    public int gender;

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNickname() {
        if (TextUtils.isEmpty(nickname))
            return "未设置昵称";
        return nickname;
    }

    public String getBinding() {
        if (TextUtils.isEmpty(binding))
            return "未设置账户";
        return binding;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getDoor_photo() {
        return door_photo;
    }

    public void setDoor_photo(String door_photo) {
        this.door_photo = door_photo;
    }

    public String getLicences() {
        return licences;
    }

    public void setLicences(String licences) {
        this.licences = licences;
    }

    public String getIs_card() {
        return is_card;
    }

    public void setIs_card(String is_card) {
        this.is_card = is_card;
    }

    public String getSex() {
        switch (gender) {
            case 1:
                return "男";
            case 2:
                return "女";
            default:
                return "未设置性别";
        }
    }

    /**
     * 等级
     */
    public String getLevel() {
        if (TextUtils.equals("-1", level))
            return "0";
        return level;
    }

    /**
     * 是否为VIP用户
     */
    public boolean isVip() {
        try {
            int v = Integer.parseInt(level);
            return v >= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getGroupNum() {
        if (TextUtils.isEmpty(group_num))
            return "0";
        return group_num;
    }

    /**
     * 货款
     */
    public String getHK() {
        return DecimalUtil.format_append(money);
    }

    /**
     * 节省
     */
    public String getJS() {
        return DecimalUtil.format_append(save_total_price);
    }

    /**
     * 消费
     */
    public String getXF() {
        return DecimalUtil.format_append(total_buy_price);
    }

    /**
     * 赚取
     */
    public String getZQ() {
        return DecimalUtil.format_append(all_total_price);
    }

    /**
     * 积分
     */
    public String getJF() {
        if (TextUtils.isEmpty(integral))
            return "0";
        return integral;
    }

    /**
     * 优惠券数量
     */
    public String getCouponNum() {
        if (TextUtils.isEmpty(coupon_num))
            return "0";
        return coupon_num;
    }

    /**
     * 保证金
     */
    public String getBond() {
        return DecimalUtil.format_append(bond);
    }

    /**
     * 可提现金额
     */
    public String getWithdraw() {
        return DecimalUtil.format_append(price);
    }
}

package com.example.myandroidsdk.ui.global;

/**
 * Created by fxb on 2020/6/19.
 */
public interface Constant {
    String BASE_URL = "https://shopmall.weidu361.com/";
    //String BASE_URL = "http://hsl37.jiaodaoren.com/";

    String REGEX_NICKNAME = "^[\u4e00-\u9fa50-9]{1,10}$";
    String REGEX_MOBILE = "^(1[^0,^1,^2,\\D])\\d{9}$";//注册手机校验

    /**
     * 1：身份验证 2：登录确认 3：登录异常 4：用户注册 5：修改密码 6：信息变更 7:修改支付密码
     */
    int TYPE_VERIFY = 1;
    int TYPE_LOGIN_CONFIRM = 2;
    int TYPE_LOGIN_EXCEPTION = 3;
    int TYPE_REGISTER = 4;
    int TYPE_MODIFY_PWD = 5;
    int TYPE_INFO_CHANGE = 6;
    int TYPE_PAY_PWD = 7;
    int TYPE_BIND_WX = 8;

    String EMPTY = "";

    /**
     * 相关网页展示
     */
    String API_TREATY = "api/default/get-treaty";       //消费者协议，不知道是个什么鬼之前说是用户协议
    String API_PARTY = "api/default/get-party";         //用户协议
    String API_PRIVACY = "api/default/get-privacy";     //隐私政策
    String API_AGREEMENT = "api/default/get-agreement"; //升级协议
    String API_GRANT = "api/user/grant";                //我的授权
    String API_ABOUT_US = "api/default/about";          //关于锁友圈
    String API_VIP_BENEFITS = "api/user/member-right";  //会员权益

    /**
     * 修改用户信息
     */
    String API_MODIFY_PHONE = "api/user/submit-pay-code";
    String API_MODIFY_USER_PASSWORD_ = "api/passport/edit-password";
    String API_MODIFY_USER_PASSWORD = "api/user/edit-password";
    String API_MODIFY_PAY_PASSWORD = "api/user/edit-pay-password";

    /**
     * 提成，利润  和   销售明细
     */
    String API_COMMISSION = "api/user/my-commission";
    String API_SALES_DETAIL = "api/user/sales-detail";

    /**
     * 商品评价api
     */
    String API_COMMENT_PT = "api/group/index/goods-comment";
    String API_COMMENT_MS = "api/miaosha-index/comment-list";
    String API_COMMENT_HOT = "api/default/comment-list";

    /**
     * 订单列表api
     */
    String API_ORDER_HOT = "api/order/list";
    String API_ORDER_PT = "api/group/order/list";
    String API_ORDER_MS = "api/miaosha/order-list";
    String API_ORDER_OTHER = "api/order/supply-list";

    /**
     * 撤销订单api
     */
    String API_REVOKE_ORDER_PT = "api/group/order/revoke";
    String API_REVOKE_ORDER_MS = "api/miaosha/revoke";
    String API_REVOKE_ORDER_HOT = "api/order/revoke";

    /**
     * 订单详情api
     */
    String API_ORDER_DETAIL_PT = "api/group/order/detail";
    String API_ORDER_DETAIL_MS = "api/miaosha/order-details";
    String API_ORDER_DETAIL_HOT = "api/order/detail";

    /**
     * 确认收货api
     */
    String API_ORDER_CONFIRM_PT = "api/group/order/confirm";
    String API_ORDER_CONFIRM_MS = "api/miaosha/confirm";
    String API_ORDER_CONFIRM_HOT = "api/order/confirm";

    /**
     * 支付订单api
     */
    String API_ORDER_PAY_PT = "api/group/order/pay-data";
    String API_ORDER_PAY_MS = "api/miaosha/pay-data";
    String API_ORDER_PAY_HOT = "api/order/pay-data";

    /**
     * 提交详情api
     */
    String API_ORDER_REFUND_PT = "api/group/order/refund-detail";
    String API_ORDER_REFUND_MS = "api/miaosha/refund-detail";
    String API_ORDER_REFUND_HOT = "api/order/refund-detail";
}

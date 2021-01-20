package com.example.myandroidsdk.ui.utils;

import android.os.Bundle;

import com.example.myandroidsdk.ui.activity.HtmlActivity;
import com.tool.cs.common.base.BaseActivity;
import com.tool.cs.common.base.BaseFragment;


/**
 * Created by fxb on 2020/8/3.
 */
public class Utils {

    public static void startHtml(Object o, String title, String api) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("api", api);
        if (o instanceof BaseFragment) {
            BaseFragment bf = (BaseFragment) o;
            bf.startActivity(HtmlActivity.class, bundle);
        } else if (o instanceof BaseActivity) {
            BaseActivity ba = (BaseActivity) o;
            ba.startActivity(HtmlActivity.class, bundle);
        }
    }

//    /**
//     * 评论api
//     */
//    public static String getGoodsCommentApi(int type) {
//        if (type == HomeData.TYPE_PT)
//            return Constant.API_COMMENT_PT;
//        if (type == HomeData.TYPE_MS)
//            return Constant.API_COMMENT_MS;
//        if (type == HomeData.TYPE_HOT)
//            return Constant.API_COMMENT_HOT;
//        return Constant.EMPTY;
//    }
//
//    /**
//     * 拉起微信支付
//     */
//    public static void wakeWeChat(Object data, Context context) {
//        String json = GSONUtils.bean2Json(data);
//        WxPay bean = GSONUtils.json2Bean(json, WxPay.class);
//        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, null);
//        //将应用的appid注册到微信
//        wxApi.registerApp(bean.getAppid());
//        PayReq request = new PayReq();
//        request.appId = bean.getAppid();
//        request.partnerId = bean.getPartnerid();
//        request.prepayId = bean.getPrepayid();
//        request.packageValue = bean.get_package();
//        request.nonceStr = bean.getNoncestr();
//        request.timeStamp = bean.getTimestamp();
//        request.sign = bean.getSign();
//        wxApi.sendReq(request);
//    }
//
//    public static String getOrderPayApi(int order_type) {
//        if (order_type == OrderType.TYPE_PT)
//            return Constant.API_ORDER_PAY_PT;
//        else if (order_type == OrderType.TYPE_MS)
//            return Constant.API_ORDER_PAY_MS;
//        else if (order_type == OrderType.TYPE_HOT)
//            return Constant.API_ORDER_PAY_HOT;
//        return Constant.EMPTY;
//    }
//
//    public static String getOrderPayType(int pay_type) {
//        switch (pay_type) {
//            case PayType.TYPE_WX:
//                return "WECHAT_PAY";
//            case PayType.TYPE_ALI:
//                return "ALIPAY";
//            case PayType.TYPE_HUODAO:
//                return "HUODAO_PAY";
//            case PayType.TYPE_BALANCE:
//                return "BALANCE_PAY";
//            default:
//                return "";
//        }
//    }
//
//    public static String getOrderPayTypeText(int pay_type) {
//        switch (pay_type) {
//            case PayType.TYPE_WX:
//                return "微信支付";
//            case PayType.TYPE_ALI:
//                return "支付宝支付";
//            case PayType.TYPE_HUODAO:
//                return "货到付款";
//            case PayType.TYPE_BALANCE:
//                return "余额支付";
//            default:
//                return "";
//        }
//    }
//
//    public static String getOrderStateText(int state) {
//
//        switch (state) {
//            case State.TYPE_REVOKE_REJECT:
//                return "取消订单已拒绝";
//            case State.TYPE_REVOKE_PASS:
//                return "取消订单已通过";
//            case State.TYPE_REVOKE_SEND:
//                return "申请售后中";
//            case State.TYPE_REVOKE:
//                return "取消订单审核中";
//            case State.TYPE_PAY:
//                return "待付款";
//            case State.TYPE_SEND:
//                return "待发货";
//            case State.TYPE_RECEIVE:
//                return "待收货";
//            case State.TYPE_COMMENT:
//                return "待评价";
//            case State.TYPE_SUCCESS:
//                return "交易成功";
//            case State.TYPE_ERROR:
//                return "拼团失败";
//            case State.RETURND:
//                return "退款申请,商家处理中";
//            case State.RETURND1:
//                return "已退款";
//            case State.RETURND2:
//                return "商家已同意换货";
//            case State.RETURND3:
//                return "已拒绝退换货";
//            case State.RETURND4:
//                return "已拒绝退款";
//            case State.RETURND5:
//                return "换货申请,商家处理中";
//                case State.RETURND6:
//                return "商家已同意退款";
//            case State.RETURND7:
//                return "同意退款,已发货";
//            case State.RETURND8:
//                return "同意换货,已发货";
//            default:
//                return "默认";
//        }
//    }
//
//    public static String getAttrInfoApi(int type) {
//        if (type == HomeData.TYPE_PT)
//            return "api/group/index/goods-attr-info";
//        else
//            return "api/default/goods-attr-info";
//    }
}

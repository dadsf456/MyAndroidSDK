package com.example.myandroidsdk.ui.net;


import com.example.myandroidsdk.ui.bean.BeanList;
import com.example.myandroidsdk.ui.bean.Goods;
import com.example.myandroidsdk.ui.bean.GoodsCategory;
import com.example.myandroidsdk.ui.bean.Image;
import com.example.myandroidsdk.ui.bean.MyUserToken;
import com.example.myandroidsdk.ui.bean.UserInfo;
import com.example.myandroidsdk.ui.bean.UserToken;
import com.example.myandroidsdk.ui.bean.WebPage;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by fxb on 2020/6/19.
 */
public interface ApiService {

    @POST("web/index.php?r=api/default/upload-image")
    @FormUrlEncoded
    Observable<Response<Image>> uploadImage(@Field("img") String image);

//    @POST("web/index.php?r=api/default/upload-image")
//    @FormUrlEncoded
//    Observable<Response<Image>> uploadImage(@Field("img") String image);
//
//    @GET("web/index.php?r=api/default/get-advert")
//    Observable<Response<Image>> getAdvertise();
//
//    /*------------------------学习中心------------------------*/
//
//    /**
//     * 图文教程
//     */
//    @POST("web/index.php?r=api/default/study-center")
//    Observable<Response<BeanList<Study>>> getStudyCenter(@QueryMap Map<String, Object> params);
//
//    /**
//     * 车系分类
//     */
//    @GET("web/index.php?r=api/default/series")
//    Observable<Response<List<CarCategory>>> getCarCategories();
//
//    /**
//     * 车品一级分类
//     */
//    @GET("web/index.php?r=api/default/car-one")
//    Observable<Response<List<Car>>> getCarListByCatId(@Query("series_id") long seriesId);
//
//    /**
//     * 车品子类
//     */
//    @GET("web/index.php?r=api/default/car-child")
//    Observable<Response<List<Car>>> getCarChildren(@Query("car_id") long id);
//
//    @GET("web/index.php?r=api/default/car-search")
//    Observable<Response> getCarSearch(@Query("keywords") String keywords);
//
//    /*------------------------个人中心------------------------*/
//
    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @param type  验证码类型 1：身份验证 2：登录确认 3：登录异常 4：用户注册 5：修改密码 6：信息变更
     * @return
     */
    @GET("web/index.php?r=api/passport/send-code")
    Observable<Response> getSendCode(@Query("mobile") String phone,
                                     @Query("type") int type);

    /**
     * 商品分类
     */
    @GET("web/index.php?r=api/default/cat-list")
    Observable<Response<BeanList<GoodsCategory>>> getShopCategories();

    /**
     * 普通商品列表
     */
    @GET("web/index.php?r=api/default/goods-list")
    Observable<Response<BeanList<Goods>>> getSearch(@QueryMap Map<String, Object> params);

    /**
     * 个人中心
     */
    @GET("web/index.php?r=api/user/user-center")
    Observable<Response<UserInfo>> getUserInfo(@QueryMap Map<String, Object> params);


    /**
     * 我的授权
     */
    @GET("web/index.php")
    Observable<Response<WebPage>> getWebPage(@QueryMap Map<String, Object> params);

    /**
     * 修改资料
     */
    @GET("web/index.php?r=api/user/edit-user-data")
    Observable<Response> getEditInfo(@QueryMap Map<String, Object> params);


    /**
     * 提交反馈
     *
     * @param type    问题类型
     * @param detail  内容
     * @param title   主题
     * @param contact 联系方式
     * @return
     */
    @GET("web/index.php?r=api/user/submit-feedback")
    Observable<Response> submitFeedback(@Query("type") int type,
                                        @Query("detail") String detail,
                                        @Query("type_title") String title,
                                        @Query("contact") String contact);

    /**
     * 提交验证码
     */
    @GET("web/index.php?r=api/user/submit-pay-code")
    Observable<Response> modifyPhone(@Query("mobile") String phone,
                                     @Query("code") String code,
                                     @Query("type") int type);

    /**
     * 注册
     */
    @GET("web/index.php?r=api/passport/register")
    Observable<Response<UserToken>> register(@QueryMap Map<String, Object> params);

    /**
     * 登录
     */
    @POST("web/index.php?r=api/passport/login")
    Observable<Response<UserToken>> login(@QueryMap Map<String, Object> params);

    /**
     * 登录
     * 测试 用传统写法来
     */
    @POST("web/index.php?r=api/passport/login")
    Observable<MyUserToken> login1(@QueryMap Map<String, Object> params);

//    /**
//     * 登录
//     */
//    @POST("web/index.php?r=api/passport/other-login")
//    @FormUrlEncoded
//    Observable<Response<UserToken>> loginOther(@FieldMap Map<String, Object> params);
//
//    /**
//     * 修改用户信息
//     * 账号密码 or 支付密码 （可以通过原密码或验证码）
//     * 更换手机(需要验证码)
//     */
//    @GET("web/index.php")
//    Observable<Response> modifyUserInfo(@QueryMap Map<String, Object> params);
//
//    @POST("web/index.php?r=api/default/index")
//    @FormUrlEncoded
//    Observable<Object> loadHomePage(@Field("type") int type,
//                                    @Field("page") int page);
//
//    /**
//     * 提交反馈
//     *
//     * @param type    问题类型
//     * @param detail  内容
//     * @param title   主题
//     * @param contact 联系方式
//     * @return
//     */
//    @GET("web/index.php?r=api/user/submit-feedback")
//    Observable<Response> submitFeedback(@Query("type") int type,
//                                        @Query("detail") String detail,
//                                        @Query("type_title") String title,
//                                        @Query("contact") String contact);
//
//    /**
//     * 设为默认收货地址接口
//     */
//    @GET("web/index.php?r=api/user/address-set-default")
//    Observable<Response> setDefaultAddressById(@Query("address_id") long addressId);
//
//    /**
//     * 删除收货地址
//     */
//    @GET("web/index.php?r=api/user/address-delete")
//    Observable<Response> deleteAddressById(@Query("address_id") long addressId);
//
//    /**
//     * 编辑收货地址接口
//     */
//    @GET("web/index.php?r=api/user/address-detail")
//    Observable<Response> editAddressById(@Query("id") long addressId);
//
//    /**
//     * 个人中心
//     */
//    @GET("web/index.php?r=api/user/user-center")
//    Observable<Response<UserInfo>> getUserInfo(@QueryMap Map<String, Object> params);
//
//    /**
//     * 修改资料
//     */
//    @GET("web/index.php?r=api/user/edit-user-data")
//    Observable<Response> getEditInfo(@QueryMap Map<String, Object> params);
//
//    /**
//     * 我的授权
//     */
//    @GET("web/index.php")
//    Observable<Response<WebPage>> getWebPage(@QueryMap Map<String, Object> params);
//
//    /**
//     * 消息通知列表
//     */
//    @GET("web/index.php?r=api/user/new-list")
//    Observable<Response<BeanList<Notice>>> getNotices();
//
//    @GET("/web/index.php?r=api/user/news")
//    Observable<Response<BeanList<Message>>> getNotices(@Query("news_type") int type,
//                                                       @Query("page") int page);
//
//    /**
//     * 帮助中心
//     */
//    @GET("web/index.php?r=api/default/help")
//    Observable<Response<List<HelpListBean>>> getHelpInfo();
//
//    /**
//     * 我要升级
//     */
//    @GET("web/index.php?r=api/user/user-level")
//    Observable<Response<BeanList<Level>>> getLevels();
//
//    /**
//     * 地址图标
//     */
//    @GET("web/index.php?r=api/default/address-pic")
//    Observable<Response> getAddressPics();
//
//    /**
//     * 我的优惠卷 - 无分页
//     *
//     * @param status 0：未使用 1：已使用 2：已过期
//     * @return
//     */
//    @GET("web/index.php?r=api/coupon/index")
//    Observable<Response<BeanList<Coupon>>> getCouponList(@QueryMap Map<String, Object> params);
//
//    /**
//     * 我的收藏
//     * type 收藏类型 不传：全部收藏 1：有优惠 2：买过
//     * page 页码
//     */
//    @GET("web/index.php?r=api/user/favorite-list")
//    Observable<Response<BeanList<Favorites>>> getFavorites(@QueryMap Map<String, Object> params);
//
//    /**
//     * 收藏下的推荐
//     */
//    @GET("web/index.php?r=api/default/favorite-recommend")
//    Observable<Response<BeanList<FavoritesRecommend>>> getFavoriteRecommend(@Query("page") int page);
//
//    /**
//     * 购物车的推荐
//     */
//    @GET("web/index.php?r=api/default/favorite-recommend")
//    Observable<Response<BeanList<ShopHot>>> getShopRecommend(@Query("page") int page);
//
//    /**
//     * 我的提成和利润列表,销售额明细 参数详见文档
//     */
//    @GET("web/index.php?r=api/user/my-commission")
//    Observable<Response<BeanList<Commission>>> getCommissionList(@QueryMap Map<String, Object> params);
//
//    /**
//     * 修改用户头像
//     */
//    @POST("web/index.php?r=api/user/edit-user-pic")
//    Observable<Response> modifyAvatarUrl(@Query("img") String img);
//
//    /**
//     * 实名认证
//     */
//    @GET("web/index.php?r=api/user/real-name")
//    Observable<Response> submitVerify(@Query("username") String username,
//                                      @Query("card_num") String cardNumber,
//                                      //@Query("gender") String sex,
//                                      @Query("just_card") String img1,
//                                      @Query("back_card") String img2);
//    /**
//     * 高级实名认证
//     */
//    @POST("web/index.php?r=api/user/user-card")
//    @FormUrlEncoded
//    Observable<Response> submitVerify1(@Field("licences") String url1,
//                                       @Field("door_photo") String url2,
//                                       @Field("shop_address") String shop_address);
//    /**
//     * 收款列表
//     */
//    @GET("web/index.php?r=api/user/profit")
//    Observable<Response<BeanList<Earnings>>> getProfit(@Query("status") int status,
//                                                       @Query("page") int page);
//
//    @POST("web/index.php?r=api/user/collection")
//    @FormUrlEncoded
//    Observable<Response> receiveProfit(@Field("id") String ids);
//
//    /**
//     * 我的团队列表接口
//     */
//    @GET("web/index.php?r=api/user/direct-team")
//    Observable<Response<BeanList<Member>>> getTeamList(@QueryMap Map<String, Object> params);
//
//    /**
//     * 申请提现
//     */
//    @POST("web/index.php?r=api/user/cash")
//    @FormUrlEncoded
//    Observable<Response> applyWithdraw(@FieldMap Map<String, Object> params);
//
//    /*------------------------商城首页------------------------*/
//    @GET("web/index.php?r=api/user/cash-detail")
//    Observable<Response<BeanList<Withdraw>>> getWithdrawDetail(@Query("status") int status,
//                                                               @Query("page") int page);
//
//    /**
//     * 商城首页请求数据
//     */
//    @GET("/web/index.php?r=api/default/shop-index")
//    Observable<Response<HomeData>> getMallHomeData();
//
//    /**
//     * 商品详情
//     */
//    @GET("web/index.php?r=api/default/goods")
//    Observable<Response<GoodsHot>> getGoodsDetailsByGoodsId(@Query("id") String id);
//
//    /**
//     * 商品评价(拼团，秒杀，热门)
//     */
//    @GET("web/index.php")
//    Observable<Response<BeanList<Comment>>> getCommentListById(@Query("r") String api,
//                                                               @Query("goods_id") String id,
//                                                               @Query("page") int page);
//
//    /**
//     * 首页分类列表
//     */
//    @GET("web/index.php?r=api/default/cat-list")
//    Observable<Response> getHomeCateGoryList();
//
//    /**
//     * 商品搜索
//     *
//     * @param key  关键词
//     * @param type 不传：商品教程 1：商品 2：教程
//     * @return
//     */
//    @GET("web/index.php?r=api/default/goods-search")
//    Observable<Response> getGoodsSearchResults(@Query("keywords") String key,
//                                               @Query("goods_type") int type);
//
//    /**
//     * 统一商品搜索
//     *
//     * @param key 关键词
//     * @return
//     */
//    @GET("web/index.php?r=api/default/search")
//    Observable<Response> getGoodsSearchResults(@Query("keywords") String key);
//
//
//    /**
//     * 商品详情页选择属性
//     */
//    @GET("web/index.php")
//    Observable<Response<AttrInfo>> getAttrInfo(@QueryMap Map<String, Object> params);
//
//    /**
//     * 普通商品列表
//     */
//    @GET("web/index.php?r=api/default/goods-list")
//    Observable<Response<BeanList<Goods>>> getSearch(@QueryMap Map<String, Object> params);
//
//    /**
//     * 收藏/点赞 取消 或 加入
//     */
//    @GET("web/index.php?store_id=2")
//    Observable<Response> addFavorite(@QueryMap Map<String, Object> params);
//
//    /**
//     * 增加浏览量
//     */
//    @GET("web/index.php?r=api/default/hits")
//    Observable<Response> e(@Query("goods_id") String goodsId);
//
//    /**
//     * 增加播放量
//     */
//    @GET("web/index.php?r=api/default/plays")
//    Observable<Response> f(@Query("goods_id") String goodsId);
//
//    /*------------------------购物车模块------------------------*/
//
//    /**
//     * 购物车列表
//     */
//    @GET("web/index.php?r=api/cart/list")
//    Observable<Response<BeanList<ShopData>>> getCartList(@Query("page") int page);
//
//    /**
//     * 添加购物车
//     */
//    @GET("/web/index.php?store_id=2&r=api/cart/add-cart")
//    Observable<Response> addShop(@QueryMap Map<String, Object> params);
//
//    /**
//     * 删除购物车
//     */
//    @GET("web/index.php?r=api/cart/delete")
//    Observable<Response> removeShop(@Query("cart_id_list") String json);
//
//    @POST("web/index.php?r=api/cart/cart-edit")
//    @FormUrlEncoded
//    Observable<Response> editShop(@Field("list") String json);
//
//    /**
//     * 收货地址列表
//     */
//    @GET("web/index.php?r=api/user/address-list")
//    Observable<Response<List<Address>>> getAddressList();
//
//    /**
//     * 保存地址
//     */
//    @POST("web/index.php?r=api/user/address-save")
//    @FormUrlEncoded
//    Observable<Response> saveAddress(@FieldMap Map<String, Object> params);
//
//    /**
//     * 地址三级联动
//     */
//    @GET("web/index.php?r=api/default/district")
//    Observable<Response<List<District>>> getDistrict();
//
//    /**
//     * 商品分类
//     */
//    @GET("web/index.php?r=api/default/cat-list")
//    Observable<Response<BeanList<GoodsCategory>>> getShopCategories();
//
//    /*------------------------订单模块------------------------*/
//
//    /**
//     * 申请售后发货
//     */
//    @POST("web/index.php?r=api/group/order/refund-send")
//    @FormUrlEncoded
//    Observable<Response> sendOrderPt(@FieldMap Map<String, Object> params);
//
//    /**
//     * 申请售后发货
//     */
//    @POST("web/index.php?r=api/miaosha/refund-send")
//    @FormUrlEncoded
//    Observable<Response> sendOrderMs(@FieldMap Map<String, Object> params);
//
//    /**
//     * 申请售后发货
//     */
//    @POST("web/index.php?r=api/order/refund-send")
//    @FormUrlEncoded
//    Observable<Response> sendOrderHot(@FieldMap Map<String, Object> params);
//
//    /**
//     * 申请售后
//     */
//    @POST("web/index.php?r=api/group/order/refund")
//    @FormUrlEncoded
//    Observable<Response> refundOrderPt(@FieldMap Map<String, Object> params);
//
//    /**
//     * 申请售后
//     */
//    @POST("web/index.php?r=api/miaosha/refund")
//    @FormUrlEncoded
//    Observable<Response> refundOrderMs(@FieldMap Map<String, Object> params);
//
//    /**
//     * 申请售后
//     */
//    @POST("web/index.php?r=api/order/refund")
//    @FormUrlEncoded
//    Observable<Response> refundOrderHot(@FieldMap Map<String, Object> params);
//
//    /**
//     * 售后详情
//     */
//    @GET("web/index.php?")
//    Observable<Response<OrderDetailRefund>> refundOrderDetail(@QueryMap Map<String, Object> params);
//
//    /**
//     * 订单列表
//     */
//    @GET("web/index.php?")
//    Observable<Response<BeanList<Order>>> getOrders(@QueryMap Map<String, Object> params);
//
//    /**
//     * 取消订单
//     */
//    @GET("web/index.php?")
//    Observable<Response> revokeOrder(@Query("r") String api,
//                                     @Query("order_id") long orderId);
//
//    /**
//     * 确认订单
//     */
//    @POST("web/index.php?r=api/order/submit-preview")
//    @FormUrlEncoded
//    Observable<Response<OrderPreview>> submitPreviewHot(@FieldMap Map<String, Object> params);
//
//    /**
//     * 订单请求提交，生成订单
//     */
//    @POST("web/index.php?r=api/order/submit")
//    @FormUrlEncoded
//    Observable<Response<Object>> submitOrderHot(@FieldMap Map<String, Object> params);
//
//    /**
//     * 支付订单
//     */
//    @GET("web/index.php")
//    Observable<Response<Object>> payOrder(@QueryMap Map<String, Object> params);
//
//    /**
//     * 订单详情
//     */
//    @GET("web/index.php")
//    Observable<Response<OrderDetail>> getOrderDetail(@Query("r") String api,
//                                                     @Query("order_id") long orderId);
//
//    /**
//     * 订单物流
//     */
//    @GET("web/index.php?r=api/order/express-detail")
//    Observable<Response<Express>> getExpressDetail(@Query("order_id") long id, @Query("type") String type);
//
//    /**
//     * 确认收货
//     */
//    @GET("web/index.php?r=api/order/confirm")
//    Observable<Response> confirmOrder(@Query("r") String api,
//                                      @Query("order_id") long orderId);
//
//    /**
//     * 订单评价预览页
//     */
//    @GET("web/index.php?r=api/order/comment-preview")
//    Observable<Response> commentPreview(@Query("order_id") long orderId,
//                                        @Query("type") String type);
//
//    /**
//     * 提交评论
//     */
//    @POST("web/index.php?r=api/order/comment-submit")
//    @FormUrlEncoded
//    Observable<Response> commentSubmit(@FieldMap Map<String, Object> param);
//
//    /**
//     * 提交评论
//     */
//    @POST("web/index.php?r=api/group/order/comment")
//    @FormUrlEncoded
//    Observable<Response> commentPtSubmit(@FieldMap Map<String, Object> param);
//
//    /**
//     * 提交评论
//     */
//    @POST("web/index.php?r=api/miaosha/comment")
//    @FormUrlEncoded
//    Observable<Response> commentMsSubmit(@FieldMap Map<String, Object> param);
//    /*------------------------拼团模块------------------------*/
//
//    @GET("web/index.php?r=api/group/index/good-list")
//    Observable<Response<GoodsPtListBean>> getPtList();
//
//    /**
//     * 拼团商品详情
//     */
//    @GET("web/index.php?r=api/group/index/good-details")
//    Observable<Response<GoodsPT>> getPtDetailsById(@Query("gid") String id);
//
//    /**
//     * 拼团商品订单提交前的预览页
//     */
//    @POST("web/index.php?r=api/group/order/submit-preview")
//    @FormUrlEncoded
//    Observable<Response<OrderPreview>> submitPreviewPt(@FieldMap Map<String, Object> params);
//
//    /**
//     * 拼团订单请求提交，生成订单
//     */
//    @POST("web/index.php?store_id=2&r=api/group/order/submit")
//    @FormUrlEncoded
//    Observable<Response<Object>> submitOrderPt(@FieldMap Map<String, Object> params);
//
//    /*------------------------秒杀模块------------------------*/
//
//    /**
//     * 秒杀商品详情
//     */
//    @GET("web/index.php?r=api/miaosha/details")
//    Observable<Response<GoodsMS>> getMsDetailsById(@Query("id") String id);
//
//    /**
//     * 秒杀订单提交预览页
//     */
//    @POST("web/index.php?r=api/miaosha/submit-preview")
//    @FormUrlEncoded
//    Observable<Response<OrderPreview>> submitPreviewMs(@FieldMap Map<String, Object> params);
//
//    /**
//     * 秒杀订单请求提交，生成订单
//     */
//    @POST("web/index.php?r=api/miaosha/submit")
//    @FormUrlEncoded
//    Observable<Response<Object>> submitOrderMs(@FieldMap Map<String, Object> params);
//
//    /**
//     * 拼团去参加
//     */
//    @GET("web/index.php?r=api/group/order/group")
//    Observable<Response<MorePtBean>> ptAdds(@Query("oid") String id);
//
//    /**
//     * 客服
//     */
//    @GET("web/index.php?r=api/default/service-center")
//    Observable<Response<List<Service>>> getServices();
//
//    /**
//     * 提交评论
//     */
//    @GET("web/index.php?r=api/order/comment-video")
//    Observable<Response> getRecommend(@Query("goods_id") String goods_id, @Query("content") String content);
//
//    /**
//     * h5页面
//     */
//    @GET("web/index.php?r=api/default/get-config")
//    Observable<Response<LinkInfo>> getLinkInfo();
//
//    /**
//     * 升级
//     */
//    @POST("web/index.php?r=api/user/user-apply")
//    @FormUrlEncoded
//    Observable<Response> getRpege(@FieldMap Map<String, Object> params);
//
//    /**
//     * 升级状态
//     */
//    @GET("web/index.php?r=api/user/apply-status")
//    Observable<Response<RpegeStatuesBean>> getRpegeStatues();

}

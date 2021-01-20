package com.example.myandroidsdk.ui.global;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.Address;
import com.example.myandroidsdk.ui.bean.UserInfo;
import com.example.myandroidsdk.ui.bean.UserToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tool.cs.common.utils.GSONUtils;
import com.tool.cs.common.utils.LogUtils;
import com.tool.cs.common.utils.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by fxb on 2020/6/16.
 */
public class App extends Application {
    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true);
            layout.setEnableOverScrollDrag(false);
            layout.setEnableOverScrollBounce(true);
            layout.setEnableLoadMoreWhenContentNotFull(true);
            layout.setEnableScrollContentWhenRefreshed(true);
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            layout.setPrimaryColorsId(R.color.activityBg, R.color.colorAccent);

            SimpleDateFormat sdf = new SimpleDateFormat("更新于 MM月dd日 HH:mm", Locale.SIMPLIFIED_CHINESE);
            /*classicsHeader.setArrowResource(R.drawable.ic_pull_down);
            classicsHeader.setProgressResource(R.drawable.ic_pull_down_loading);
            classicsHeader.setDrawableArrowSize(48);
            classicsHeader.setDrawableProgressSize(48);*/
            return new ClassicsHeader(context).setTimeFormat(sdf);
        });
    }

    @SuppressLint("StaticFieldLeak")
    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private UserToken userToken;//这里的bean仅保存token，其余用户信息每次打开App需要取一次

    /**
     * 保存用户信息
     * @param data
     */
    public void setUserToken(UserToken data) {
        this.userToken = data;

        if (data != null) {
            String tokenJson = GSONUtils.bean2Json(data);
            SpUtils.getInstance(this).put("token", tokenJson);//存储token Json
        }
    }

    public UserToken getUserToken() {
        if (userToken == null) {
            String token = SpUtils.getInstance(this)
                    .getString("token", "{}");//读取token Json

            userToken = GSONUtils.json2Bean(token, UserToken.class);

            LogUtils.e("APP", token);
            LogUtils.e("APP", userToken.toString());
        }
        return userToken;
    }

    public String getToken() {
        return getUserToken().getToken();
    }

    public long getUid() {
        return getUserToken().getId();
    }

    public long getPlayTime() {
        return getUserInfo().plan_time;
    }

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private Address address;

    public Address getAddress() {
        if (address == null) {
            String add = SpUtils.getInstance(this)
                    .getString("address", "{}");//读取token Json

            address = GSONUtils.json2Bean(add, Address.class);
        }
        return address;
    }


    public void setAddressBean(Address data) {
        this.address = data;

        String tokenJson = GSONUtils.bean2Json(data);
        SpUtils.getInstance(this).put("address", tokenJson);//存储token Json
    }


}

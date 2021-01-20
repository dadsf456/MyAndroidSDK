package com.example.myandroidsdk.ui.activity;

import android.view.View;
import android.widget.Toast;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.UserInfo;
import com.example.myandroidsdk.ui.fragment.MainFragment1;
import com.example.myandroidsdk.ui.fragment.MainFragment2;
import com.example.myandroidsdk.ui.fragment.MainFragment3;
import com.example.myandroidsdk.ui.fragment.MainFragment4;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.adapter.FragmentAdapter;
import com.tool.cs.common.base.BaseActivity;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.BottomNavigationBar;
import com.tool.cs.common.widget.NoScrollViewPager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {
    @BindView(R.id.mStatusBar)
    View mStatusBar;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.mBottomBar)
    BottomNavigationBar mBottomBar;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        super.initView();
        queryUserInfo();
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new MainFragment1());
        fragmentAdapter.addFragment(new MainFragment2());
        fragmentAdapter.addFragment(new MainFragment3());
        fragmentAdapter.addFragment(new MainFragment4());
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setOffscreenPageLimit(fragmentAdapter.getCount());
        mBottomBar.setOnTabSelectListener(new BottomNavigationBar.OnTabSelectListener() {
            @Override
            public void selectTab(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
    }

    /**
     * 每次登录查询一次
     */
    private void queryUserInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", App.getInstance().getUid());
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getUserInfo(params)
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::getData)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo data) {
                        //内存中更新用户信息
                        App.getInstance().setUserInfo(data);
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast("信息同步失败，请重新登录！");
                        finish();
                        startActivity(LoginActivity.class);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgress(R.string.text_loading_sync);
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                    }
                });
    }


    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarView(mStatusBar)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarDarkIcon(true, 0.2f)
                .autoStatusBarDarkModeEnable(true, 0.2f)
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .init();
    }

    /**
     * 连按两次退出逻辑，间隔时间<=2秒
     */
    private long lastClickTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - this.lastClickTime > 2000L) {
            this.lastClickTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.again_out) + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        } else
            super.onBackPressed();
    }
}

package com.example.myandroidsdk.ui.fragment;


import android.view.View;
import android.widget.TextView;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.activity.LoginActivity;
import com.example.myandroidsdk.ui.activity.SettingsActivity;
import com.example.myandroidsdk.ui.bean.UserInfo;
import com.example.myandroidsdk.ui.event.UpdatePhoneEvent;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.example.myandroidsdk.ui.utils.GlideImageLoader;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseFragment;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.AvatarImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 作者：Created by dadsf456 on 2020-11-25 18:23
 * 邮箱：
 * 描述：
 */
public class MainFragment4 extends BaseFragment {
    @BindView(R.id.mStatusBar)
    View mStatusBar;
    @BindView(R.id.iv_avatar)
    AvatarImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_mine;
    }


    @Override
    protected void initialize() {
       // updateView ();
        if (App.getInstance().getUserInfo().avatar_url != null) {
            GlideImageLoader.loadAvatar(getContext(), iv_avatar, App.getInstance().getUserInfo().avatar_url);
        }
        if (App.getInstance().getUserInfo().binding != null) {
            tv_phone.setText(App.getInstance().getUserInfo().binding);
        }
        if (App.getInstance().getUserInfo().getNickname() != null) {
            tv_name.setText(App.getInstance().getUserInfo().getNickname());
        }
         tv_name.setText(App.getInstance().getUserInfo().getNickname());
         tv_phone.setText(App.getInstance().getUserInfo().binding);
        queryUserInfo();
    }

    protected void updateView (){
        if (App.getInstance().getUserInfo().avatar_url != null) {
            GlideImageLoader.loadAvatar(getContext(), iv_avatar, App.getInstance().getUserInfo().avatar_url);
        }
        if (App.getInstance().getUserInfo().binding != null) {
            tv_phone.setText(App.getInstance().getUserInfo().binding);
        }
        if (App.getInstance().getUserInfo().getNickname() != null) {
            tv_name.setText(App.getInstance().getUserInfo().getNickname());
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            ImmersionBar.with(this)
                    .statusBarView(mStatusBar)
                    .statusBarDarkFont(true, 0.2f)
                    .navigationBarDarkIcon(true, 0.2f)
                    .autoStatusBarDarkModeEnable(true, 0.2f)
                    .autoNavigationBarDarkModeEnable(true, 0.2f)
                    .init();
        }
    }

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
                        getActivity().finish();
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

    @OnClick(R.id.btn_message)
    public void onClickMessage() {
      //  startActivity(NoticeActivity.class);
    }

    @OnClick(R.id.cv_modify_info)
    public void onClickModifyInfo() {
       // startActivityForResult(UserInfoActivity.class, 0);
    }

    @OnClick({R.id.cv_vip, R.id.cv_upgrade, R.id.cv_service,
            R.id.cv_wallet, R.id.cv_order, R.id.cv_shop,
            R.id.cv_favorites, R.id.cv_verify, R.id.cv_invite,
            R.id.cv_benefits, R.id.cv_address, R.id.cv_settings
    })
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.cv_vip:
//                if (App.getInstance().getUserInfo().isVip())
//                    startActivity(VipActivity.class);
//                else
//                    showToast(R.string.prompt_upgrade_vip);
//                break;
//            case R.id.cv_upgrade:
//                startActivity(UpgradeActivity.class);
//                break;
//            case R.id.cv_service:
//                new ServiceDialog()
//                        .setOnItemClickListener(data ->
//                                new ServiceSelectDialog()
//                                        .init(data)
//                                        .show(getChildFragmentManager(), "select"))
//                        .show(getChildFragmentManager(), "service");
//                break;
//            case R.id.cv_wallet:
//                startActivity(WalletActivity.class);
//                break;
//            case R.id.cv_order:
//                startActivity(OrderActivity.class);
//                break;
//            case R.id.cv_shop:
//                if (getActivity() instanceof MainActivity)
//                    EventBus.getDefault().post(new EventJump(2));
//                else {
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("select_cart", true);
//                    startActivity(MainActivity.class, bundle);
//                }
//                break;
//            case R.id.cv_favorites:
//                startActivity(FavoritesActivity.class);
//                break;
//            case R.id.cv_verify:
//                int is_check = App.getInstance().getUserInfo().is_check;
//                if (is_check == 1) {
//                    showToast("正在审核中");
//                    return;
//                }
//                startActivity(VerifyActivity.class);
//                break;
//            case R.id.cv_invite:
//                startActivity(InviteActivity.class);
//                break;
//            case R.id.cv_benefits:
//                Utils.startHtml(this, "会员权益", Constant.API_VIP_BENEFITS);
//                break;
//            case R.id.cv_address:
//                startActivity(Setting01Activity.class);
//                break;
            case R.id.cv_settings:
                startActivityForResult(SettingsActivity.class, 0);
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            initialize();
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void XXX(UpdatePhoneEvent phoneEvent) {
      //  updateView();
        initialize();
    }
}

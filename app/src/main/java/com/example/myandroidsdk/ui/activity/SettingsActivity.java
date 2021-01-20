package com.example.myandroidsdk.ui.activity;

import android.widget.TextView;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.dialog.CommonDialog;
import com.example.myandroidsdk.ui.event.UpdatePhoneEvent;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.utils.GlideImageLoader;
import com.example.myandroidsdk.ui.utils.Utils;
import com.tool.cs.common.AppManager;
import com.tool.cs.common.utils.SpUtils;
import com.tool.cs.common.widget.AvatarImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fxb on 2020/6/16.
 */
public class SettingsActivity extends BaseSecondActivity {
    @BindView(R.id.iv_avatar)
    AvatarImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @Override
    protected String getBarTitleText() {
        return getString(R.string.bar_title_settings);
    }

    @Override
    protected int createContent() {
        EventBus.getDefault().register(this);
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {

        GlideImageLoader.loadAvatar(this, iv_avatar, App.getInstance().getUserInfo().avatar_url);
        tv_name.setText(App.getInstance().getUserInfo().getNickname());
        tv_phone.setText(App.getInstance().getUserInfo().binding);
    }

    @OnClick(R.id.cv_edt)
    public void onClickEdit() {
        //不同的方式 设置图片
     //   startActivityForResult(UserInfoActivity.class, 0);
        startActivityForResult(UserInfoActivity.class, 0);
    }

//    @OnClick(R.id.cv_settings_01)
//    public void onClickSettings01() {
//        startActivity(Setting01Activity.class);
//    }
//
//    @OnClick(R.id.cv_settings_02)
//    public void onClickSettings02() {
//        startActivity(Setting02Activity.class);
//    }
//
//    @OnClick(R.id.cv_settings_03)
//    public void onClickSettings03() {
//        startActivity(Setting03Activity.class);
//    }
//
//    @OnClick(R.id.cv_settings_04)
//    public void onClickSettings04() {
//        startActivity(Setting04Activity.class);
//    }
//
//    @OnClick(R.id.cv_settings_05)
//    public void onClickSettings05() {
//        startActivity(Setting05Activity.class);
//    }
//
//    @OnClick(R.id.cv_settings_06)
//    public void onClickSettings06() {
//        startActivity(Setting06Activity.class);
//    }
//
//    @OnClick(R.id.cv_settings_07)
//    public void onClickSettings07() {
//        startActivity(Setting07Activity.class);
//    }
//
    @OnClick(R.id.cv_settings_08)
    public void onClickSettings08() {
        startActivity(Setting08Activity.class);
    }

    @OnClick(R.id.cv_settings_09)
    public void onClickSettings09() {
        Utils.startHtml(this, "关于锁友圈", Constant.API_ABOUT_US);
    }

    @OnClick(R.id.cv_logout)
    public void onClickLogout() {
        CommonDialog dialog = new CommonDialog.Builder()
                .setContentText(String.format("确定退出%s吗？", getString(R.string.app_name)))
                .setConfirmText("退出")
                .setOnConfirmListener(() -> {
                    //登出删除本地token
                    SpUtils.getInstance(this).remove("token");
                    App.getInstance().setUserToken(null);

                    AppManager.getAppManager().finishAllActivity();
                    startActivity(LoginActivity.class);
                }).create();
        dialog.show(getSupportFragmentManager(), "logout");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void XXX(UpdatePhoneEvent phoneEvent) {
        initView();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            setResult(RESULT_OK);
//            initView();
//        }
//    }
}

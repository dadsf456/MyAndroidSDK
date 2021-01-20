package com.example.myandroidsdk.ui.fragment;


import android.view.View;
import android.widget.Button;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.activity.ComplexViewActivity;
import com.example.myandroidsdk.ui.activity.DialogActivity;
import com.example.myandroidsdk.ui.activity.DogTypeActivity;
import com.example.myandroidsdk.ui.activity.LoginActivity;
import com.example.myandroidsdk.ui.activity.MoreItemActivity;
import com.example.myandroidsdk.ui.activity.PopupActivity;
import com.example.myandroidsdk.ui.activity.RefreshActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：Created by dadsf456 on 2020-11-25 18:23
 * 邮箱：
 * 描述：
 */
public class MainFragment1 extends BaseFragment {
    @BindView(R.id.bt_popup)
    Button btPopup;
    @BindView(R.id.bt_dialog)
    Button btDialog;
    @BindView(R.id.bt_refrech)
    Button btRefrech;
    @BindView(R.id.bt_moreitem)
    Button btMoreitem;
    @BindView(R.id.bt_pull_delete)
    Button btPullDelete;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_dog_type)
    Button btDogType;
    @BindView(R.id.bt_details)
    Button btDetails;
    Unbinder unbinder;
    @BindView(R.id.mStatusBar)
    View mStatusBar;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_01;
    }

    @Override
    protected void initialize() {

    }

    public void popupClick(View v) {
        // Toast.makeText(this, "点击了按钮", 5).show();
    }


    @OnClick({R.id.bt_popup, R.id.bt_dialog, R.id.bt_refrech, R.id.bt_moreitem, R.id.bt_pull_delete, R.id.bt_login,
            R.id.bt_dog_type, R.id.bt_details, R.id.bt_complex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_popup:
                startActivity(PopupActivity.class);
                break;
            case R.id.bt_dialog:
                startActivity(DialogActivity.class);
                break;
            case R.id.bt_refrech:
                startActivity(RefreshActivity.class);
                break;
            case R.id.bt_moreitem:
                startActivity(MoreItemActivity.class);
                break;
            case R.id.bt_pull_delete:
                break;
            case R.id.bt_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.bt_dog_type:
                startActivity(DogTypeActivity.class);
                break;
            case R.id.bt_details:
                break;
            case R.id.bt_complex:
                startActivity(ComplexViewActivity.class);
                break;
        }
    }
}

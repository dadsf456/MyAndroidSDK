package com.example.myandroidsdk.ui.activity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.myandroidsdk.R;
import com.tool.cs.common.popup.CommonPopupWindow;
import com.tool.cs.common.utils.ScreenUtils;

import butterknife.OnClick;

public class PopupActivity extends BaseSecondActivity implements CommonPopupWindow.ViewInterface {

    private CommonPopupWindow popup;


    @Override
    protected String getBarTitleText() {
        return "popupDemo";
    }

    @Override
    protected int createContent() {
        return R.layout.activity_popup;
    }

    @Override
    protected void initView() {
        super.initView();

        showPopup();
    }

    private void showPopup() {
        if (popup != null && popup.isShowing())
            return;
        popup = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_address_select)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) (ScreenUtils.getScreenHeight(this) * 0.3f))
                .setOutsideTouchable(true)
                .setViewOnclickListener(this)
                .create();
       // popup.showAtLocation(findViewById(android.R.id.content), Gravity.TOP, 0, 0);
    }

    /**
     * 小一点的布局
     */
    private void showMixPopup() {
        if (popup != null && popup.isShowing())
            return;
        popup = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_address_select)
                .setWidthAndHeight(180,
                        90)
                .setOutsideTouchable(true)
                .setViewOnclickListener(this)
                .create();
        // popup.showAtLocation(findViewById(android.R.id.content), Gravity.TOP, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        view.findViewById(R.id.btn_close).setOnClickListener(v -> popup.dismiss());
    }


    @OnClick({R.id.bt_top, R.id.bt_left, R.id.bt_down, R.id.bt_right, R.id.bt_center,R.id.bt_top1,R.id.bt_top2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_top:
                popup.setAnimationStyle(R.style.top_down_anim);
                popup.showAtLocation(findViewById(android.R.id.content), Gravity.TOP, 0, 0);
                break;
            case R.id.bt_left:
                popup.setAnimationStyle(R.style.left_right_anim);
                popup.showAtLocation(findViewById(android.R.id.content), Gravity.LEFT, 0, 0);
                break;
            case R.id.bt_down:
                popup.setAnimationStyle(R.style.down_top_anim);
                popup.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.bt_right:
                popup.setAnimationStyle(R.style.right_left_anim);
                popup.showAtLocation(findViewById(android.R.id.content), Gravity.RIGHT, 0, 0);
                break;
            case R.id.bt_center:
                popup.setAnimationStyle(R.style.scale_center_anim);
              //  popup.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, ScreenUtils.getScreenWidth(this)/2, ScreenUtils.getScreenHeight(this)/2);
             //   popup.showAsDropDown(view);
                popup.showAtLocation(findViewById(android.R.id.content),Gravity.CENTER,0,0);
                break;
            case R.id.bt_top1:
             // popup测量 布局中match warp无效的
                int[] position = new int[2];
                view.getLocationOnScreen(position);
                popup.setAnimationStyle(R.style.scale_down_up_anim);
                popup.showAtLocation(view, Gravity.NO_GRAVITY, 0,position[1]+view.getMeasuredHeight());
                break;

            case R.id.bt_top2:
                //PopupWindow小坑：无论偏移多大，不会跑出屏幕
                // popup测量 布局中match warp无效的
                int[] position1 = new int[2];
                view.getLocationOnScreen(position1);
                showMixPopup();
                popup.setAnimationStyle(R.style.scale_lefr_right_anim);
                popup.showAtLocation(view, Gravity.NO_GRAVITY, position1[0]+view.getMeasuredWidth(),position1[1]);
                break;
        }
    }
}

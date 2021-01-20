package com.example.myandroidsdk.ui.activity;

import android.view.View;
import android.widget.Button;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.dialog.CommonDialog;
import com.example.myandroidsdk.ui.dialog.InputDialog;
import com.example.myandroidsdk.ui.dialog.PayDialog;
import com.tool.cs.common.utils.MyToast;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogActivity extends BaseSecondActivity {


    @BindView(R.id.bt_top)
    Button btTop;
    @BindView(R.id.bt_left)
    Button btLeft;
    @BindView(R.id.bt_down)
    Button btDown;
    @BindView(R.id.bt_right)
    Button btRight;
    @BindView(R.id.bt_center)
    Button btCenter;
    @BindView(R.id.bt_top1)
    Button btTop1;
    @BindView(R.id.bt_top2)
    Button btTop2;

    @Override
    protected String getBarTitleText() {
        return "dialogDemo";
    }

    @Override
    protected int createContent() {
        return R.layout.activity_popup;
    }

    @Override
    protected void initView() {
        super.initView();
        btTop.setText("1弹出通用确定取消");
        btLeft.setText("2弹出通用确定取消");
        btDown.setText("3弹出通用确定取消");
        btRight.setText("4弹出通用确定取消");
    }


    @OnClick({R.id.bt_top, R.id.bt_left, R.id.bt_down, R.id.bt_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_top:
                CommonDialog dialog = new CommonDialog.Builder()
                        .setTitleText("第一个")
                        .setContentText("确定发布评价吗？")
                        .setConfirmText("发布")
                        .setCancelText("闪了乐乐")
                        .setOnConfirmListener(this::onConfirm1)
                        .create();
                dialog.show(getSupportFragmentManager(), "publish");
                break;
            case R.id.bt_left:
                CommonDialog dialog2 = new CommonDialog.Builder()
                        .setTitleText("第2个")
                        .setContentText("确定发布评价吗？")
                        .setConfirmText("发布")
                        .setCancelText("闪了乐乐")
                        .setOnConfirmListener(this::onConfirm2)
                        .create();
                dialog2.show(getSupportFragmentManager(), "publish");
                break;
            case R.id.bt_down:
                //普通写法
                new PayDialog()
                        .setNumber(100)
                        .setTotal(200)
                        .setOnConfirmListener(new PayDialog.OnConfirmListener() {
                            @Override
                            public void confirm(int pay_type) {
                                MyToast.showToast(DialogActivity.this.getBaseContext(), "点击确定3");
                            }
                        })
                        .show(getSupportFragmentManager(), "");
                break;
            case R.id.bt_right:
                InputDialog builder = new InputDialog.Builder()
                        .setTitleText("标题") //不设置就隐藏布局
                        .setSubTitleText("内容~~~~~~")
                        .setConfirmText("123456")
                        .setOnConfirmListener(new InputDialog.OnConfirmListener() {
                            @Override
                            public void onConfirm(String str) {
                                MyToast.showToast(DialogActivity.this.getBaseContext(), "点击确定4");
                            }
                        })
                        .create();
                builder.show(getSupportFragmentManager(), null);
                break;
        }
    }


    public void onConfirm1() {
        MyToast.showToast(this, "点击确定1");
    }

    public void onConfirm2() {
        MyToast.showToast(this, "点击确定2");
    }
}

package com.example.myandroidsdk.ui.dialog;

import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myandroidsdk.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fxb on 2020/7/17.
 */
public class PayDialog extends BaseDialogFragment {
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.iv_wx)
    ImageView iv_wx;
    @BindView(R.id.iv_zfb)
    ImageView iv_zfb;
    @BindView(R.id.iv_money)
    ImageView iv_money;
    private int number;
    private double total;

  //  @PayType
    private int pay_type;

    public PayDialog setNumber(int number) {
        this.number = number;
        return this;
    }

    public PayDialog setTotal(double total) {
        this.total = total;
        return this;
    }

    public PayDialog setPay_type(int pay_type) {
        this.pay_type = pay_type;
        return this;
    }

    @Override
    protected int createViewByLayoutId() {
        return R.layout.popup_order_pay;
    }

    @Override
    protected float getWidthScale() {
        return 1.0f;
    }

    @Override
    protected void resetLayoutParams(WindowManager.LayoutParams params) {
        super.resetLayoutParams(params);
        params.gravity = Gravity.BOTTOM;
        params.windowAnimations = R.style.down_top_anim;
    }

    @Override
    protected void initDialogFragment() {
        tv_amount.setText(String.valueOf(number));
        tv_total.setText(String.valueOf(total));

//        if (pay_type == PayType.TYPE_BALANCE)
//            iv_money.setSelected(true);
    }

    @OnClick(R.id.ll_wx)
    public void onClickWeChatPay() {
        iv_wx.setSelected(true);
        iv_zfb.setSelected(false);
        iv_money.setSelected(false);

       // pay_type = PayType.TYPE_WX;
    }

    @OnClick(R.id.ll_money)
    public void onClickWeMoneyPay() {
        iv_wx.setSelected(false);
        iv_zfb.setSelected(false);
        iv_money.setSelected(true);

       // pay_type = PayType.TYPE_BALANCE;
    }

    @OnClick(R.id.ll_zfb)
    public void onClickAliPay() {
        iv_wx.setSelected(false);
        iv_zfb.setSelected(true);
        iv_money.setSelected(false);

      //  pay_type = PayType.TYPE_ALI;
    }

    @OnClick(R.id.cv_confirm)
    public void onClickConfirm() {
        if (onConfirmListener != null) {
            onConfirmListener.confirm(pay_type);
        }
        dismissAllowingStateLoss();
    }

    private OnConfirmListener onConfirmListener;

    public PayDialog setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public interface OnConfirmListener {
        void confirm(int pay_type);
    }
}

package com.example.myandroidsdk.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tool.cs.common.utils.MyToast;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.ComplexView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fxb on 2020/6/29.
 * 获取验证码，直接提交验证为修改绑定手机 开启下一级页面为修改用户密码或支付密码
 */
public class ModifyPhoneActivity extends BaseBgActivity {
    public static final int MODIFY_PHONE = 1;//修改绑定手机
    public static final int OTHER_LOGIN = 2;//其他登录绑定手机
    public static final int MODIFY_PWD = 3;//找回密码
    @BindView(R.id.ll_container)
    LinearLayout ll_container;

    @BindView(R.id.et_phone)
    AppCompatEditText et_phone;
    @BindView(R.id.et_code)
    AppCompatEditText et_code;

    @BindView(R.id.cv_confirm)
    ComplexView cv_confirm;

    private int type;
    private boolean isPay;

    @Override
    protected String getBarTitleText() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected int createContent() {
        return R.layout.activity_modify_phone;
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", MODIFY_PHONE);
        isPay = getIntent().getBooleanExtra("isPay", false);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        Observable<CharSequence> observable1 = RxTextView.textChanges(et_phone).skip(1);
        Observable<CharSequence> observable2 = RxTextView.textChanges(et_code).skip(1);

        Observable.combineLatest(observable1, observable2,
                (charSequence1, charSequence2) -> {
                    boolean empty1 = TextUtils.isEmpty(charSequence1);
                    boolean empty2 = TextUtils.isEmpty(charSequence2);

                    phone = charSequence1.toString();
                    code = charSequence2.toString();

                    return (!empty1 && !empty2);
                }).subscribe(aBoolean -> cv_confirm.setEnabled(aBoolean));

        //激活Observable
        et_phone.setText("");
        et_code.setText("");
    }

    @BindView(R.id.cv_send_code)
    ComplexView cv_send_code;

    @OnClick(R.id.cv_send_code)
    public void onClickCode() {
        if (!phone.matches(Constant.REGEX_MOBILE)) {
            showToast(R.string.warning_input_correct_phone);
            return;
        }
        boolean isPay = getIntent().getBooleanExtra("isPay", false);

        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getSendCode(phone, isPay ? Constant.TYPE_PAY_PWD : type==ModifyPhoneActivity.MODIFY_PWD?
                        Constant.TYPE_MODIFY_PWD:Constant.TYPE_INFO_CHANGE)
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::isSuccessful)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        startTimeCountDown();
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast(message);
                    }
                });

    }

    private void startTimeCountDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(60)
                .compose(RxUtils.switchObservableSchedulers())
                .map(aLong -> 59 - aLong)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(Long aLong) {
                        cv_send_code.setEnabled(false);
                        cv_send_code.setText(aLong + "秒后可重发");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        cv_send_code.setEnabled(true);
                        cv_send_code.setText("重新发送");
                    }
                });
    }

    private String phone;
    private String code;

    @OnClick(R.id.cv_confirm)
    public void onClickConfirm() {
        MyToast.showToast(this,"点击了修改");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            finish();
        }
    }
}

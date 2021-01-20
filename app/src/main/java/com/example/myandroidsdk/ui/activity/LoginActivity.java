package com.example.myandroidsdk.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.UserToken;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tool.cs.common.AppManager;
import com.tool.cs.common.utils.LogUtils;
import com.tool.cs.common.utils.MyToast;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.ComplexView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LoginActivity extends BaseBgActivity {
    @BindView(R.id.mStatusBar)
    View mStatusBar;
    @BindView(R.id.tv_login_by_code)
    TextView tv_login_by_code;
    @BindView(R.id.tv_login_by_pwd)
    TextView tv_login_by_pwd;
    @BindView(R.id.et_phone)
    AppCompatEditText et_phone;
    @BindView(R.id.et_password)
    AppCompatEditText et_password;
    @BindView(R.id.iv_login)
    ImageView iv_login;

    @BindView(R.id.cv_confirm)
    ComplexView cv_confirm;

    @BindView(R.id.ll_check)
    LinearLayout ll_check;
    @BindView(R.id.cb_check)
    CheckBox cb_check;
    @BindView(R.id.tv_check)
    TextView tv_check;
    @BindView(R.id.cv_send_code)
    ComplexView cv_send_code;

    private String type;
    private int loginMode;//0-密码登录 1-验证码登录 默认0

    private String phone;
    private String password;
    private String code;


    @Override
    protected String getBarTitleText() {
        return "登录";
    }

    @Override
    protected int createContent() {
        return R.layout.activity_login;
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

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();

        resetLoginTypeUi();

        Observable<CharSequence> observable1 = RxTextView.textChanges(et_phone).skip(1);
        Observable<CharSequence> observable2 = RxTextView.textChanges(et_password).skip(1);

        Observable.combineLatest(observable1, observable2, (charSequence1, charSequence2) -> {
            boolean empty1 = TextUtils.isEmpty(charSequence1);
            boolean empty2 = TextUtils.isEmpty(charSequence2);

            phone = charSequence1.toString();
            if (loginMode == 0) {
                password = charSequence2.toString();
            } else {
                code = charSequence2.toString();
            }

            return !empty1 && !empty2;
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                cv_confirm.setEnabled(aBoolean);
            }
        });
    }

    private void resetLoginTypeUi() {
        if (loginMode == 0) {
            tv_login_by_code.setTextSize(14);
            tv_login_by_code.getPaint().setFakeBoldText(false);
            tv_login_by_code.setTextColor(ContextCompat.getColor(this, R.color.gray_9));
            tv_login_by_pwd.setTextSize(18);
            tv_login_by_pwd.getPaint().setFakeBoldText(true);
            tv_login_by_pwd.setTextColor(ContextCompat.getColor(this, R.color.colorBtnNormalBg));
        } else {
            tv_login_by_code.setTextSize(18);
            tv_login_by_code.getPaint().setFakeBoldText(true);
            tv_login_by_code.setTextColor(ContextCompat.getColor(this, R.color.colorBtnNormalBg));
            tv_login_by_pwd.setTextSize(14);
            tv_login_by_pwd.getPaint().setFakeBoldText(false);
            tv_login_by_pwd.setTextColor(ContextCompat.getColor(this, R.color.gray_9));
        }
    }

    @OnClick(R.id.tv_login_by_code)
    public void onClickLoginByCode() {
        loginMode = 1;
        resetLoginTypeUi();
        iv_login.setImageResource(R.drawable.ic_login_code);
        et_password.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_password.setHint(R.string.hint_input_code);
        cv_send_code.setVisibility(View.VISIBLE);
        et_password.setText("");
    }

    @OnClick(R.id.tv_login_by_pwd)
    public void onClickLoginByPwd() {
        loginMode = 0;

        resetLoginTypeUi();

        iv_login.setImageResource(R.drawable.ic_login_pwd);
        et_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        et_password.setHint(R.string.hint_input_password);
        cv_send_code.setVisibility(View.INVISIBLE);
        et_password.setText("");
    }


    @OnClick(R.id.cv_confirm)
    public void onClickConfirm() {
        if (!cb_check.isChecked()) {
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f);
            ObjectAnimator.ofPropertyValuesHolder(ll_check, scaleX, scaleY)
                    .setDuration(500)
                    .start();
            return;
        }



        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .login(getLoginParams())
                .compose(RxUtils.switchObservableSchedulers())
                .map(new Function<Response<UserToken>, UserToken>() {
                    @Override
                    public UserToken apply(Response<UserToken> userTokenResponse) throws Exception {
                        return userTokenResponse.getData();
                    }
                })
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<UserToken>() {
                    @Override
                    public void onSuccess(UserToken data) {
                        App.getInstance().setUserToken(data);
                        //内存中更新用户信息
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(MainActivity.class);
                        MyToast.showToast(getBaseContext(), "登陆返回数据");
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast(message);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgress(R.string.text_loading_login);
                        //  showProgress2(R.layout.activity_progress,R.string.text_loading_login);
                        //showProgress3(R.layout.activity_progress,R.string.app_name,R.string.text_loading_login);
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                    }
                });



//        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
//                .login1(getLoginParams())
//                .compose(RxUtils.switchObservableSchedulers())
//                .compose(RxUtils.bindEvent(this))
//                .subscribe(new Observer<MyUserToken>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        showProgress(R.string.text_loading_login);
//                    }
//
//                    @Override
//                    public void onNext(MyUserToken myUserToken) {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        hideProgress();
//                    }
//                });
    }

    private Map<String, Object> getLoginParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("binding", phone);
        if (loginMode == 0) {
            params.put("password", password);
        } else {
            params.put("code", code);
            params.put("type", Constant.TYPE_LOGIN_CONFIRM);
        }
        LogUtils.i(TAG, params.toString());
        return params;
    }

    @OnClick(R.id.cv_send_code)
    public void onClickSendCode() {
//        if (!phone.matches(Constant.REGEX_MOBILE)) {
//            showToast(R.string.warning_input_correct_phone);
//            return;
//        }
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getSendCode(phone, Constant.TYPE_LOGIN_CONFIRM)
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

    /**
     * 验证码倒计时
     */
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
}

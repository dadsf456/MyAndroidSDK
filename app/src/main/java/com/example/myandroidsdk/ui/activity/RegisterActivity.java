package com.example.myandroidsdk.ui.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.UserToken;
import com.example.myandroidsdk.ui.dialog.BottomWheelDialog;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tool.cs.common.AppManager;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.ComplexView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fxb on 2020/6/17.
 */
public class RegisterActivity extends BaseBgActivity {
    @BindView(R.id.et_name)
    AppCompatEditText et_name;
    @BindView(R.id.cv_sex)
    ComplexView cv_sex;
    @BindView(R.id.et_phone)
    AppCompatEditText et_phone;
    @BindView(R.id.et_code)
    AppCompatEditText et_code;
    @BindView(R.id.et_password)
    AppCompatEditText et_password;
    @BindView(R.id.et_password_)
    AppCompatEditText et_password_;
    @BindView(R.id.et_invite)
    AppCompatEditText et_invite;
    @BindView(R.id.cv_register)
    ComplexView cv_register;

    private List<String> sexList;

    @Override
    protected String getBarTitleText() {
        return getString(R.string.bar_title_register);
    }

    @Override
    protected int createContent() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        sexList = Arrays.asList(getResources().getStringArray(R.array.sex));
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        Observable<CharSequence> observable1 = RxTextView.textChanges(et_name).skip(1);
        Observable<CharSequence> observable2 = RxTextView.textChanges(cv_sex).skip(1);
        Observable<CharSequence> observable3 = RxTextView.textChanges(et_phone).skip(1);
        Observable<CharSequence> observable4 = RxTextView.textChanges(et_code).skip(1);
        Observable<CharSequence> observable5 = RxTextView.textChanges(et_password).skip(1);
        Observable<CharSequence> observable6 = RxTextView.textChanges(et_password_).skip(1);
        Observable<CharSequence> observable7 = RxTextView.textChanges(et_invite).skip(1);
        Observable.combineLatest(observable1, observable2, observable3, observable4, observable5,
                observable6, observable7, (charSequence1, charSequence2, charSequence3, charSequence4,
                                           charSequence5, charSequence6, charSequence7) -> {
                    boolean empty1 = TextUtils.isEmpty(charSequence1);
                    boolean empty2 = TextUtils.isEmpty(charSequence2);
                    boolean empty3 = TextUtils.isEmpty(charSequence3);
                    boolean empty4 = TextUtils.isEmpty(charSequence4);
                    boolean empty5 = TextUtils.isEmpty(charSequence5);
                    boolean empty6 = TextUtils.isEmpty(charSequence6);

                    nickname = charSequence1.toString();
                    phone = charSequence3.toString();
                    code = charSequence4.toString();
                    password = charSequence5.toString();
                    _password = charSequence6.toString();
                    invite = charSequence7.toString();

                    return !empty1 && !empty2 && !empty3 && !empty4 && !empty5 && !empty6;
                }).subscribe(aBoolean -> cv_register.setEnabled(aBoolean));

        et_name.setText("");
        cv_sex.setText("");
        et_phone.setText("");
        et_code.setText("");
        et_password.setText("");
        et_password_.setText("");
        et_invite.setText("");
    }

    @OnClick(R.id.cv_sex)
    public void onClickCvSex() {
        BottomWheelDialog dialog = new BottomWheelDialog.Builder()
                .setTitleText("")
                .setData(sexList)
                .setPosition(sex == 2 ? 1 : 0)
                .setOnConfirmListener(position -> {
                    if (position == 0) {
                        sex = 1;
                    } else {
                        sex = 2;
                    }
                    cv_sex.setText(sexList.get(position));
                })
                .create();
        dialog.show(getSupportFragmentManager(), "modify_sex");
    }

    @BindView(R.id.cv_send_code)
    ComplexView cv_send_code;

    @OnClick(R.id.cv_send_code)
    public void onClickSendCode() {
        if (!phone.matches(Constant.REGEX_MOBILE)) {
            showToast(R.string.warning_input_correct_phone);
            return;
        }
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getSendCode(phone, Constant.TYPE_REGISTER)
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

    private String nickname;
    private String password;
    private String _password;
    private int sex;
    private String code;
    private String phone;
    private String invite;

    @OnClick(R.id.cv_register)
    public void onClickRegister() {
//        if (!nickname.matches(Constant.REGEX_NICKNAME)) {
//            showToast(R.string.prompt_regex_nickname);
//            return;
//        }
        Map<String, Object> params = new HashMap<>();
        params.put("nickname", nickname);
        params.put("password", password);
        params.put("confirm_password", _password);
        params.put("gender", sex);
        params.put("code", code);
        params.put("binding", phone);
        params.put("type", Constant.TYPE_REGISTER);

        //TODO 邀请码这里待测试
        if (!TextUtils.isEmpty(invite)) {
            Integer value = Integer.valueOf(invite);
            params.put("invite", value);
        }

        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .register(params)//参数
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::getData)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<UserToken>() {
                    @Override
                    public void onSuccess(UserToken data) {
                        //保存token及uid到本地
                        App.getInstance().setUserToken(data);

                        AppManager.getAppManager().finishAllActivity();
                        startActivity(MainActivity.class);
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast(message);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgress(R.string.text_loading_login);
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                    }
                });
    }

}

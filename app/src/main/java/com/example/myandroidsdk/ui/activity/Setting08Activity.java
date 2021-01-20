package com.example.myandroidsdk.ui.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.ComplexView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by fxb on 2020/6/16.
 * 问题反馈
 */
public class Setting08Activity extends BaseSecondActivity {
    @BindView(R.id.et_content)
    AppCompatEditText et_content;
    @BindView(R.id.et_contact)
    AppCompatEditText et_contact;

    @BindView(R.id.cv_confirm)
    ComplexView cv_submit;

    @Override
    protected String getBarTitleText() {
        return getString(R.string.settings_text_08);
    }

    @Override
    protected int createContent() {
        return R.layout.activity_setting_08;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        Observable<CharSequence> observable1 = RxTextView.textChanges(et_content).skip(1);
        Observable<CharSequence> observable2 = RxTextView.textChanges(et_contact).skip(1);
        Observable.combineLatest(observable1, observable2, (charSequence1, charSequence2) -> {
            boolean empty1 = TextUtils.isEmpty(charSequence1);
            boolean empty2 = TextUtils.isEmpty(charSequence2);

            content = charSequence1.toString();
            contact = charSequence2.toString();

            return !empty1 && !empty2;
        }).subscribe(aBoolean -> cv_submit.setEnabled(aBoolean));
        et_content.setText("");
        et_contact.setText("");
    }

    private String content;
    private String contact;

    @OnClick(R.id.cv_confirm)
    public void onClickSubmit() {
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .submitFeedback(0, content, "", contact)
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::isSuccessful)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        showToast("提交成功");
                        finish();
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast(message);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgress(R.string.text_loading_normal);
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                    }
                });
    }
}

package com.example.myandroidsdk.ui.activity;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.UserInfo;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.tool.cs.common.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.reactivex.disposables.Disposable;


/**
 * Name: MeNikeActivity
 * Details:我的昵称
 * Created by PC on 2018/10/15.
 * Update:
 */

public class NicknameActivity extends BaseSecondActivity {
    @BindView(R.id.et_nickname)
    AppCompatEditText et_nickname;
    @BindView(R.id.btn_clear)
    ImageButton btn_clear;

    private String oldNickname;
    private String newNickname;

    @Override
    protected String getBarTitleText() {
        return "修改昵称";
    }

    @Override
    protected String getRightBtnText() {
        return "保存";
    }

    @Override
    protected int createContent() {
        return R.layout.activity_nickname;
    }

    @Override
    protected void initData() {
        oldNickname = App.getInstance().getUserInfo().getNickname();
        if (oldNickname == null)
            oldNickname = "";
    }

    @Override
    protected void initView() {
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newNickname = s.toString();
                if (TextUtils.isEmpty(newNickname))
                    btn_clear.setVisibility(View.INVISIBLE);
                else
                    btn_clear.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_nickname.setText(oldNickname);
        et_nickname.setSelection(oldNickname.length());
    }

    @OnFocusChange(R.id.et_nickname)
    public void onNicknameFocusChange() {
        if (!TextUtils.isEmpty(newNickname) && et_nickname.hasFocus())
            btn_clear.setVisibility(View.VISIBLE);
        else
            btn_clear.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_clear)
    public void onClickClear() {
        et_nickname.setText("");
    }

    @OnClick(R.id.tv_bar_text_btn)
    public void onClickRightBtn() {
        if (TextUtils.isEmpty(newNickname)) {
            showToast("您还没有输入昵称！");
            return;
        }
        if (TextUtils.equals(oldNickname, newNickname)) {
            finish();
            return;
        }
        if (!newNickname.matches(Constant.REGEX_NICKNAME)) {
            showToast(R.string.prompt_regex_nickname);
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("nickname", newNickname);
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getEditInfo(params)
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::isSuccessful)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        showToast("修改成功");
                        setResult(RESULT_OK);
                        UserInfo userInfo = App.getInstance().getUserInfo();
                        userInfo.nickname = newNickname;

                        App.getInstance().setUserInfo(userInfo);
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

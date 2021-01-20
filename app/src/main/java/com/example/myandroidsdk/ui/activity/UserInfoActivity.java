package com.example.myandroidsdk.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.Image;
import com.example.myandroidsdk.ui.dialog.BottomDialog;
import com.example.myandroidsdk.ui.dialog.BottomWheelDialog;
import com.example.myandroidsdk.ui.event.UpdatePhoneEvent;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.example.myandroidsdk.ui.utils.GlideImageLoader;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.AvatarImageView;
import com.tool.cs.common.widget.ComplexView;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


/**
 * Created by fxb on 2020/6/18.
 */
public class UserInfoActivity extends BasePhotoActivity {
    @BindView(R.id.iv_avatar)
    AvatarImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.cv_name)
    ComplexView cv_name;
    @BindView(R.id.cv_phone)
    ComplexView cv_phone;
    @BindView(R.id.cv_sex)
    ComplexView cv_sex;

    private List<String> sexList;

    @Override
    protected String getBarTitleText() {
        return "个人信息";
    }

    @Override
    protected int createContent() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initData() {
        sexList = Arrays.asList(getResources().getStringArray(R.array.sex));
        resetInfo();
    }

    private void resetInfo() {
        GlideImageLoader.loadAvatar(UserInfoActivity.this, iv_avatar, App.getInstance().getUserInfo().avatar_url);
        tvName.setText(App.getInstance().getUserInfo().nickname);
        tvPhone.setText(App.getInstance().getUserInfo().binding);
        cv_name.setText(App.getInstance().getUserInfo().nickname);
        cv_phone.setText(App.getInstance().getUserInfo().binding);
        cv_sex.setText(App.getInstance().getUserInfo().getSex());
    }

    @OnClick(R.id.fl_name)
    public void onClickEditName() {
        startActivityForResult(NicknameActivity.class, 10054);
    }

    @OnClick(R.id.fl_sex)
    public void onClickEditSex() {
        int sex = App.getInstance().getUserInfo().gender;
        BottomWheelDialog dialog = new BottomWheelDialog.Builder()
                .setTitleText("")
                .setData(sexList)
                .setPosition(sex == 2 ? 1 : 0)
                .setOnConfirmListener(position -> {
                    if (position == 0) {
                        modifySex(1);
                    } else {
                        modifySex(2);
                    }
                })
                .create();
        dialog.show(getSupportFragmentManager(), "modify_sex");
    }

    @Override
    public void onBack(View v) {
        setResult(RESULT_OK);
        finish();
    }

    private void modifySex(int sex) {
        //减少不必要请求
        if (sex == App.getInstance().getUserInfo().gender)
            return;
        Map<String, Object> params = new HashMap<>();
        params.put("gender", sex);
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL).getEditInfo(params)
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::isSuccessful)
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        showToast("修改成功");
                        setResult(RESULT_OK);

                        App.getInstance().getUserInfo().gender = sex;
                        resetInfo();
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

    @OnClick(R.id.update_head)
    public void updateHead() {
        if (getSupportFragmentManager().findFragmentByTag("verify") == null) {
            BottomDialog dialog = new BottomDialog.Builder()
                    .addItem(R.string.take_photo)
                    .addItem(R.string.choose_from_album)
                    .setOnItemClickListener(position -> {
                        switch (position) {
                            case 0:
                                openCamera();
                                break;
                            case 1:
                                openAlbum();
                                break;
                            default:
                                break;
                        }
                    })
                    .create();
            dialog.show(getSupportFragmentManager(), "verify");
        }
    }

    @Override
    protected void compressSuccess(String path) {

        uploadImage(path, new OnUploadedListener() {
            @Override
            public void uploaded(Image image) {
                Map<String, Object> params = new HashMap<>();
                params.put("avatar_url", image.url);
                RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                        .getEditInfo(params)
                        .compose(RxUtils.switchObservableSchedulers())
                        .map(Response::isSuccessful)
                        .subscribe(new BaseObserver<Boolean>() {
                            @Override
                            public void onSuccess(Boolean data) {
                                showToast("修改成功");
                                setResult(RESULT_OK);
                                App.getInstance().getUserInfo().avatar_url = image.url;
                                resetInfo();
                                 EventBus.getDefault().post(new UpdatePhoneEvent());
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == 10054) {
//            EventBus.getDefault().post(new UpdatePhoneEvent());
//            resetInfo();
//        }
        //    resetInfo();

    }
}

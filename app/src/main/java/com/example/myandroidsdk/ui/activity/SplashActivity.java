package com.example.myandroidsdk.ui.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.UserToken;
import com.example.myandroidsdk.ui.dialog.CommonDialog;
import com.example.myandroidsdk.ui.global.App;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.utils.SpanTextBuilder;
import com.example.myandroidsdk.ui.utils.Utils;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseActivity;
import com.tool.cs.common.utils.DensityUtil;
import com.tool.cs.common.utils.LogUtils;
import com.tool.cs.common.utils.SpUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * Created by fxb on 2020/7/30.
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.mStatusBar)
    View mStatusBar;
    @BindView(R.id.iv_splash)
    ImageView iv_splash;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        Glide.with(this)
                .asGif()
                .load(R.drawable.res_splash)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<GifDrawable> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource,
                                                   Object model,
                                                   Target<GifDrawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {

                        try {
                            Field gifStateField = GifDrawable.class.getDeclaredField("state");
                            gifStateField.setAccessible(true);
                            Class gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable$GifState");
                            Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
                            gifFrameLoaderField.setAccessible(true);
                            Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");
                            Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder");
                            gifDecoderField.setAccessible(true);
                            Class gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder");
                            Object gifDecoder = gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)));
                            Method getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", int.class);
                            getDelayMethod.setAccessible(true);
                            //设置只播放一次
                            resource.setLoopCount(1);
                            //获得总帧数
                            int count = resource.getFrameCount();
                            int delay = 0;
                            for (int i = 0; i < count; i++) {
                                //计算每一帧所需要的时间进行累加
                                delay += (int) getDelayMethod.invoke(gifDecoder, i);
                            }
                            LogUtils.e(TAG, "delay=" + delay);
                            iv_splash.postDelayed(() -> {
                                if (listener != null) {
                                    listener.gifPlayComplete();
                                }
                            }, 4000);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                }).into(iv_splash);
    }

    private void showDialog() {
        SpannableStringBuilder ssb = new SpanTextBuilder()
                .append("请您务必阅读、充分理解用户协议和隐私政策各条款。您可以阅读《用户协议》和《隐私政策》了解详细信息。如您同意，请点击“同意”开始接受我们的服务")
                .addClickableSpan(span1, 29, 35)
                .addClickableSpan(span2, 36, 42)
                .get();
        CommonDialog dialog = new CommonDialog.Builder()
                .setTitleText("服务协议和隐私政策")
                .setContentText(ssb)
                .setLineSpacing(DensityUtil.dp2px(this, 5))
                .setCancelText("暂不使用", ContextCompat.getColor(this, R.color.colorText))
                .setConfirmText("同意", ContextCompat.getColor(this, R.color.colorBlue))
                .setCanceledOnTouchOutside(false)
                .setOnCancelListener(this::finish)
                .setOnConfirmListener(() -> {
                    SpUtils.getInstance(this).put("is_read", true);
                    startActivity(LoginActivity.class);
                    finish();
                })
                .create();
        dialog.show(getSupportFragmentManager(), "contract");
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

    private ClickableSpan span1 = new ClickableSpan() {
        @Override
        public void onClick(@NonNull View widget) {
            Utils.startHtml(this, "用户协议", Constant.API_TREATY);
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorBlue));//设置颜色
            ds.setUnderlineText(false);//去掉下划线
        }
    };

    private ClickableSpan span2 = new ClickableSpan() {
        @Override
        public void onClick(@NonNull View widget) {
            Utils.startHtml(this, "用户协议", Constant.API_TREATY);
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorBlue));//设置颜色
            ds.setUnderlineText(false);//去掉下划线
        }
    };

    private GifListener listener = new GifListener() {
        @Override
        public void gifPlayComplete() {
            UserToken userToken = App.getInstance().getUserToken();
            boolean is_read = SpUtils.getInstance(SplashActivity.this).getBoolean("is_read");
            if (is_read) {
                if (App.getInstance().getUid() != 0)
                    SplashActivity.this.startActivity(MainActivity.class);
                else
                    SplashActivity.this.startActivity(LoginActivity.class);

                SplashActivity.this.finish();
            } else
                SplashActivity.this.showDialog();
        }
    };

    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }
}

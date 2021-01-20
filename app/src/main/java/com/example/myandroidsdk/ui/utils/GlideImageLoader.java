package com.example.myandroidsdk.ui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.myandroidsdk.R;

/**
 * Created by fxb on 2020/7/6.
 */
public class GlideImageLoader {
    /**
     * 通用加载方式
     *
     * @param context    上下文
     * @param view       所显示的控件
     * @param imgUrl     图片url
     * @param defaultImg 占位图
     * @param errorImg   加载出错的图
     */
    public static void load(Context context, ImageView view, String imgUrl, int rounded,
                            @DrawableRes int defaultImg,
                            @DrawableRes int errorImg) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        if (rounded > 0)
            options.transforms(new CenterCrop(), new RoundedCorners(rounded));
        if (defaultImg != 0)
            options.placeholder(defaultImg);
        if (errorImg != 0)
            options.error(errorImg);

        // 图片加载库采用Glide框架
        RequestBuilder<Drawable> requestBuilder = Glide.with(context).load(imgUrl).apply(options);
        if (rounded > 0) {
            requestBuilder
                    .thumbnail(loadTransform(context, defaultImg, rounded))
                    .thumbnail(loadTransform(context, errorImg, rounded))
                    .into(view);
        } else
            requestBuilder.into(view);
    }

    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId, int radius) {
        return Glide.with(context)
                .load(placeholderId)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(radius)));
    }


    /**
     * 圆角图片加载
     *
     * @param context      上下文
     * @param imageView    图片显示控件
     * @param url          图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage   加载失败后图片
     * @return
     */
    public static void load(Context context, ImageView imageView, String url,
                            int defaultImage,
                            int errorImage,
                            int w,
                            int h,
                            RequestListener<Drawable> listener) {
        //RequestOptions 设置请求参数，通过apply方法设置
        RequestOptions options = new RequestOptions()
                // 但不保证所有图片都按序加载
                // 枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW
                // 默认为Priority.NORMAL
                // 如果没设置fallback，model为空时将显示error的Drawable，
                // 如果error的Drawable也没设置，就显示placeholder的Drawable
                .priority(Priority.NORMAL) //指定加载的优先级，优先级越高越优先加载，
                .placeholder(defaultImage)
                .error(errorImage)
                .dontAnimate()
                // 缓存原始数据
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter();
        if (w > 0 && h > 0)
            options.override(w, h);

        // 图片加载库采用Glide框架
        Glide.with(context).load(url).apply(options)
                .listener(listener)
                .into(imageView);
    }

    /**
     * 加载用户头像
     */
    public static void loadAvatar(Context context, ImageView view, String imgUrl) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .placeholder(R.drawable.ic_avatar_normal)
                .error(R.drawable.ic_avatar_normal)
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context).load(imgUrl).apply(options).into(view);
    }

    /**
     * 加载商品分类图片
     */
    public static void loadCategory(Context context, ImageView view, String imgUrl) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .placeholder(R.drawable.ic_pictures_error)
                .error(R.drawable.ic_pictures_error)
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();

        Glide.with(context).load(imgUrl).apply(options).into(view);
    }

    public static void loadGoodsPic(Context context, ImageView view, String imgUrl) {
        load(context, view, imgUrl, 0, R.drawable.ic_pictures_error, R.drawable.ic_pictures_error);
    }
}

package com.example.myandroidsdk.ui.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.myandroidsdk.ui.global.App;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tool.cs.common.BuildConfig;
import com.tool.cs.common.utils.MapTypeAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by *** on 2019/8/2.
 * 封装网络请求工具类
 */
public class RetrofitUtils {
    private final static String TAG = "RetrofitUtils";

    /**
     * 配置一个Retrofit Builder
     */
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                    }.getType(), new MapTypeAdapter()).create()))//添加gson转换器
            .addConverterFactory(ScalarsConverterFactory.create())//添加字符串转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());//RxJava支持

    /**
     * http超时较短
     */
    public static <S> S createService(Class<S> serviceClass, String baseURL) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)//设置连接超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//设置写入超时时间
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .addNetworkInterceptor(createHttpLoggingInterceptor())//添加日志拦截器
                .addInterceptor(new SignInterceptor())
                .build();

        Retrofit retrofit = builder.client(okHttpClient)
                .baseUrl(baseURL)

                .build();
        return retrofit.create(serviceClass);
    }

    /**
     * 创建Http日志拦截器
     */
    private static HttpLoggingInterceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.e(TAG, message));
        if (BuildConfig.DEBUG) {
            //显示日志
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }

    private static class SignInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();
            if (original.method().equals("GET")) {
            } else if (original.method().equals("POST")) {
            }
            Request.Builder builder = original.newBuilder();



//            //请求头，固定写法
//          //  builder.header("user-agent",getCurrentUserAgent());
//            original=builder.build();
//            //调试用的应用请求，打印，可以输出请求头的参数
//            System.out.println("=====================================================");
//            long t1 = System.nanoTime();
//            String requestHeader = String.format(">>>>>Sending request %s on %s%n%s",
//                    original.url(), chain.connection(), original.headers());
//            LogUtils.e("HeadersInterceptor",requestHeader);
//
//
//
//            Response response = chain.proceed(original);
//            long t2 = System.nanoTime();
//            String responseHeader =   String.format(">>>>>Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers());
//            LogUtils.e("HeadersInterceptor",responseHeader);
//            System.out.println("=====================================================");


            if (!TextUtils.isEmpty(App.getInstance().getToken())) {
                builder.addHeader("token", App.getInstance().getToken());//可以这样加没有问题啊
            } else {
                //  builder.addHeader("access_token", "App.getInstance().getToken()");//可以这样加没有问题啊
            }

            return chain.proceed(builder.build());
        }
    }



}

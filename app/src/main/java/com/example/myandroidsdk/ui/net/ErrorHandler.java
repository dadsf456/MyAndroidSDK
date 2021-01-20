package com.example.myandroidsdk.ui.net;

import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by fxb on 2019-09-30.
 */
public class ErrorHandler {

    public static String handleException(Throwable e) {
        Log.i("tag", "e.toString = " + e.toString());
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case 401://未经授权
                case 403://禁止访问
                case 404://页面不存在
                case 408://请求超时
                case 500://服务器错误
                case 502://网关错误
                case 503://服务不可用
                case 504://网关超时
                default:
                    break;
            }
            return "网络错误";
        } else if (e instanceof SocketTimeoutException) {
            return "连接超时";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
            /*|| e instanceof ParseException*/) {
            return "解析出错";
        } else if (e instanceof ConnectException) {
            return "连接失败";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            return "证书认证失败";
        } else {
            return "未知错误";
        }
    }
}

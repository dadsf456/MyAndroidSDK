package com.example.myandroidsdk.ui.net;

/**
 * Created by *** on 2019/8/2.
 */
public class ErrorException extends Exception {
    private int code;
    private String msg;

    public ErrorException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

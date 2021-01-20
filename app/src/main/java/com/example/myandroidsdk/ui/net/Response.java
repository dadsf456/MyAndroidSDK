package com.example.myandroidsdk.ui.net;

/**
 * Created by fxb on 2020/6/19.
 */
public class Response<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() throws ErrorException {
        if (!isSuccessful())
            return null;
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() throws ErrorException {
        return isSuccessful() ? data : null;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccessful() throws ErrorException {
        if (code != 0)
            throw new ErrorException(code, msg);
        return true;
    }
}

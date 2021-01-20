package com.example.myandroidsdk.ui.net;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by *** on 2019-09-01.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onComplete();

        Log.e("BaseObserver", e.toString());

        String message;
        if (e instanceof ErrorException) {
            ErrorException error = (ErrorException) e;

            message = error.getMsg();
        } else {
            message = ErrorHandler.handleException(e);
        }
        onFailed(message);
    }

    @Override
    public void onComplete() {

    }

    public void onSuccess(T data) {


    }

    public void onFailed(String message) {

    }
}

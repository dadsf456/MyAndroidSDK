package com.example.myandroidsdk.ui.net;

import android.os.Handler;

import com.tool.cs.common.utils.LogUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by weiyang on 2018/11/22.
 * http请求队列
 */
public class HttpQueue {
    private final String TAG = "HttpQueue";
    private Handler handler;
    private Queue<Runnable> queue;

    private int pos;

    public HttpQueue() {
        handler = new Handler();
        queue = new LinkedList<>();
    }

    public void addHttpRequest(Runnable runnable) {
        queue.add(runnable);
    }

    public void execute() {
        if (!queue.isEmpty()) {
            LogUtils.e(TAG, "执行第" + (++pos) + "网络请求");
            Runnable poll = queue.poll();
            handler.post(poll);
        } else {
            if (pos == 0)
                LogUtils.e(TAG, "当前队列为空");
            else
                LogUtils.e(TAG, "队列执行结束");
        }
    }

    public int current() {
        return pos;
    }

    /**
     * 判断队列是否执行完
     */
    public boolean isComplete() {
        return pos != 0 && queue.isEmpty();
    }

    /**
     * 重置状态
     */
    public void reset() {
        pos = 0;
        clear();
    }

    public void clear() {
        if (!queue.isEmpty()) {
            queue.clear();
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

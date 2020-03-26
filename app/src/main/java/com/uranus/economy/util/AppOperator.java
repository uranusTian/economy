package com.uranus.economy.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池  主线程 和 子线程执行
 */
public final class AppOperator {
    private static Handler mHandler;
    private static ExecutorService EXECUTORS_INSTANCE;

    public static Executor getExecutor() {
        if (EXECUTORS_INSTANCE == null) {
            synchronized (AppOperator.class) {
                if (EXECUTORS_INSTANCE == null) {
                    EXECUTORS_INSTANCE = Executors.newFixedThreadPool(6);
                }
            }
        }
        return EXECUTORS_INSTANCE;
    }


    public static void runOnMainThread(Runnable runnable) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(runnable);
    }

    public static void runOnMainThreadDelay(Runnable runnable,long time) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.postDelayed(runnable,time);
    }

    public static void runOnMainThreadRemove(Runnable runnable) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
            return;
        }
        mHandler.removeCallbacks(runnable);
    }

    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }


}


package com.uranus.economy.util;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class RxViewUtils {
    private static final int DELAYED_TIME = 1000;//防抖动时长,单位毫秒

    /**
     * 防止重复点击
     *
     * @param target   目标view
     * @param consumer 点击回调
     */
    public static void setOnClickListeners(@NonNull final View target, Consumer<View> consumer) {
        setOnClickListeners(target, consumer, DELAYED_TIME);
    }

    /**
     * 防止重复点击，支持自定义防抖动时长
     *
     * @param target      目标view
     * @param consumer    点击回调
     * @param delayedTime 延迟时间
     */
    @SuppressLint("CheckResult")
    public static void setOnClickListeners(@NonNull final View target, Consumer<View> consumer, int delayedTime) {
        Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(final ObservableEmitter<View> emitter) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(target);
                        }
                    }
                };
                target.setOnClickListener(listener);
            }
        })
                .throttleFirst(delayedTime, TimeUnit.MILLISECONDS)
                .subscribe(consumer);
    }
}

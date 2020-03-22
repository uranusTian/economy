package com.uranus.economy.network.callback;

import android.text.TextUtils;

import com.uranus.economy.R;
import com.uranus.economy.bean.BaseBean;
import com.uranus.economy.constant.Constant;
import com.uranus.economy.manager.UserManager;
import com.uranus.economy.network.ApiException;
import com.uranus.economy.util.LogUtils;
import com.uranus.economy.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class RequestCallback<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * 把onNext的名字变成 onSuccess，更容易理解
     */
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    /**
     * 请求失败时，对象转换，并且对外暴漏
     */
    @Override
    public void onError(Throwable e) {
        LogUtils.d1("tian","onError Throwable : " + e);
        ApiException exception;
        if (e instanceof ApiException) {
            exception = (ApiException) e;
            //产生ApiException的异常有两种情况：
            //1、请求接口失败，服务器异常
            //2、请求接口成功，但是dataInfo == null，导致json解析失败，但是接口是成功
            //所以此处把第二种情况改为请求成功
            if (exception.getCode() == Constant.Code.SUCCESS) {
                onSuccess(null);
                return;
            }
            switch (((ApiException) e).getCode()) {
                case Constant.Code.LOGIN_INVALID://登录失效/过期/被踢
                    ToastUtils.showShort(R.string.login_expired);
                    UserManager.getInstance().logout();
                    return;
            }

        } else {
            BaseBean<String> baseBean = new BaseBean<>();
            baseBean.code = Constant.Code.LOCATION_FAILED;
            if (e != null && !TextUtils.isEmpty(e.getMessage())) {
                baseBean.data = e.getMessage();
            }
            exception = new ApiException(Constant.Code.LOCATION_FAILED,e.getMessage());
        }
        onError(exception);
    }




    //--------------------------对外暴露的成功和失败结果--------------------------
    //--------------------------对外暴露的成功和失败结果--------------------------
    //--------------------------对外暴露的成功和失败结果--------------------------
    public abstract void onSuccess(T t);

    public abstract void onError(ApiException e);
}

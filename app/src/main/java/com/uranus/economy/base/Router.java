package com.uranus.economy.base;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import me.jessyan.autosize.utils.LogUtils;

public class Router {
    public static final String INTENT_DATA = "route_data";//intent传递数据的key
    private AppCompatActivity baseActivity;

    public Router(AppCompatActivity baseActivity) {
        this.baseActivity = baseActivity;
    }


    /**
     * @param className Activity名称
     */
    public void router(Class className) {
        Intent intent = new Intent(baseActivity, className);
        try {
            baseActivity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtils.e("ActivityNotFoundException:" + className.getName());
        }
    }

    /**
     * @param className   Activity名称
     * @param requestCode requestCode
     */
    public void router(Class className, int requestCode) {
        Intent intent = new Intent(baseActivity, className);
        try {
            baseActivity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            LogUtils.e("ActivityNotFoundException:" + className.getName());
        }
    }

    /**
     * @param className Activity名称
     * @param bundle    跳转需携带的数据
     */
    public void router(Class className, Bundle bundle) {
        Intent intent = new Intent(baseActivity, className);
        intent.putExtra(INTENT_DATA, bundle);
        try {
            baseActivity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtils.e("ActivityNotFoundException:" + className.getName());
        }
    }

    /**
     * @param className   Activity名称
     * @param bundle      跳转需携带的数据
     * @param requestCode requestCode
     */
    public void router(Class className, Bundle bundle, int requestCode) {
        Intent intent = new Intent(baseActivity, className);
        intent.putExtra(INTENT_DATA, bundle);
        try {
            baseActivity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            LogUtils.e("ActivityNotFoundException:" + className.getName());
        }
    }

    /**
     * 获取携带的内容，必须是通过router(Bundle bundle,....)跳转的才可以获取
     * @return 携带的数据
     */
    public Bundle getIntent(){
        Intent intent = baseActivity.getIntent();
        if(intent == null){
            return null;
        }
        return intent.getBundleExtra(INTENT_DATA);
    }
}

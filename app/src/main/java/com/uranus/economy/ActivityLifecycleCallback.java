package com.uranus.economy;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * -----------
 * Activity生命周期监听管理
 * 在Application进行注册
 * 包括功能：Activity活动列表管理，退出App
 */
public class ActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private WeakReference<Activity> mCurrentActivity;
    private static List<Activity> activityList = new ArrayList<>();

    private int isForget;


    private static class ActivityLifecycleHolder {
        private static final ActivityLifecycleCallback INSTANCE = new ActivityLifecycleCallback();
    }

    public static ActivityLifecycleCallback getInstance() {
        return ActivityLifecycleHolder.INSTANCE;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityList.add(activity);
    }


    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);

    }


    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (mCurrentActivity != null) {
            currentActivity = mCurrentActivity.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        mCurrentActivity = new WeakReference<>(activity);
    }

    /**
     * 退出App
     */
    public void exitApp() {
        closeAllActivity();
//        AppUtils.exitApp(App.context);
    }

    /**
     * 关闭所有Activity
     */
    public void closeAllActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

}


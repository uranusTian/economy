package com.uranus.economy.base;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.uranus.economy.ActivityLifecycleCallback;
import com.uranus.economy.util.CrashHandler;
import com.uranus.economy.util.LogUtils;


public class App extends MultiDexApplication {
    private final static String TAG = App.class.getSimpleName();

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.d("tian","DEBUG VALUE : " + BuildConfig.DEBUG);
        //内存泄漏安装
        LeakCanary.install(this);
        //启动Log打印
        LogUtils.setEnable(true);
        //突破655535方法数量限制
        MultiDex.install(this);
        LogUtils.d1(TAG, "--onCreate--");
        //异常捕获
        CrashHandler.getInstance().init(this);
        //数据库初始化
//        DBManager.getInstance();
        //Activity生命周期监听注册
        registerActivityLifecycleCallbacks(ActivityLifecycleCallback.getInstance());
    }


    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    public boolean isMainProcess() {
        return getApplicationContext().getPackageName().equals(getCurrentProcessName());
    }


    /**
     * 获取当前进程名
     */
    private String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }
}

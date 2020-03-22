package com.uranus.economy.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.uranus.economy.util.LogUtils;


public class App extends MultiDexApplication {
    private final static String TAG = App.class.getSimpleName();

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //根据项目模式区分一些操作
        if (BuildConfig.DEBUG) {
            //内存泄漏安装
            LeakCanary.install(this);
            //启动Log打印
            LogUtils.setEnable(true);
        }
        //Release模式
        else {
            //关闭Log
            LogUtils.setEnable(false);
        }
        //突破655535方法数量限制
        MultiDex.install(this);
        LogUtils.d1(TAG, "--onCreate--");
        //异常捕获
        CrashHandler.getInstance().init(this);
        //数据库初始化
//        DBManager.getInstance();
        //Activity生命周期监听注册
        registerActivityLifecycleCallbacks(ActivityLifecycleCallback.getInstance());
        if (isMainProcess()){
            registerBroadcast();
            initIXinTui();
        }
    }

    public static void initIXinTui(){
        PushSdkApi.register(context, BuildConfig.IXINTUI_APP_KEY, BuildConfig.IXINTUI_CHANNEL, AppUtils.getVersionName(context),
                BuildConfig.XIAOMI_APPID, BuildConfig.XIAOMI_AppKey, BuildConfig.MEIZU_APPID, BuildConfig.MEIZU_APPKEY);
    }


    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ixintui.action.MESSAGE");
        intentFilter.addAction("com.ixintui.action.RESULT");
        intentFilter.addAction("com.ixintui.action.notification.CLICK");
        PushReceiver pushReceiver = new PushReceiver();
        registerReceiver(pushReceiver, intentFilter);

        //targesdkversion大于等于26的时候需要动态注册AndroidMainfest中的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.ixintui.action.BROADCAST");
        filter.addAction("android.intent.action.BOOT_COMPLETED");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.intent.action.REBOOT");
        filter.addAction("android.intent.action.ACTION_SHUTDOWN");
        filter.addCategory("android.intent.category.HOME");
        filter.addAction("android.intent.action.MEDIA_MOUNTED");
        filter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        Receiver receiver = new Receiver();
        registerReceiver(receiver, filter);

        IntentFilter ixintuifilter = new IntentFilter();
        ixintuifilter.addAction("android.intent.action.PACKAGE_REMOVED");
        ixintuifilter.addDataScheme("package");
        registerReceiver(receiver,ixintuifilter);
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

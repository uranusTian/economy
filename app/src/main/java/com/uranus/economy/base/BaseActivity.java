package com.uranus.economy.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.uranus.economy.R;
import com.uranus.economy.constant.Constant;
import com.uranus.economy.manager.UserManager;
import com.uranus.economy.util.AppUtils;
import com.uranus.economy.util.ScreenUtil;
import com.uranus.economy.util.StatusBarUtils;
import com.uranus.economy.views.BaseToolBar;
import com.uranus.economy.views.WaterMarkView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//所有Activity的基类
public abstract class BaseActivity extends AppCompatActivity {

    public Router router;//跳转路由
    private Unbinder bind;
    public static boolean IS_ACTIVE = true;
    //Activity结束时，上个页面的出现动画
    protected int activityCloseEnterAnimation;
    //Activity结束时，本页面的退出动画
    protected int activityCloseExitAnimation;
    private Fragment mFragment;
    private View progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //有些机型，会存在先走Splash，后走App的情况，这样会导致App.context为空，此处进行判断
        if (App.context == null) {
            App.context = getApplicationContext();
        }
        if (initBundle(getIntent().getExtras())) {
            router = new Router(this);
            setContentView(getLayoutId());
            setStatusBarStyle(Constant.BarStyle.TRAN_BLACK);
            //ButterKnife的控件绑定注册，不需要在子Activity再进行注册
            bind = ButterKnife.bind(this);
            if (isShowWaterMark()){
                showWaterMark();
            }
            //如果Activity的布局当中包括BaseToolBar，则进行初始化
            if (findViewById(R.id.toolbar) != null) {
                toolBarSetting((BaseToolBar) findViewById(R.id.toolbar));
            }
            initData();
            initFinishAnimation();
        } else {
            finish();
        }
    }

    private void showWaterMark() {
        FrameLayout content = (FrameLayout)findViewById(android.R.id.content);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        content.addView(new WaterMarkView(this),params);
    }

    protected boolean isShowWaterMark() {
        return true;
    }

    protected void toolBarSetting(BaseToolBar toolBar) {
    }

    /**
     * 指定Layout布局
     *
     * @return layout的R文件地址
     */
    public int getLayoutId() {
        return 0;
    }

    /**
     * 无需重写Activity的onCreate，数据初始化在此方法执行
     */
    protected abstract void initData();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

//    protected void initStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//    }

    /**
     * 注册Event，需要的Activity直接进行调用即可
     */
    protected void registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 设置状态栏的颜色
     */
    protected void setStatusBarStyle(int barStyle) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        switch (barStyle) {
            case Constant.BarStyle.HIDE://直接全部隐藏
                StatusBarUtils.setStatusBarVisibility(this, false);
                break;
            case Constant.BarStyle.MESSAGE://消息主题
                StatusBarUtils.setStatusBarVisibility(this, true);
                StatusBarUtils.setStatusBarColor(this, AppUtils.getColor(R.color.c_6ecbf3));
                StatusBarUtils.setStatusBarLightMode(this, true);
                break;
            case Constant.BarStyle.USER://用户主题
                StatusBarUtils.setStatusBarVisibility(this, true);
                StatusBarUtils.setStatusBarColor(this, AppUtils.getColor(R.color.c_8e65f0));
                StatusBarUtils.setStatusBarLightMode(this, false);
                break;
            case Constant.BarStyle.TRAN_BLACK://深色字体的透明状态栏
                StatusBarUtils.setStatusBarVisibility(this, true);
                StatusBarUtils.setStatusBarAlpha(this, 0);
                StatusBarUtils.setStatusBarLightMode(this, true);
                break;
            case Constant.BarStyle.TRAN_WHITE://浅色字体的透明状态栏
                StatusBarUtils.setStatusBarVisibility(this, true);
                StatusBarUtils.setStatusBarAlpha(this, 0);
                StatusBarUtils.setStatusBarLightMode(this, false);
                break;
            case Constant.BarStyle.CHAT:
                StatusBarUtils.setStatusBarVisibility(this, true);
                StatusBarUtils.setStatusBarColor(this, AppUtils.getColor(R.color.c_e0e0e0));
                StatusBarUtils.setStatusBarLightMode(this, true);
                break;
        }
    }

    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mFragment != null) {
                    transaction.hide(mFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mFragment != null) {
                    transaction.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            transaction.commit();
        }
    }

    @SuppressWarnings("unused")
    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
        }
    }

    /**
     * 获取style中的退出动画
     * 注意！！！！！使用style配置Activity进出动画时，Activity B退出时，会使用上个页面的退出动画，导致动画不匹配。
     * 所以此处将Activity B的退出动画获取出来，强行使用Activity B的退出动画！！
     */
    protected void initFinishAnimation() {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});

        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);

        activityStyle.recycle();

        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});

        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

        activityStyle.recycle();
    }

    /**
     * 是否在前台
     *
     * @return isOnForeground APP是否在前台
     */
    protected boolean isOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        assert activityManager != null;
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    protected void showActivityProgress() {
        if (progress == null) {
            progress = LayoutInflater.from(this).inflate(R.layout.include_activity_progressbar, null);
//            CircularProgressDrawable drawable = new CircularProgressDrawable(this);
//            drawable.setStyle(CircularProgressDrawable.LARGE);
//            drawable.setColorSchemeColors(AppUtils.getColor(R.color.colorPrimary));
//            drawable.start();
//            progress.setBackground(drawable);
        } else {
            // 避免重复添加的bug
            if (progress.getParent() != null || progress.getVisibility() == View.VISIBLE) {
                return;
            }
        }
        FrameLayout parent = (FrameLayout)getWindow().getDecorView().findViewById(android.R.id.content);
        int width = ScreenUtil.dp2px(30f);
        FrameLayout.LayoutParams la = new FrameLayout.LayoutParams(width, width);
        la.gravity = Gravity.CENTER;
        progress.setLayoutParams(la);
        parent.addView(progress);
    }

    protected void hideActivityProgress() {
        if (progress != null && progress.getParent()!= null) {
            FrameLayout parent = (FrameLayout)getWindow().getDecorView().findViewById(android.R.id.content);
            parent.removeView(progress);
            progress=null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        IS_ACTIVE = isOnForeground();
    }

    @Override
    public void finish() {
        super.finish();
        //注释掉activity本身的过渡动画
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    @Override
    protected void onDestroy() {
        if (bind != null) {
            bind.unbind();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }
}

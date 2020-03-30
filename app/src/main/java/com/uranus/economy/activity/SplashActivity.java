package com.uranus.economy.activity;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.manager.UserManager;
import com.uranus.economy.util.AppOperator;

public class SplashActivity extends BaseActivity {

    private static final int DELAY_TIME = 1000;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean isShowWaterMark() {
        return true;
    }

    @Override
    protected void initData() {
        routerActivity(DELAY_TIME);
    }

    public void routerActivity(long delayTime) {

        AppOperator.runOnMainThreadDelay(new Runnable() {
            @Override
            public void run() {
                if (UserManager.getInstance().isLogin()){
                    router.router(MainActivity.class);
                }else {
                    router.router(LoginActivity.class);
                }
                SplashActivity.this.finish();
            }
        }, delayTime);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

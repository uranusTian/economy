package com.uranus.economy.activity;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.SystemClock;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.nav.NavFragment;
import com.uranus.economy.nav.NavigationButton;
import com.uranus.economy.util.ToastUtils;


public class MainActivity extends BaseActivity implements NavFragment.OnNavigationClickListener {
    private NavFragment mNavBar;
    private long mBackPressedTime = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavFragment) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this, manager, R.id.main_container, this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initData();
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {
    }

    @Override
    public void onSelect(NavigationButton navigationButton) {

    }

    @Override
    public void onBackPressed() {
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            finish();
        } else {
            mBackPressedTime = curTime;
            ToastUtils.showShort(R.string.tip_double_click_exit);
        }
    }

}

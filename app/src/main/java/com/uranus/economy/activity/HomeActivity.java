package com.uranus.economy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.bean.User;
import com.uranus.economy.bean.UserInfo;
import com.uranus.economy.manager.UserManager;
import com.uranus.economy.network.ApiException;
import com.uranus.economy.network.AppRequest;
import com.uranus.economy.network.callback.RequestCallback;
import com.uranus.economy.util.AppUtils;
import com.uranus.economy.util.KeyboardUtils;
import com.uranus.economy.util.Md5Utils;
import com.uranus.economy.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.uranus.economy.Global.GET_USER_INFO;
import static com.uranus.economy.Global.REQUEST_LOGIN;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.normal_level)
    protected TextView normalLevel;

    @BindView(R.id.total_score)
    protected TextView totalScore;

    @BindView(R.id.normal_level_layout)
    protected LinearLayout normalLevelLayout;

    private boolean canClick = false;

    @OnClick({R.id.normal_level_layout})
    @Override
    public void onClick(View v) {
        if(!canClick){
            return;
        }
        switch (v.getId()){
            case R.id.normal_level_layout:
                router.router(SearchTrueActivity.class);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        initUserInfo();
    }

    private void initUserInfo() {
        String serialNumber = android.os.Build.SERIAL;
        UserInfo curUser = AppUtils.getUserInfo(getApplicationContext());
        if(curUser.id > 0){
            normalLevel.setText("关卡：" + curUser.cur_level);
            totalScore.setText("当前积分：" + curUser.score);
            return;
        }
//        加密md5的方法
//        Md5Utils.md5(pwd)
        AppRequest.getUserInfo(GET_USER_INFO, 0, serialNumber,
                new RequestCallback<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        canClick = true;
                        normalLevel.setText("关卡：" + userInfo.cur_level);
                        totalScore.setText("当前积分：" + userInfo.score);
                        AppUtils.saveUserInfo(getApplicationContext(),userInfo);
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(R.string.fetch_data_error);
                    }
                });
    }
}

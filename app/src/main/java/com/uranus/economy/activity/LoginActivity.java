package com.uranus.economy.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uranus.economy.ActivityLifecycleCallback;
import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.bean.User;
import com.uranus.economy.manager.UserManager;
import com.uranus.economy.network.ApiException;
import com.uranus.economy.network.AppRequest;
import com.uranus.economy.network.callback.RequestCallback;
import com.uranus.economy.util.AppUtils;
import com.uranus.economy.util.KeyboardUtils;
import com.uranus.economy.util.LogUtils;
import com.uranus.economy.util.Md5Utils;
import com.uranus.economy.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.uranus.economy.Global.REQUEST_LOGIN;
import static com.uranus.economy.constant.Constant.Code.CODE_IDENTITY_ERROR;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.mUserName)
    protected EditText mUserName;

    @BindView(R.id.mPassWord)
    protected EditText mPassWord;

    @BindView(R.id.mLogin)
    protected Button mLogin;

    private long mBackPressedTime;
    private int num = 0;
    private boolean isOpen = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
//        showVerifyCode();
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        User saveUser = AppUtils.getUser(this.getApplicationContext());
        if (saveUser!=null&&!TextUtils.isEmpty(saveUser.no)){
            mUserName.setText(saveUser.no);
        }
        if (TextUtils.isEmpty(AppUtils.getToken(this.getApplicationContext()))){
            LogUtils.d1("demo","LoginActivity get token==null");
        }
    }

    private void checkInput(){
        String name = mUserName.getText().toString();
        String pwd = mPassWord.getText().toString();

        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
            mLogin.setEnabled(false);
        }else {
            mLogin.setEnabled(true);
        }
    }

    @OnClick({R.id.mLogin,R.id.ll_login_view_root})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mLogin:
                toLogin();
                break;
            case R.id.ll_login_view_root:
                //键盘收起
                if (KeyboardUtils.isSoftInputVisible(this)){
                    KeyboardUtils.hideSoftInput(this);
                    checkInput();
                }
                break;
        }
    }

    private void toLogin() {
        if (isLoading){
            return;
        }
        isLoading = true;
        String name = mUserName.getText().toString();
        String pwd = mPassWord.getText().toString();

        router.router(MainActivity.class);
        LoginActivity.this.finish();
        AppRequest.login(REQUEST_LOGIN, name, Md5Utils.md5(pwd),
                new RequestCallback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        isLoading = false;
                        UserManager.getInstance().loginSuccess(user);
                        AppUtils.saveUser(LoginActivity.this.getApplicationContext(), user);
                        router.router(MainActivity.class);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onError(ApiException e) {
                        isLoading = false;
                        ToastUtils.showShort(R.string.account_password_error);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            ActivityLifecycleCallback.getInstance().closeAllActivity();
        } else {
            mBackPressedTime = curTime;
            ToastUtils.showShort(R.string.tip_double_click_exit);
        }
    }

}
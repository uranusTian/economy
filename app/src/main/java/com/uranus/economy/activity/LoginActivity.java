package com.uranus.economy.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.multidex.BuildConfig;

import com.uranus.economy.MainActivity;
import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.bean.User;
import com.uranus.economy.util.AppUtils;
import com.uranus.economy.util.KeyboardUtils;
import com.uranus.economy.util.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.mUserName)
    protected EditText mUserName;

    @BindView(R.id.mPassWord)
    protected EditText mPassWord;

    @BindView(R.id.mVerificationCode)
    protected EditText mVerificationCode;

    @BindView(R.id.mVerificationImage)
    protected ImageView mVerificationImage;

    @BindView(R.id.mLogin)
    protected Button mLogin;

    @BindView(R.id.mLogin_test)
    protected Button mLoginTest;

    @BindView(R.id.verify_refresh)
    protected TextView refreshVerify;

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
        mVerificationCode.addTextChangedListener(new TextWatcher() {
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
        mLoginTest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isOpen = true;
                return true;
            }
        });
        if (TextUtils.isEmpty(AppUtils.getToken(this.getApplicationContext()))){
            LogUtils.d1("ixintui_demo","LoginActivity get token==null");
//            App.initIXinTui();
        }
    }

//    private void showVerifyCode(){
////        ImageLoad.displayImg(LoginActivity.this, mVerificationImage, REQUEST_FETCH_VERIFY+"?time="+System.currentTimeMillis());
//        AppRequest.fetchVerify(REQUEST_FETCH_VERIFY, new VerifyCallback() {
//            @Override
//            public void onSuccess(final Bitmap bitmap) {
//                AppOperator.runOnMainThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mVerificationImage.setImageBitmap(bitmap);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
//                ToastUtils.showShort(R.string.download_identify_code_error);
//            }
//        });
//        refreshVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImageLoad.displayImg(LoginActivity.this, mVerificationImage, REQUEST_FETCH_VERIFY);
//            }
//        });
//    }

    private void checkInput(){
        String name = mUserName.getText().toString();
        String pwd = mPassWord.getText().toString();
        String verify = mVerificationCode.getText().toString();

        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(verify)){
            mLogin.setEnabled(false);
        }else {
            mLogin.setEnabled(true);
        }
    }

    @OnClick({R.id.mVerificationImage,R.id.mLogin,R.id.mLogin_test,R.id.ll_login_view_root})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mVerificationImage:
//                showVerifyCode();
                break;
            case R.id.mLogin:
                toLogin();
                break;
            case R.id.mLogin_test:
                if (BuildConfig.DEBUG && isOpen) {
                    num +=1;
                    if (num>=2){
                        mUserName.setText("EMP00003");
                        mPassWord.setText("888888");
                    }
                }
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
        if (1==2){
            router.router(MainActivity.class);
            LoginActivity.this.finish();
            return;
        }
        String name = mUserName.getText().toString();
        String pwd = mPassWord.getText().toString();
        String verify = mVerificationCode.getText().toString();
        String token = AppUtils.getToken(LoginActivity.this.getApplicationContext());
        AppRequest.login(REQUEST_LOGIN, name, Md5Utils.md5(pwd), verify , token,
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
                        switch (e.getCode()){
                            case CODE_IDENTITY_ERROR:
                                ToastUtils.showShort(R.string.identify_code_error);
                                showVerifyCode();
                                break;
                            default:
                                ToastUtils.showShort(R.string.account_password_error);
                                break;
                        }
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
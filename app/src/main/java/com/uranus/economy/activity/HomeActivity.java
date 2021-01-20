package com.uranus.economy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.bean.AuthResult;
import com.uranus.economy.bean.User;
import com.uranus.economy.bean.UserInfo;
import com.uranus.economy.manager.UserManager;
import com.uranus.economy.network.ApiException;
import com.uranus.economy.network.AppRequest;
import com.uranus.economy.network.callback.RequestCallback;
import com.uranus.economy.util.AppUtils;
import com.uranus.economy.util.KeyboardUtils;
import com.uranus.economy.util.Md5Utils;
import com.uranus.economy.util.OrderInfoUtil2_0;
import com.uranus.economy.util.ToastUtils;

import java.util.Map;

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

    @OnClick({R.id.normal_level_layout,R.id.zhifubao_login})
    @Override
    public void onClick(View v) {
        if(!canClick){
            return;
        }
        switch (v.getId()){
            case R.id.normal_level_layout:
                router.router(SearchTrueActivity.class);
                break;
            case R.id.zhifubao_login:
                authV2();
//                router.router(SearchTrueActivity.class);
                break;
        }
    }

    public static final String APPID = "2021002120644174";
    public static final String PID = "2088212540437882";
    public static final String TARGET_ID = "shimming_01";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCuIY2LGe0rPLXdxOQ" +
            "+vLD8lLQKZLd0ULN6b2oK3ACBnbjb6wgDjeatdYR/xLeQw8lFgVhyIa6YHWuj8QWo++Kdv1vnNFoXzfv9hIb05mh293jOEHXEbRB+w" +
            "lm1r7g4Lry1Eqdg4Sq2sGZ1J8svVBPHaKWIRvUKJEr72AEb9rR+KnJR70FoF1UgIQ3ucimEx/xNhDanMdbI02Y+GEnefS32jxEcikK" +
            "BC+ViB2NxFIlloG5uOK6ooLPS4AiI30VNqWL9rKU+E04kLrL6EqZRA4pirJluyCyksJi7LNeNMu3Ai/Be+ChIUzVvqG6dN8UCpeLmvM" +
            "ZEGucJubYyN+rjxSPZAgMBAAECggEARMSne5nfmkW9pTmO0l/+naG0gK5J7Tu5xc6eWG+OG6JIUUG+xniR80sH6tWrkRZhNHvijCBVL" +
            "WnhCruy+ouPcbETAHoiYmhM7lAaZDliZClToj3J5KsV6pmiiCg/XqDGkdQuGgqSrdd4fygaDDTuIsBnLyymtTykCFXECmEh4vkEkdRl" +
            "bpTZHQNZKfQ0ZafWxF9CBtFOBBnIriDuDCMmR2GeKeq1Metlmt47JrtfGguVzx2i/naJJNdKwyf/CRjeDzdRz5rh0DLSjHCRE85AJ6M" +
            "m6bn2ykHIwqE7yQYUKbPFCli++JJD0WUPb76ChQMj+vYlVI8JFJ76KcZKJ75xAQKBgQDs5Brh+ZExrXWlrf3P3TPM2nUloAz/2paMs0" +
            "fqiATubJ2CxaTiKl0zTffnkKvzSQBbwlfztkdRRsMK6gX0kXSlVoerQ7ZU/0QMWnMuNYSkZZ3+aZtA1GWGczmPNbtkr9ijN8KKHagjz" +
            "eHGCEQrD9YcUjqVwLRaZlq+wxEDIRn+CQKBgQC8LW3oq5GYZqhCl/T4Sd5nT93D/ubBUI6ApHSU78nxsOpR/SfkegwDLqtEhExHpFz5" +
            "OYXo3CN2fmtzTLnXFZUbk9LPscut9ipNX8FcVGVH7kRUCIyuXkiMjM/UN+BURN3ORQr+gx8bVBpUGrf0rIJq/O/bx5X4eUYr/N3Kme5" +
            "rUQKBgFXyxRYsGFPv3XmJUUdzuHGg6VQHZ/AEfQ77lSIwy6k920AxOYudV0dgCIwJ33lbiTfBoWuZPPLuO6HgGt0LUUkTxhGLdcINA8" +
            "F/p76iSEflXpeJ9XxmE5fegonFMwJrEqzVdE3sSK8E6d+R7Dm7ezG3W+4thsIt1MrVt3xtLCMRAoGAP0LGgEGtYJ6iUkDjidTLf8/Jr" +
            "eoziSmbvoOK7cbvDOF85LNGhfjBsRAvY4lWW39WAjLkYbQf9+XoGYrhrv0Cc5LufAQ5hPIMppZaWr/EjLn9HoMBSF1J5nYwKBfgwnOk" +
            "XYO4fnd2LanIcB3LWeXksjL2L9yY/j9G5+LKbSHylQECgYEAmbA3dXR3bQZccLc9Z34Ply4CjLa+U64O57XIvDGFUtzz9um5SgqrZSGE" +
            "GhJW3eC3rK2lwK2sMu6qElHnsN26WZD/DcHYVxIC2DbATy6q+xEYYY7e6Pb/6dpJTdKNXuGA73LzAqSw1pwTez7MgYiXHGwXJSxBq3g" +
            "i/G3m7C9sZTE=";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(HomeActivity.this, "授权成功" + authResult);
                        Log.d("tian","authResult : " + authResult);

                    } else {
                        // 其他状态值则为授权失败
                        showAlert(HomeActivity.this, "授权失败" + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public void authV2() {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            showAlert(this, "缺少支付宝相关参数");
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(HomeActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
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
        String serialNumber = AppUtils.getDeviceSerial();
        UserInfo curUser = AppUtils.getUserInfo(getApplicationContext());
        ToastUtils.showShort("serialNumber : " + serialNumber);
        if(curUser.id > 0){
            canClick = true;
            normalLevel.setText("关卡：" + curUser.cur_level);
            totalScore.setText("当前积分：" + curUser.score);
            return;
        }

        Log.d("tian","serialNumber : " + serialNumber);
//        加密md5的方法
//        Md5Utils.md5(pwd)
        AppRequest.getUserInfo(GET_USER_INFO, curUser.id, serialNumber,
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
                        Log.d("tian","ApiException : " + e.getMessage());
                        ToastUtils.showShort(R.string.fetch_data_error);
                    }
                });
    }
}

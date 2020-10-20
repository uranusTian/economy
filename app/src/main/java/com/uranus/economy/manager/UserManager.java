package com.uranus.economy.manager;

import android.content.Intent;

import com.uranus.economy.ActivityLifecycleCallback;
import com.uranus.economy.activity.LoginActivity;
import com.uranus.economy.base.App;
import com.uranus.economy.bean.User;
import com.uranus.economy.network.AppRequest;
import com.uranus.economy.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    //单例
    private static final UserManager ourInstance = new UserManager();

    private User loginUser;

    private UserManager() {

    }


    public int getUserId() {
        if (loginUser != null) {
            return loginUser.id;
        }
        return -1;
    }

    public String getUserUnique() {
        if (loginUser != null && loginUser.unique != null) {
            return loginUser.unique;
        }
        return "";
    }

    public static UserManager getInstance() {
        return ourInstance;
    }


    public User getUser() {
        return loginUser;
    }

    public void setUser(User loginUser) {
        this.loginUser = loginUser;
        if (loginUser != null) {
            SPUtils.getInstance().put(SPUtils.USER_INFO, loginUser);
        }
    }

    /**
     * 用户是否登录
     *
     * @return true为登录，false为未登录
     */
    public boolean isLogin() {
        return loginUser != null;
    }

    /**
     * 退出登录
     */
    public void logout() {
        loginUser = null;
//        channelAccountBeans.clear();
//        if (ActivityLifecycleCallback.getInstance().getCurrentActivity() instanceof LoginActivity){
//            return;
//        }
        ActivityLifecycleCallback.getInstance().closeAllActivity();
        //跳转登录页
        Intent intent = new Intent(App.context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.context.startActivity(intent);
    }


    /**
     * 登录成功回调
     */
    public void loginSuccess(User loginUser) {
        //保存用户信息到内存
        setUser(loginUser);
        //请求通用资源信息
//        ResourceManager.getInstance().requestResources();
        //请求相关信息
//        requestUserReference();
    }


//    /**
//     * 登录成功回调
//     */
//    public List<ChannelAccountBean> getMyBindAccount() {
//        return channelAccountBeans;
//    }

//    /**
//     * 请求自己的设置配置信息
//     */
//    private void requestUserReference() {
//        //请求用户媒体账号列表
//        loadUserPlatformAccount();
//    }

//    public void loadUserPlatformAccount() {
//        //请求用户媒体账号列表
//        AppRequest.getUserPlatformAccount(new RequestCallback<List<ChannelAccountBean>>() {
//            @Override
//            public void onSuccess(List<ChannelAccountBean> datas) {
//                if (datas != null) {
//                    channelAccountBeans.clear();
//                    channelAccountBeans.addAll(datas);
//                    EventBusUtils.post(new LoadUserPlatformAccountSuccess());
//                }
//            }
//
//            @Override
//            public void onError(ApiException e) {
//                ToastUtils.showShort(R.string.network_error);
//            }
//        });
//    }
}

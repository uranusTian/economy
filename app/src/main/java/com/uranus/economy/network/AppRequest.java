package com.uranus.economy.network;

import com.google.gson.JsonObject;
import com.uranus.economy.bean.User;
import com.uranus.economy.manager.HttpManager;
import com.uranus.economy.network.callback.RequestCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AppRequest {
    /**
     * 用户名密码登录
     *
     * @param url        具体请求地址，请求地址是通过接口获取到的
     * @param name       用户名
     * @param pwd        密码
     * @param subscriber 回调
     */
    public static void login(String url, String name, String pwd, RequestCallback<User> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("pwd", pwd);

        HttpManager.getInstance()
                .getService()
                .loginByEmail(getRequestBody(params))
                .map(new HttpManager.HttpRequestFunc<User>())
                .compose(RxHelper.<User>applySchedulers())
                .subscribe(subscriber);
    }


    public static RequestBody getRequestBody(Map<String, Object> bodys) {
        JsonObject jsonObject = new JsonObject();
        if (bodys != null) {
            for (String key : bodys.keySet()) {
                if (bodys.get(key) instanceof Boolean){
                    jsonObject.addProperty(key, (boolean)bodys.get(key));
                }else {
                    jsonObject.addProperty(key, bodys.get(key).toString());
                }
            }
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return requestBody;
    }
}

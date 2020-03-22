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
     * @param verify     验证码
     * @param token      用于推送消息
     * @param subscriber 回调
     */
    public static void login(String url, String name, String pwd, String verify, String token, RequestCallback<User> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("pwd", pwd);
        params.put("verify", verify);
        params.put("token", token);

        HttpManager.getInstance()
                .getService()
                .loginByEmail(getRequestBody(params))
                .map(new HttpManager.HttpRequestFunc<User>())
                .compose(RxHelper.<User>applySchedulers())
                .subscribe(subscriber);
    }

//    /**
//     * 登出
//     */
//    public static void logout(RequestCallback<String> subscriber) {
//        HttpManager.getInstance()
//                .getService()
//                .logout(getCommonRequestBody())
//                .map(new HttpManager.HttpRequestFunc<String>())
//                .compose(RxHelper.<String>applySchedulers())
//                .subscribe(subscriber);
//    }


//    /**
//     * 获取验证码
//     *
//     * @param url        具体请求地址，请求地址是通过接口获取到的
//     * @param subscriber
//     */
//    public static void fetchVerify(String url, VerifyCallback subscriber) {
//        HttpManager.getInstance()
//                .getService()
//                .fetchVerify(url)
//                .enqueue(subscriber);
//    }

//    /**
//     * 获取task列表
//     */
//    public static void fetchTaskList(int pagesize, int pagenum, boolean is_finish, RequestCallback<TaskList> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("pagesize", pagesize);
//        params.put("pagenum", pagenum);
//        params.put("is_finish", is_finish);
//        params.put("employ_id", UserManager.getInstance().getUserId());
//
//        HttpManager.getInstance()
//                .getService()
//                .fetchTaskList(getRequestBody(params))
//                .map(new HttpManager.HttpRequestFunc<TaskList>())
//                .compose(RxHelper.<TaskList>applySchedulers())
//                .subscribe(subscriber);
//    }
//    /**
//     * 获取task列表
//     */
//    public static void fetchRankList(int type, RequestCallback<RankList> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("type", type);
//        params.put("employ_id", UserManager.getInstance().getUserId());
//
//        HttpManager.getInstance()
//                .getService()
//                .fetchRankList(getRequestBody(params))
//                .map(new HttpManager.HttpRequestFunc<RankList>())
//                .compose(RxHelper.<RankList>applySchedulers())
//                .subscribe(subscriber);
//    }

//    /**
//     * 文件上传
//     * @param file 文件地址
//     * @param callback 回调
//     */
//    public static void uploadFile(int task_id, String title, int platform_account_id, int id, int type, File file, UploadCallback<String> callback) {
////        Map<String, Object> params = new HashMap<>();
////        params.put("id",id);
////        params.put("task_id",task_id);
////        params.put("type",type);
////        params.put("platform_account_id",platform_account_id);
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        if (file!=null){
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
//            //通过该行代码将RequestBody转换成特定的FileRequestBody
//            UploadBody body = new UploadBody<>(requestBody, callback);
//            builder.addFormDataPart("result_img", file.getName(), body);
//        }
//        builder.addFormDataPart("id",id+"");
//        builder.addFormDataPart("task_id",task_id+"");
//        builder.addFormDataPart("type",type+"");
//        builder.addFormDataPart("title",title);
//        builder.addFormDataPart("platform_account_id",platform_account_id+"");
//        builder.setType(MultipartBody.FORM);
//        MultipartBody build = builder.build();
//        HttpManager.getInstance()
//                .getService()
//                .uploadFile(build)
//                .map(new HttpManager.HttpRequestFunc<String>())
//                .compose(RxHelper.<String>applySchedulers())
//                .subscribe(callback);
//    }
//
//
//    /**
//     * 绑定账号
//     */
//    public static void bindAccount(int platformId, String account, String nickname,RequestCallback<String> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("platform_id", platformId);
//        params.put("nickname", nickname);
//        params.put("account", account);
//        params.put("employ_id", UserManager.getInstance().getUserId());
//
//        HttpManager.getInstance()
//                .getService()
//                .bindAccount(getRequestBody(params))
//                .map(new HttpManager.HttpRequestFunc<String>())
//                .compose(RxHelper.<String>applySchedulers())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 账号唯一性检查
//     */
//    public static void checkAccount(String account, int platform_id, RequestCallback<String> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("account", account);
//        params.put("platform_id", platform_id);
//
//        HttpManager.getInstance()
//                .getService()
//                .checkAccount(getRequestBody(params))
//                .map(new HttpManager.HttpRequestFunc<String>())
//                .compose(RxHelper.<String>applySchedulers())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 更新账号
//     */
//    public static void updateAccount(int id, int platformId, String nickname, String account, int status, RequestCallback<String> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", id);
//        params.put("status", status);
//        params.put("platform_id", platformId);
//        params.put("nickname", nickname);
//        params.put("account", account);
//        params.put("employ_id", UserManager.getInstance().getUserId());
//
//        HttpManager.getInstance()
//                .getService()
//                .updateAccount(getRequestBody(params))
//                .map(new HttpManager.HttpRequestFunc<String>())
//                .compose(RxHelper.<String>applySchedulers())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 媒体平台列表
//     */
//    public static void getPlatformList(RequestCallback<List<PlatformBean>> subscriber) {
//        HttpManager.getInstance()
//                .getService()
//                .getPlatformList()
//                .map(new HttpManager.HttpRequestFunc<List<PlatformBean>>())
//                .compose(RxHelper.<List<PlatformBean>>applySchedulers())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 媒体账号
//     */
//    public static void getUserPlatformAccount(RequestCallback<List<ChannelAccountBean>> subscriber) {
//        HttpManager.getInstance()
//                .getService()
//                .getUserPlatformAccounts(getCommonRequestBody())
//                .map(new HttpManager.HttpRequestFunc<List<ChannelAccountBean>>())
//                .compose(RxHelper.<List<ChannelAccountBean>>applySchedulers())
//                .subscribe(subscriber);
//    }
//
//
//    public static RequestBody getCommonRequestBody() {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("employ_id", UserManager.getInstance().getUserId());
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
//        return requestBody;
//    }

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

package com.uranus.economy.network;

import com.uranus.economy.manager.UserManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * -----------
 * 公共参数封装
 * 所有请求都会携带此类声明的参数
 */
public class PublicParams implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        //放在URL中的请求参数
        HttpUrl.Builder authorizedUrlBuilder = request.url()
                .newBuilder();
        //添加统一参数 如手机唯一标识符,token等
//                .addQueryParameter("X-Client-Imei", DeviceUtils.getAndroidID())//客户端的唯一码
//                .addQueryParameter("X-Client-Type", "200")//客户端类型:200Android、202Android_Pad
//                .addQueryParameter("X-Client-Version", BuildConfig.VERSION_NAME)//客户端版本号
//                .addQueryParameter("X-Client-App", "1")//应用类型:1PiE
//                .addQueryParameter("X-Token", UserManger.getInstance().getToken());//登录用户的Token

        //放在Header中的公共参数
        Request newRequest = request.newBuilder()
                //对所有请求添加请求头
//                .addHeader("X-Client-Type", "200")//客户端类型:200Android、202Android_Pad
//                .addHeader("X-Client-Imei", DeviceUtils.getAndroidID())//客户端的唯一码
//                .addHeader("X-Client-Version", BuildConfig.VERSION_NAME)//客户端版本
//                .addHeader("X-Client-App", "1")//应用类型:1PiE
//                .addHeader("X-Token", UserManger.getInstance().getToken())//登录用户的Token
                .addHeader("id", UserManager.getInstance().getUserId()+"")//登录用户的Id
                .addHeader("unique", UserManager.getInstance().getUserUnique())
                .addHeader("devicetype", 1+"")//客户端类型:1Android、2ios
                .method(request.method(), request.body())
                .url(authorizedUrlBuilder.build())
                .build();
        return chain.proceed(newRequest);
    }
}


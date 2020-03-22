package com.uranus.economy.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.uranus.economy.base.App;

public class SPUtils {


    //--------------------------------Key--------------------------------
    //--------------------------------Key--------------------------------
    //--------------------------------Key--------------------------------
    //是否自动登录
    public static final String AUTO_LOGIN = "auto_login";
    //用户登录成功后，保存用户信息，之后会自动登录
    public static final String USER_INFO = "user_info";
    //是否需要显示新手引导
    public static final String KEY_SHOWED_USER_GUIDE = "key_showed_user_guide";


    //--------------------------------单例--------------------------------
    //--------------------------------单例--------------------------------
    //--------------------------------单例--------------------------------
    private SharedPreferences sp;

    private static final SPUtils ourInstance = new SPUtils();

    public static SPUtils getInstance() {
        return ourInstance;
    }

    private SPUtils() {
        /*
         * name：sp文件名称
         * MODE_PRIVATE:操作模式，该类型为私有数据，只能被应用本身访问，写入的内容会进行覆盖操作
         */
        sp = App.context.getSharedPreferences("economy_sp", Context.MODE_PRIVATE);
    }

    //--------------------------------Get--------------------------------
    //--------------------------------Get--------------------------------
    //--------------------------------Get--------------------------------
    public String getString(String key) {
        return sp.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultBoolean) {
        return sp.getBoolean(key, defaultBoolean);
    }

    public long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public float getFloat(String key) {
        return sp.getFloat(key, 0);
    }

    public <T> T getObject(String key, Class<T> cls) {
        String s = getString(key);
        try {
            return GsonUtil.GsonToBean(s, cls);
        } catch (Exception ex) {
            return null;
        }
    }

    //--------------------------------Put--------------------------------
    //--------------------------------Put--------------------------------
    //--------------------------------Put--------------------------------
    public void put(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void put(String key, long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }


    public void put(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void put(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void put(String key, float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void put(String key, Object obj) {
        String s = GsonUtil.GsonString(obj);
        put(key, s);
    }
}


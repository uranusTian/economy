package com.uranus.economy.bean;

import com.uranus.economy.constant.Constant;

public class BaseBean<T> {

    public int code;
    public T data;
    public long timestamp;
    public String msg;
    public String version;


    public boolean requestSuccess(){
        return code == Constant.Code.SUCCESS;
    }
}


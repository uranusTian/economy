package com.uranus.economy.convert;

import com.google.gson.annotations.SerializedName;
import com.uranus.economy.constant.Constant;

public class HttpStatus {
    @SerializedName("code")
    private int mCode;
    @SerializedName("msg")
    private String mMessage;

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    /**
     * API是否请求失败
     *
     * @return 失败返回true, 成功返回false
     */
    public boolean isCodeInvalid() {
        return mCode != Constant.Code.SUCCESS;
    }
}

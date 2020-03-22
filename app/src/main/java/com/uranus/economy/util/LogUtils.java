package com.uranus.economy.util;

import android.util.Log;

public class LogUtils {
    private static boolean ENABLE = true;
    public static void setEnable(boolean enable) {
        LogUtils.ENABLE = enable;
    }

    public static void v1(String tag, String mess) {
        if (ENABLE) {
            Log.v(tag, mess);
        }
    }

    public static void d1(String tag, String mess) {
        if (ENABLE) {
            Log.d(tag, mess);
        }
    }
}

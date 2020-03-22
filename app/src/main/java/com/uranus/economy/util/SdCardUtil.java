package com.uranus.economy.util;

import android.os.Environment;
import android.text.TextUtils;

import com.uranus.economy.base.App;

import java.io.File;

public class SdCardUtil {
    private static final String PATH_CRASH = "crash";
    private static final String PATH_USER = "user";
    private static final String PATH_IM = "im";
    private static final String PATH_IM_PIC = "pics";
    private static final String HTTP_CACHE = "HttpCache";

    private static String rootPath;

    /**
     * 获取程序的缓存根目录
     * <p>
     * 当设备挂在了 sdcard 缓存目录在 sdcard  下
     * <p>
     * 如果没有挂载 sdcard 缓存目录在 /data 目录下
     * <p>
     * <p>
     * <li> /sdcard/Android/data/<application package>/cache </>
     * <li> /data/data/<application package>/cache </>
     */
    public static String getRootCachePath() {
        if (TextUtils.isEmpty(rootPath)) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                File f = App.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                if (f == null) {
                    File cacheDir = App.context.getExternalCacheDir();
                    if(cacheDir == null){
                        cacheDir = App.context.getCacheDir();
                    }
                    rootPath = cacheDir.getPath();
                } else {
                    rootPath = f.getPath();
                }
            } else {
                rootPath = App.context.getFilesDir().getPath();
            }
        }
        return rootPath;
    }

    public static String getPathCrash() {
        String resultPath = getRootCachePath() + File.separator + PATH_CRASH;
        checkDir(resultPath);
        return resultPath;
    }

    public static String getHttpCache() {
        String resultPath = getRootCachePath() + File.separator + HTTP_CACHE;
        checkDir(resultPath);
        return resultPath;
    }

    public static String getPathUser() {
        String resultPath = getRootCachePath() + File.separator + PATH_USER;
        checkDir(resultPath);
        return resultPath;
    }

    public static String getPathTempPic() {
        String resultPath = getRootCachePath() + File.separator + PATH_IM_PIC;
        checkDir(resultPath);
        return resultPath;
    }

    private static void checkDir(String resultPath) {
        File dir = new File(resultPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}

package com.uranus.economy.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.multidex.BuildConfig;

import com.uranus.economy.base.App;
import com.uranus.economy.bean.User;
import com.uranus.economy.bean.UserInfo;

import java.util.List;
import java.util.Locale;

public class AppUtils {

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    // 两次点击按钮之间的点击间隔不能少于800毫秒
    private static final int MIN_CLICK_DELAY_TIME = 700;
    private static long lastClickTime;

    /**
     * 防重复点击
     *
     * @return
     */
    public static boolean isNotFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = curClickTime;
            flag = true;
        }
        return flag;
    }
    /**
     * 判断某个意图是否存在
     */
    public static boolean isHaveIntent(String intentName) {
        PackageManager packageManager = App.context.getPackageManager();
        Intent intent = new Intent(intentName);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static int getColor(int resource) {
        return App.context.getResources().getColor(resource);
    }

    public static String getString(int resource) {
        return App.context.getResources().getString(resource);
    }
//    public static String getPicUrl(String url) {
//        return BuildConfig.HOST_URL_ADDRESS+url;
//    }

    public static String getFormatString(int resource,Object... args) {
        return String.format(App.context.getResources().getString(resource),args);
    }

    public static String[] getStringArray(int resource) {
        return App.context.getResources().getStringArray(resource);
    }

    public static void exitApp(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                List<ActivityManager.AppTask> list = manager.getAppTasks();
                if (list != null) {
                    for (ActivityManager.AppTask task : list) {
                        task.finishAndRemoveTask();
                    }
                }
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        }
        System.exit(0);

    }


    /**
     * 是否是小米
     */
    public static boolean isXiaoMi() {
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("xiaomi");
    }

    /**
     * 是否是华为
     */
    public static boolean isHuaWei() {
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("huawei");
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 启动到应用商店app详情界面
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;

            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //com.android.vending   GP的包名
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 跳转第三方应用详情界面
     */
    public static boolean launchThirdApp(String scheme) {
        try {
            Uri uri = Uri.parse(scheme);//今日头条
//            Uri uri = Uri.parse("snssdk143://detail?groupid=6664348467722191364");//今日头条
//            Uri uri = Uri.parse("snssdk141://detail?groupid=6673639928963793416");//今日头条
//            Uri uri = Uri.parse("newsapp://doc/EAIFV6M40001875P");//网易新闻
//            Uri uri = Uri.parse("sinaweibo://detail?dispMeIfNeed=1&mblogid=4351220028072617");//新浪微博
//            Uri uri = Uri.parse("qqnews://article_9527?nm=20190403A06FPW00&chlid=news_news_top");//腾讯新闻
//            Uri uri = Uri.parse("yidian://deeplink?deep_data={\"deep_message\":{\"action_method\":\"CLICK_DOC\",\"docid\":\"0LZpLBdQ\"}}");//一点资讯
//            Uri uri = Uri.parse("comifengnewsclient://call?type=doc&id=https://api.iclient.ifeng.com/ipadtestdoc?aid=ucms_7lL8wqx2VZb");//凤凰新闻
//            Uri uri = Uri.parse("qnreading://article_detail?nm=20190312A16X7U00");//天天快报
//            Uri uri = Uri.parse("zhihu://answers/606815059");//知乎
//            Uri uri = Uri.parse("sohunews://pr/news://newsId=353280001");//搜狐新闻   分享的链接里面有一个n（n353280001）只要后面的数字
            //澎湃新闻需要请求接口  动态获取 urlscheme
//            Uri uri = Uri.parse("app.thepaper.cn://demo/a?params=OpeYjFeuKbvqKbd9LqvxOGeVHi6emuL5WLyzV6YIclb/B+e4J5Zy+2v/cOYy8wnfLsctzQXiPXwG4+YeIcD/Of7Efw6+7G5O9+k4xT1+35Q=");//澎湃
//            Uri uri = Uri.parse("sinafinance://type=17&url=https://cj.sina.cn/article/norm_detail?url=https://finance.sina.com.cn/china/gncj/2019-03-26/doc-ihsxncvh5614636.shtml");//新浪财经
//            Uri uri = Uri.parse("tbpb://tieba.baidu.com//tid=6080034192");//贴吧
//            Uri uri = Uri.parse("xueqiu://7379392644/123733407");//雪球股票

//            String params = "baiduboxapp://utils?action=sendIntent&minver=7.4&params=%7B%22intent%22%3A%22intent%3A%23Intent%3Baction%3Dcom.baidu.searchbox.action.HOME%3Bpackage%3Dcom.baidu.searchbox%3BS.targetCommand%3D%257B%2522mode%2522%253A%25220%2522%252C%2522intent%2522%253A%2522intent%253A%2523Intent%253BS.toolbaricons%253D%25257B%252522toolids%252522%25253A%25255B%2525224%252522%25252C%2525221%252522%25252C%2525222%252522%25252C%2525223%252522%25255D%25257D%253Bcomponent%253Dcom.baidu.searchbox%252F.home.feed.FeedDetailActivity%253BS.menumode%253D2%253BS.sfrom%253Dfeed%253BS.context%253D%25257B%252522nid%252522%25253A%252522";
//            String s1 = "news_9761420508600113048";
//            String p1 = "%252522%25257D%253BS.ch_url%253Dhttps%25253A%25252F%25252Fmbd.baidu.com%25252Fnewspage%25252Fdata%25252Flandingreact%25253FpageType%25253D2%252526sourceFrom%25253DlandingShare%252526nid%25253D";
//            String s2 = "%253Bend%2522%252C%2522min_v%2522%253A%252216787968%2522%257D%3Bend%22%7D";
//
//            Uri uri = Uri.parse(params+s1+p1+s1+s2);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            // 当前应用的版本名称
            String versionName = info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        prefs.edit().putString("token", token).apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return prefs.getString("token", "");
    }

    public static void saveUser(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //通过edit()方法创建一个SharePreferences.Editor类的实例对象
        SharedPreferences.Editor editor =prefs.edit();
        editor.putString("name", user.name);
        editor.putInt("id", user.id);
        editor.putString("no", user.no);
        editor.putString("last_login_time", user.last_login_time);
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        User user = new User();
        user.name = prefs.getString("name", "");
        user.id = prefs.getInt("id", -1);
        user.no = prefs.getString("no", "");
        user.last_login_time = prefs.getString("last_login_time", "");
        return user;
    }

    public static void saveUserInfo(Context context, UserInfo userInfo) {
        SharedPreferences prefs = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        //通过edit()方法创建一个SharePreferences.Editor类的实例对象
        SharedPreferences.Editor editor =prefs.edit();
        editor.putString("device_num", userInfo.device_num);
        editor.putInt("id", userInfo.id);
        editor.putInt("cur_level", userInfo.cur_level);
        editor.putInt("score", userInfo.score);
        editor.apply();
    }

    public static UserInfo getUserInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        UserInfo user = new UserInfo();
        user.device_num = prefs.getString("device_num", "");
        user.id = prefs.getInt("id", -1);
        user.cur_level = prefs.getInt("cur_level", 1);
        user.score = prefs.getInt("score", 0);
        return user;
    }

}
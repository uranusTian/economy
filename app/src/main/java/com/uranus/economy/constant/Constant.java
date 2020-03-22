package com.uranus.economy.constant;

public interface Constant {
    /**
     * 状态栏风格
     */
    interface BarStyle {
        //消息风格：字体为深色，背景为浅蓝色
        int MESSAGE = 101;
        //用户风格：字体为浅色，背景为紫色
        int USER = 103;
        //深色字体透明
        int TRAN_BLACK = 104;
        //浅色字体透明
        int TRAN_WHITE = 105;
        //全部隐藏
        int HIDE = 102;
        //白色状态栏
        int CHAT = 106;

    }

    /**
     * 接口结果的Code码
     */
    interface Code {
        //接口请求成功
        int SUCCESS = 0;
        //本地产生异常
        int LOCATION_FAILED = -666;
        //登录失效
        int LOGIN_INVALID =401;
        //设备被禁用
        int DEVICE_BAN = 10019;
        //账号被禁用
        int ACCOUNT_BAN = 10020;
        //验证码不正确
        int CODE_IDENTITY_ERROR = 10002;
    }

    interface UploadFileType {
        int FILE_TYPE_HEAD_PIC = 1;//头像
        int FILE_TYPE_NINE_PICS = 2;//相册9张图片
        int FILE_TYPE_IMAGE = -1;//图片
        int FILE_TYPE_AUDIO = -2;//音频
    }
}

package com.uranus.economy;

public class Global {
    public static final String REQUEST_HOST = BuildConfig.HOST_URL_ADDRESS;
    public static final String REQUEST_HOST_PRE = BuildConfig.HOST_URL_ADDRESS+"/api";
    public static final String REQUEST_LOGIN = REQUEST_HOST_PRE + "/app/login/check";
    public static final String REQUEST_FETCH_VERIFY = REQUEST_HOST_PRE + "/app/login/verify";
    public static final String REQUEST_FETCH_TASKLIST =REQUEST_HOST_PRE +  "/app/task/list";
    public static final String REQUEST_FETCH_PLATFORM_ACCOUNTS =REQUEST_HOST_PRE +  "/app/platform/account";
    public static final String REQUEST_FETCH_PLATFORM_LIST = REQUEST_HOST_PRE + "/app/platform/list";
    public static final String REQUEST_LOGOUT = REQUEST_HOST_PRE + "/app/login/logout";
    public static final String REQUEST_UPDATE_PLATFORM_ACCOUNT = REQUEST_HOST_PRE + "/app/platform/updateAccount";
    public static final String REQUEST_CHECK_PLATFORM_ACCOUNT = REQUEST_HOST_PRE + "/app/platform/checkUnique";
    public static final String REQUEST_BIND_PLATFORM_ACCOUNT = REQUEST_HOST_PRE + "/app/platform/bindAccount";
    public static final String REQUEST_TASK_SUBMIT = REQUEST_HOST_PRE + "/app/task/submit";
    public static final String REQUEST_FETCH_RANKLIST = REQUEST_HOST_PRE + "/app/task/chart";
    public static final String REQUEST_AGREEMENT_URL = REQUEST_HOST_PRE + "/app/agreement/index";

    public static final String SHIM_HOST = "http://39.98.181.182:7095/";
    public static final String GET_USER_INFO = SHIM_HOST + "airplane/user/getUserInfo";
}

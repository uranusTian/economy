package com.uranus.economy.network;

import com.uranus.economy.bean.BaseBean;
import com.uranus.economy.bean.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.uranus.economy.Global.REQUEST_FETCH_TASKLIST;
import static com.uranus.economy.Global.REQUEST_LOGIN;

/**
 * 接口声明，所有的接口都在此处声明即可
 * 由于Lamma的接口地址是通过接口来获取的，不是固定的，所以需要动态传递URL，不能使用@POST(url) or @GET(url)
 * <p>
 * 注解解释：
 * GET(url):声明该请求为GET请求，url为接口的请求地址，因为我们的请求地址是通过key获取的，所以可以将url设置为参数，直接写GET即可
 * POST(url)：声明为POST请求
 * Query(key)：GET请求的参数声明，用这个方式声明的参数，将会拼接到URL后面，例：http://www.lamma.com?key=123 。key为括号中的内容
 * QueryMap：GET请求的参数集合声明，多个参数可用该注解
 * FieldMap：表单集合，用于POST请求的参数传递，多个参数时可以使用此方式，需要和FormUrlEncoded注解同时使用
 * Field(key):表单单个参数，用于POST请求的参数传递，用于参数较少时，需要和FormUrlEncoded注解同时使用
 */
public interface RequestService {
////    如果url地址格式为http://xxxx?key，需要直接将参数拼到key的地方时，需使用该方式
//    /message/obj/hi/read/{userId}
//    Observable<BaseBean> test2(@Url String url,@Path("userId") String id);
////    如果该接口需要缓存，设置缓存时间，不需要则不设置，设置完成后，在无网时才会使用缓存
//    @Headers("Cache-Control: max-stale=" + Integer.MAX_VALUE)
//    @GET("data/{type}/10/{pageNum}")
//    Observable<BaseBean<EmailLoginBean>> test(@Path("type") String type, @Path("pageNum") int pageNum);

    /**
     * 获取所有接口
     *
     * @param version 本地数据的版本号，如果一致服务器将不会提供新数据
     */
//    @GET(BuildConfig.HOST_URL_ADDRESS)
//    Observable<BaseBean<HostUrlBean>> getHostUrl(@Query("v") String version);

    /**
     * 获取全局配置
     */
//    @GET
//    Observable<BaseBean<GlobalConfigBean>> getGlobalConfig(@Url String url);


    /**
     * 邮箱登录
     */
    @POST(REQUEST_LOGIN)
    Observable<BaseBean<User>> loginByEmail(@Body RequestBody params);


    /**
     * 验证码
     *
     */
    @Streaming
    @POST
    Call<ResponseBody> fetchVerify(@Url String fileUrl);

//    /**
//     * 获取task列表
//     */
//    @POST(REQUEST_FETCH_TASKLIST)
//    Observable<BaseBean<TaskList>> fetchTaskList(@Body RequestBody param);
//    /**
//     * 获取rank列表
//     */
//    @POST(REQUEST_FETCH_RANKLIST)
//    Observable<BaseBean<RankList>> fetchRankList(@Body RequestBody param);

//    /**
//     * 上传文件
//     *
//     * @param body      本地文件
//     * @return
//     */
//    @POST(REQUEST_TASK_SUBMIT)
//    Observable<BaseBean<String>> uploadFile(@Body MultipartBody body);

    /**
     * 下载文件
     *
     * @param fileUrl 要下载的文件url
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

//    @POST(REQUEST_FETCH_PLATFORM_LIST)
//    Observable<BaseBean<List<PlatformBean>>> getPlatformList();
//
//    @POST(REQUEST_FETCH_PLATFORM_ACCOUNTS)
//    Observable<BaseBean<List<ChannelAccountBean>>> getUserPlatformAccounts(@Body RequestBody body);
//
//    @POST(REQUEST_BIND_PLATFORM_ACCOUNT)
//    Observable<BaseBean<String>> bindAccount(@Body RequestBody body);
//
//    @POST(REQUEST_CHECK_PLATFORM_ACCOUNT)
//    Observable<BaseBean<String>> checkAccount(@Body RequestBody body);
//
//    @POST(REQUEST_UPDATE_PLATFORM_ACCOUNT)
//    Observable<BaseBean<String>> updateAccount(@Body RequestBody body);
//
//    @POST(REQUEST_LOGOUT)
//    Observable<BaseBean<String>> logout(@Body RequestBody body);

}

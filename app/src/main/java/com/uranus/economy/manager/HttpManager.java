package com.uranus.economy.manager;

import android.content.res.AssetManager;
import android.util.Log;

import androidx.multidex.BuildConfig;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.uranus.economy.Global;
import com.uranus.economy.base.App;
import com.uranus.economy.bean.BaseBean;
import com.uranus.economy.convert.CustomGsonConverterFactory;
import com.uranus.economy.network.ApiException;
import com.uranus.economy.network.LoggingInterceptor;
import com.uranus.economy.network.PublicParams;
import com.uranus.economy.network.RequestService;
import com.uranus.economy.util.LogUtils;
import com.uranus.economy.util.SdCardUtil;

import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * -----------
 * 请求实例的初始化，包括OkHttp和Retrofit的初始化
 */
public class HttpManager {
    //超时时间，默认20秒
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存大小,50M
    private static final int CACHE_MAX_SIZE = 1024 * 1024 * 50;
    //接口声明Service
    private RequestService requestService;

    private volatile static HttpManager ourInstance;

    private HttpManager() {
        //设置缓存路径
        File cacheFile = new File(SdCardUtil.getHttpCache());
        //设置缓存文件大小
        Cache cache = new Cache(cacheFile, CACHE_MAX_SIZE);
        //okHttp初始化
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//超时时长
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//超时时长
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(EconomyAllCerts.createSSLSocketFactory())
                .hostnameVerifier(new EconomyAllCerts.TrustAllHostnameVerifier())
                .cache(cache)//缓存路径&大小
                .addInterceptor(new PublicParams());//公共参数


        //如果是Debug，显示接口日志
        if (BuildConfig.DEBUG) {
            LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
            loggingInterceptor.setLevel(LoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        //Retrofit初始化
        requestService = new Retrofit.Builder()
                .client(okHttpBuilder.build())//网络请求库的设置，okHttp
                .addConverterFactory(CustomGsonConverterFactory.create())//json解析库的设置，Gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//回调库的设置，RxJava
                .baseUrl(Global.REQUEST_HOST+"/")//基类url
                .build()
                .create(RequestService.class);

    }


    //获取单例
    public static HttpManager getInstance() {
        //在调用该方法时进行判空，在对象为null时创建对象
        if (ourInstance == null) {
            synchronized (new Object()) {
                if (ourInstance == null) {
                    ourInstance = new HttpManager();
                    LogUtils.d1("HttpManager","HttpManager初始化成功");
                }
            }
        }
        return ourInstance;
    }

    public RequestService getService() {
        return requestService;
    }

    /**
     * 请求结果的预先处理
     * 如果请求失败，会把本次的请求的状态置为ApiException
     */
    public static class HttpRequestFunc<T> implements Function<BaseBean<T>, T> {
        @Override
        public T apply(BaseBean<T> tBaseRxBean) {
            //将请求失败的接口直接过滤，触发失败回调，并结束
            if (!tBaseRxBean.requestSuccess()) {
                throw new ApiException(tBaseRxBean.code,tBaseRxBean.msg);
            }
            //到这里代表接口请求成功
            //如果dataInfo为空，map会转化失败，因为map不支持传递空值
            //直接抛出异常，到onError里进行分析，分析代码在RequestCallback当中
            if(tBaseRxBean.data == null){
                throw new ApiException(tBaseRxBean.code,tBaseRxBean.msg);
            }
            return tBaseRxBean.data;
        }
    }

//    /**
//     * 文件上传
//     * @param fileType 文件类型，头像为1，其他暂时为2，-1单纯的上传文件和业务无关
//     * @param file 文件地址
//     * @param callback 回调
//     */
//    public void uploadFile(int fileType, File file, UploadCallback<String> callback) {
////        String uploadUrl = UrlManager.getUrl(Constant.Request.KEY_UPLOAD_PIC_ONLY);
//        String uploadUrl = "http://192.168.10.194:8090/api/publish/app/taskSubmit";
//
//        RequestBody requestBody = null;
//
//        if(fileType == Constant.UploadFileType.FILE_TYPE_AUDIO) {
//            requestBody = RequestBody.create(MediaType.parse("video/mp4"), file);
//        } else {
//            requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        }
//
//        //通过该行代码将RequestBody转换成特定的FileRequestBody
//        UploadBody body = new UploadBody<>(requestBody, callback);
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.addFormDataPart("result_img", file.getName(), body);
//        builder.addFormDataPart("id","1");
//        builder.addFormDataPart("platform_account_id","1");
//        builder.setType(MultipartBody.FORM);
//        MultipartBody build = builder.build();
//        requestService.uploadFile(build)
//                .map(new HttpManager.HttpRequestFunc<String>())
//                .compose(RxHelper.<String>applySchedulers())
//                .subscribe(callback);
//    }



    public static SSLContext getSslContextForCertificateFile(String fileName) {
        try {
            KeyStore keyStore = getKeyStore(fileName);
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            String msg = "Error during creating SslContext for certificate from assets";
            Log.e("SslUtils", msg, e);
            throw new RuntimeException(msg);
        }
    }

    private static KeyStore getKeyStore(String fileName) {
        KeyStore keyStore = null;
        try {
            AssetManager assetManager = App.context.getAssets();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = assetManager.open(fileName);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.d("SslUtils", "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            String keyStoreType = KeyStore.getDefaultType();
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
        } catch (Exception e) {
            Log.e("SslUtils", "Error during getting keystore", e);
        }
        return keyStore;
    }
}

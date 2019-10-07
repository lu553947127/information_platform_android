package com.shuangduan.zcy.model.api.retrofit;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.BuildConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.model.api.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/04/04
 *     desc   : retrofit帮助类
 *     version: 1.0
 * </pre>
 */

public class RetrofitHelper {

    public static String BASE_TEST_URL = "http://xx.yijijian.com";//测试服
//    public static String BASE_TEST_URL = "http://app.zicai365.com";//正式服
    private static ApiService apiService;

    private static void init(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
                .hostnameVerifier((hostname, session) -> true)//配置
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder().addHeader("token", SPUtils.getInstance().getString(SpConfig.TOKEN));
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

        if (BuildConfig.IS_SHOW_LOG){
            builder.addInterceptor(new LoggingIntercepter());
        }

        String BASE_URL = "http://app.zicai365.com";
        Retrofit build = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.IS_DEBUG ? BASE_TEST_URL : BASE_URL)
                .build();

        apiService = build.create(ApiService.class);
    }

    public static ApiService getApiService() {
        init();
        return apiService;
    }
}

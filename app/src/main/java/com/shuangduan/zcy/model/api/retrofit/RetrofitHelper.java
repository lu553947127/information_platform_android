package com.shuangduan.zcy.model.api.retrofit;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.BuildConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.model.api.ApiService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.api.retrofit
 * @ClassName: RetrofitHelper
 * @Description: OkHttpClient网络请求初始化设置
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/7 11:37
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/7 11:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class RetrofitHelper {


    private static ApiService apiService;

    private static void init(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(Objects.requireNonNull(SSLSocketClient.getSSLSocketFactory()))//配置https证书
                .hostnameVerifier((hostname, session) -> true)//配置
                .dns(new ApiDns())
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder().addHeader("token", SPUtils.getInstance().getString(SpConfig.TOKEN));
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

        if (BuildConfig.SHOW_LOG){
            builder.addInterceptor(new LoggingIntercepter());
        }



        Retrofit build = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();

        apiService = build.create(ApiService.class);
    }

    public static ApiService getApiService() {
        init();
        return apiService;
    }
}

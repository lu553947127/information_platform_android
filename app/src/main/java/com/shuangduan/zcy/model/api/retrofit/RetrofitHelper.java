package com.shuangduan.zcy.model.api.retrofit;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.BuildConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.model.api.ApiService;
import com.shuangduan.zcy.model.api.convert.MyGsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/04/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RetrofitHelper {

    private static String BASE_URL = "";
    private static String BASE_TEST_URL = "http://47.104.64.194:3000/mock/43/district/province/";
    private static ApiService apiService;

    private static void init(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("X-App-Token", SPUtils.getInstance().getString(SpConfig.TOKEN))
                            .addHeader("X-App-Version", "1.0.0");
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

        if (BuildConfig.IS_SHOW_LOG){
            builder.addInterceptor(new LoggingIntercepter());
        }

        Retrofit build = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .baseUrl(BuildConfig.IS_DEBUG? BASE_TEST_URL :BASE_URL)
                .build();

        apiService = build.create(ApiService.class);
    }

    public static ApiService getApiService() {
        init();
        return apiService;
    }

}

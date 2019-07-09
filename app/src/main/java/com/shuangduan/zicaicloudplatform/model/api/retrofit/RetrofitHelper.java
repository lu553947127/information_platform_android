package com.shuangduan.zicaicloudplatform.model.api.retrofit;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zicaicloudplatform.app.BuildConfig;
import com.shuangduan.zicaicloudplatform.app.SpConfig;
import com.shuangduan.zicaicloudplatform.model.api.ApiService;
import com.shuangduan.zicaicloudplatform.model.api.convert.MyGsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static String BASE_TEST_URL_1 = "";
    private static ApiService apiService;

    private static void init(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .addHeader("X-App-Token", SPUtils.getInstance().getString(SpConfig.TOKEN))
                                .addHeader("X-App-Version", "1.0.0");
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

        if (BuildConfig.IS_SHOW_LOG){
            builder.addInterceptor(new LoggingIntercepter());
        }

        Retrofit build = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .baseUrl(BuildConfig.IS_DEBUG?BASE_TEST_URL_1:BASE_URL)
                .build();

        apiService = build.create(ApiService.class);
    }

    public static ApiService getApiService() {
        init();
        return apiService;
    }

}

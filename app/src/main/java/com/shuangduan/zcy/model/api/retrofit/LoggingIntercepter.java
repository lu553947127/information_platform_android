package com.shuangduan.zcy.model.api.retrofit;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/06/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class LoggingIntercepter implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();

        String body = null;

        if (requestBody != null){
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null){
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }

        LogUtils.i(String.format("发送请求\nmethod：%s\nurl：%s\nheaders: %s \nbody：%s", request.method(), request.url(), request.headers(), body));

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        String rBody = null;

        if (responseBody != null){
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null){
                try {
                    charset = contentType.charset(UTF8);
                }catch (UnsupportedCharsetException e){
                    e.printStackTrace();
                }
            }

            rBody = buffer.clone().readString(charset);
        }

        LogUtils.i(String.format("收到响应 %s %s \n时间 %s ms\n请求url：%s\n请求body：", response.code(), response.message(), tookMs, response.request().url()));
        // TODO: 2019/1/19 json类型的响应数据，一些手机上显示不出来，不知道什么鸡毛问题，手头的手机显示不出来就把rBody放到上一行显示String类型去
        LogUtils.json(rBody);

        return response;
    }
}

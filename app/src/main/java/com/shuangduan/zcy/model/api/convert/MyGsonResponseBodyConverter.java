package com.shuangduan.zcy.model.api.convert;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.model.api.convert.exception.ApiException;
import com.shuangduan.zcy.model.bean.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/05/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */

final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T>{

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private static final Charset UTF_8 = Charset.forName("UTF-8");


    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        //增加json数据的打印
        BaseResponse result = gson.fromJson(response, BaseResponse.class);
        int responseCode = result.getCode();
        //如果响应码不为正常响应我们就抛出一个自定义的异常，onError方法回去捕获这个异常
        if (responseCode != 200) {
            value.close();
            LogUtils.i(result.getMessage());
            throw new ApiException(result.getCode(), result.getMessage());
        }
        MediaType type=value.contentType();
        Charset charset=type!=null?type.charset(UTF_8):UTF_8;
        InputStream is = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(is,charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }

    }
}

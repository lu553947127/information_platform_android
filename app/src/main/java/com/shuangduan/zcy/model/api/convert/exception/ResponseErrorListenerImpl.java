package com.shuangduan.zcy.model.api.convert.exception;

import android.net.ParseException;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.view.login.WelcomeActivity;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ResponseErrorListenerImpl implements ResponseErrorListener {
    @Override
    public void handlerResponseError(Throwable t) {
        LogUtils.i("请求出错", t.getMessage(), t.getCause());
        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = httpException.getMessage();
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "数据解析错误";
        }else if (t instanceof ApiException){
            //自定义错误
            msg = convertStatusCode((ApiException) t);
        }

        ToastUtils.showLong(msg);
    }

    /**
     * 自定义错误处理，非200
     *  可根据后台提供错误码信息
     */
    private String convertStatusCode(ApiException exception) {
        String msg;
        if (exception.getErrorCode() == 500) {
            msg = "服务器发生错误";
        } else if (exception.getErrorCode() == 404) {
            msg = "请求地址不存在";
        } else if (exception.getErrorCode() == 403) {
            msg = "请求被服务器拒绝";
        } else if (exception.getErrorCode() == 307) {
            msg = "请求被重定向到其他页面";
        } else if (exception.getErrorCode() == -1){
            msg = "账户失效，请重新登录";
            SPUtils.getInstance().clear();
            SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
            ActivityUtils.startActivity(WelcomeActivity.class);
            ActivityUtils.finishAllActivitiesExceptNewest();

        }else {
            msg = exception.getMsg();
        }
        return msg;
    }
}

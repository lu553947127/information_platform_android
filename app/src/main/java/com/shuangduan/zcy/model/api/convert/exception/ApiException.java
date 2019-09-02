package com.shuangduan.zcy.model.api.convert.exception;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/30
 *     desc   : 自定义异常类型
 *     version: 1.0
 * </pre>
 */

public class ApiException extends RuntimeException {

    private int errorCode;
    private String msg;

    public ApiException(int errorCode, String msg) {
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}

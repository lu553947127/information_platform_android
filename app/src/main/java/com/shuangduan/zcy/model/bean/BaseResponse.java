package com.shuangduan.zcy.model.bean;


/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/05/16
 *     desc   : data为object
 *     version: 1.0
 * </pre>
 */

public class BaseResponse<T> {

    private int code;
    private String msg;
    private int time;
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/05/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class BaseObjResponse<T> {

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

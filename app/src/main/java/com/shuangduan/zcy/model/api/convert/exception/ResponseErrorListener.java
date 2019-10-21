package com.shuangduan.zcy.model.api.convert.exception;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public interface ResponseErrorListener {

    void handlerResponseError(Throwable t);

    ResponseErrorListener EMPTY = new ResponseErrorListener(){

        @Override
        public void handlerResponseError(Throwable t) {

        }
    };

}

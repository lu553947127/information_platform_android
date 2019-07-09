package com.shuangduan.zicaicloudplatform.model.api.convert.exception;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ErrorHandlerFactory {
    private ResponseErrorListener mResponseErrorListener;

    public ErrorHandlerFactory(ResponseErrorListener mResponseErrorListener) {
        this.mResponseErrorListener = mResponseErrorListener;
    }

    /**
     *  处理错误
     * @param throwable
     */
    public void handleError(Throwable throwable) {
        mResponseErrorListener.handlerResponseError(throwable);
    }
}

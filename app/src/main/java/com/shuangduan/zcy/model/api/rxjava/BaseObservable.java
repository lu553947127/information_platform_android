package com.shuangduan.zcy.model.api.rxjava;

import com.shuangduan.zcy.model.api.convert.exception.ErrorHandlerFactory;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public abstract class BaseObservable<T> implements Observer<T> {

    private ErrorHandlerFactory mErrorHandlerFactory;

    public BaseObservable(ErrorHandlerFactory mErrorHandlerFactory) {
        this.mErrorHandlerFactory = mErrorHandlerFactory;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mErrorHandlerFactory.handleError(e);
    }

    @Override
    public void onComplete() {

    }

}

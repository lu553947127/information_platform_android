package com.shuangduan.zcy.model.api.rxjava;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.model.api.convert.exception.ApiException;
import com.shuangduan.zcy.model.api.convert.exception.ErrorHandlerFactory;
import com.shuangduan.zcy.model.api.convert.exception.ResponseErrorListenerImpl;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseObjResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class BaseSubscriber<T> implements Subscriber<T> {

    private ErrorHandlerFactory mErrorHandlerFactory;
    private MutableLiveData<T> data;
    private MutableLiveData<List<T>> dataList;

    public BaseSubscriber() {
        this.mErrorHandlerFactory = new ErrorHandlerFactory(new ResponseErrorListenerImpl());
        this.data = new MutableLiveData<>();
        this.dataList = new MutableLiveData<>();
    }

    /**
     * 返回数据类型BaseResponse
     * @return
     */
    public MutableLiveData<T> getData() {
        return data;
    }

    /**
     * 返回数据类型BaseListResponse
     * @return
     */
    public MutableLiveData<List<T>> getDataList(){
        return dataList;
    }

    public void set(T t){
        if (t instanceof BaseObjResponse){
            this.data.setValue((T) ((BaseObjResponse) t).getData());
        }else if (t instanceof BaseListResponse){
            this.dataList.setValue(((BaseListResponse) t).getData());
        }
    }

    public void onFinish(T t){
        set(t);
    }

    @Override
    public void onSubscribe(Subscription s) {
        //观察者接收事件 = 1个
        s.request(1);
    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseObjResponse){
            if (((BaseObjResponse) t).getCode() == 200){
                onFinish(t);
            }else {
                mErrorHandlerFactory.handleError(new ApiException(((BaseObjResponse) t).getCode(), ((BaseObjResponse) t).getMessage()));
            }
        }else if (t instanceof BaseListResponse){
            if (((BaseListResponse) t).getCode() == 200){
                onFinish(t);
            }else {
                mErrorHandlerFactory.handleError(new ApiException(((BaseListResponse) t).getCode(), ((BaseListResponse) t).getMessage()));
            }
        }
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

package com.shuangduan.zcy.model.api.rxjava;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.convert.exception.ApiException;
import com.shuangduan.zcy.model.api.convert.exception.ErrorHandlerFactory;
import com.shuangduan.zcy.model.api.convert.exception.ResponseErrorListenerImpl;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseObjResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Objects;

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
    private MutableLiveData<String> pageState;

    public BaseSubscriber() {
        this.mErrorHandlerFactory = new ErrorHandlerFactory(new ResponseErrorListenerImpl());
        if (this.data == null)
            this.data = new MutableLiveData<>();
        if (this.dataList == null)
            this.dataList = new MutableLiveData<>();
        if (this.pageState == null)
            this.pageState = new MutableLiveData<>();
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
        //网络判断，无网络停止请求
        if (!NetworkUtils.isConnected()){
            pageState.postValue(PageState.PAGE_NET_ERROR);
            s.cancel();
            return;
        }
        pageState.postValue(PageState.PAGE_LOADING);
    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseObjResponse){
            if (((BaseObjResponse) t).getCode() == 200){
                onFinish(t);
                pageState.postValue(PageState.PAGE_LOAD_SUCCESS);
            }else {
                mErrorHandlerFactory.handleError(new ApiException(((BaseObjResponse) t).getCode(), ((BaseObjResponse) t).getMessage()));
                pageState.postValue(PageState.PAGE_SERVICE_ERROR);
            }
        }else if (t instanceof BaseListResponse){
            if (((BaseListResponse) t).getCode() == 200){
                onFinish(t);
                pageState.postValue(PageState.PAGE_LOAD_SUCCESS);
            }else {
                mErrorHandlerFactory.handleError(new ApiException(((BaseListResponse) t).getCode(), ((BaseListResponse) t).getMessage()));
                pageState.postValue(PageState.PAGE_SERVICE_ERROR);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mErrorHandlerFactory.handleError(e);
        pageState.postValue(PageState.PAGE_ERROR);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 获取请求过程状态，更新界面
     * @return
     */
    public MutableLiveData<String> getPageState() {
        return pageState;
    }

}

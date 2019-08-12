package com.shuangduan.zcy.model.api.rxjava;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.convert.exception.ApiException;
import com.shuangduan.zcy.model.api.convert.exception.ErrorHandlerFactory;
import com.shuangduan.zcy.model.api.convert.exception.ResponseErrorListenerImpl;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseResponse;

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
    private MutableLiveData<String> pageState;

    public BaseSubscriber() {
        this.mErrorHandlerFactory = new ErrorHandlerFactory(new ResponseErrorListenerImpl());
    }

    /**
     * 获取请求过程状态，更新界面
     * @return
     */
    public void setPageState(MutableLiveData<String> pageState) {
        this.pageState = pageState;
    }

    /**
     * 返回数据类型BaseResponse
     * @return
     */
    public void setData(MutableLiveData<T> data) {
        this.data = data;
    }

    /**
     * 返回数据类型BaseListResponse
     * @return
     */
    public void setDataList(MutableLiveData<List<T>> dataList){
        this.dataList = dataList;
    }

    public void set(T t){
        if (t instanceof BaseResponse && this.data != null){
            this.data.setValue((T) ((BaseResponse) t).getData());
        }else if (t instanceof BaseListResponse && this.dataList != null){
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
            ToastUtils.showShort("请检查是否联网");
            if (pageState != null)
                pageState.postValue(PageState.PAGE_NET_ERROR);
            s.cancel();
            return;
        }
        if (pageState != null && !PageState.PAGE_REFRESH.equals(pageState.getValue()) && !PageState.PAGE_LOADING.equals(pageState.getValue())){
            pageState.postValue(PageState.PAGE_LOADING);
        }
    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseResponse){
            if (((BaseResponse) t).getCode() == 200){
                onFinish(t);
                if (pageState != null)
                    pageState.postValue(PageState.PAGE_LOAD_SUCCESS);
            }else {
                mErrorHandlerFactory.handleError(new ApiException(((BaseResponse) t).getCode(), ((BaseResponse) t).getMessage()));
                if (pageState != null)
                    pageState.postValue(PageState.PAGE_SERVICE_ERROR);
            }
        }else if (t instanceof BaseListResponse){
            if (((BaseListResponse) t).getCode() == 200){
                onFinish(t);
                if (pageState != null)
                    pageState.postValue(PageState.PAGE_LOAD_SUCCESS);
            }else {
                mErrorHandlerFactory.handleError(new ApiException(((BaseListResponse) t).getCode(), ((BaseListResponse) t).getMessage()));
                if (pageState != null)
                    pageState.postValue(PageState.PAGE_SERVICE_ERROR);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mErrorHandlerFactory.handleError(e);
        if (pageState != null)
            pageState.postValue(PageState.PAGE_ERROR);
    }

    @Override
    public void onComplete() {

    }

}

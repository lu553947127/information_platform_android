package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.shuangduan.zcy.model.api.ApiService;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/18 9:36
 * @change
 * @chang time
 * @class describe
 */
public class BaseRepository<T> {

    /*RxJava回调*/
    private BaseSubscriber<T> baseObservable;
    /*解决背压*/
    private Flowable<T> flowable;
    public ApiService apiService;
    public MutableLiveData<String> pageStateLiveData;

    /*初始化*/
    public BaseRepository() {
        if (this.baseObservable == null)
            this.baseObservable = new BaseSubscriber<T>() ;
        if (this.apiService == null)
            this.apiService = RetrofitHelper.getApiService();
    }

    /**
     * 发送请求
     * @return
     */
    public BaseSubscriber<T> send(){
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObservable);
        return baseObservable;
    }

    /**
     * 初始化flowable
     * @param flowable
     * @return
     */
    public BaseRepository<T> request(Flowable<T> flowable){
        this.flowable = flowable;
        return this;
    }

    public MutableLiveData<String> getPageStateLiveData() {
        return pageStateLiveData !=null? pageStateLiveData: new MutableLiveData<>();
    }
}

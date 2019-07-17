package com.shuangduan.zcy.model.api;

import androidx.annotation.MainThread;

import com.shuangduan.zcy.model.api.convert.exception.ErrorHandlerFactory;
import com.shuangduan.zcy.model.api.convert.exception.ResponseErrorListenerImpl;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.api.rxjava.BaseObservable;
import com.shuangduan.zcy.model.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.model.api
 * @class describe
 * @time 2019/7/3 17:13
 * @change
 * @chang time
 * @class describe
 */
public class DataManager {

    private final ApiService apiService;
    private ErrorHandlerFactory errorHandlerFactory;

    public DataManager() {
        this.apiService = RetrofitHelper.getApiService();
        errorHandlerFactory = new ErrorHandlerFactory(new ResponseErrorListenerImpl());
    }

    private static class Holder{
        private static final DataManager instance = new DataManager();
    }

    public static DataManager getInstance(){
        return Holder.instance;
    }

    public void smsCode(String tel, int type){
        apiService.smsCode(tel, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObservable<BaseResponse>(errorHandlerFactory) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {

                    }
                });
    }

}

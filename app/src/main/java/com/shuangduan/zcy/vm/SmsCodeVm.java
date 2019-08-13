package com.shuangduan.zcy.vm;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.LoginRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/13 18:41
 * @change
 * @chang time
 * @class describe
 */
public class SmsCodeVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<Long> timeLiveDataLiveData;
    public MutableLiveData smsDataLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public SmsCodeVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        smsDataLiveData = new MutableLiveData<>();
        timeLiveDataLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void smsCode(String tel, int type){
        new LoginRepository().smsCode(smsDataLiveData, pageStateLiveData, tel, type);
    }

    /**
     * 获取验证码倒计时
     */
    @SuppressLint("CheckResult")
    public void sendVerificationCode(){
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(60)
                .map(aLong -> 59 - aLong)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(aLong -> timeLiveDataLiveData.postValue(aLong), throwable -> {

                }, () -> {
                    //倒计时结束，重置按钮，并停止获取请求
                    timeLiveDataLiveData.postValue((long) -1);
                });

    }
}

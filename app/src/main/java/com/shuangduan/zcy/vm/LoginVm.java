package com.shuangduan.zcy.vm;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.base.BaseViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.vm
 * @class describe
 * @time 2019/7/4 15:23
 * @change
 * @chang time
 * @class describe
 */
public class LoginVm extends BaseViewModel {

    private MutableLiveData<Integer> changeData = new MutableLiveData<>();
    private MutableLiveData<Long> timeLiveData;

    public LoginVm() {
        timeLiveData = new MutableLiveData<>();
        addLiveData(timeLiveData);
    }

    public MutableLiveData<Integer> getChangeData() {
        return changeData;
    }

    public MutableLiveData<Long> getTimeLiveData() {
        return timeLiveData;
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
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(aLong -> timeLiveData.postValue(aLong), throwable -> {

                }, () -> {
                    //倒计时结束，重置按钮，并停止获取请求
                    timeLiveData.postValue((long) -1);
                });

    }

}

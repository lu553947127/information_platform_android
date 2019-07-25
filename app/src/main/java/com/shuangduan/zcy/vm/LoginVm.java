package com.shuangduan.zcy.vm;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.LoginRepository;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.ReSetPwdBean;
import com.shuangduan.zcy.model.bean.RegisterBean;

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

    public MutableLiveData<Long> timeLiveDataLiveData;
    public MutableLiveData smsDataLiveData;
    public MutableLiveData<LoginBean> accountLoginLiveData;
    public MutableLiveData<RegisterBean> registerLiveData;
    public MutableLiveData<ReSetPwdBean> resetPwdLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public LoginVm() {
        timeLiveDataLiveData = new MutableLiveData<>();
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

    public void smsCode(String tel, int type){
        LoginRepository loginRepository = new LoginRepository();
        smsDataLiveData = loginRepository.smsCode(tel, type);
        pageStateLiveData = loginRepository.getPageStateLiveData();
    }

    public void codeLogin(String tel, String code, String client_id){
        LoginRepository loginRepository = new LoginRepository();
        accountLoginLiveData = loginRepository.codeLogin(tel, code, client_id);
        pageStateLiveData = loginRepository.getPageStateLiveData();
    }

    public void accountLogin(String tel, String pwd, String client_id){
        LoginRepository loginRepository = new LoginRepository();
        accountLoginLiveData = loginRepository.accountLogin(tel, pwd, client_id);
        pageStateLiveData = loginRepository.getPageStateLiveData();
    }

    public void register(String tel, String code, String pwd, String invite_tel){
        LoginRepository loginRepository = new LoginRepository();
        registerLiveData = loginRepository.register(tel, code, pwd, invite_tel);
        pageStateLiveData = loginRepository.getPageStateLiveData();
    }

    public void resetPwd(String tel, String code, String pwd){
        LoginRepository loginRepository = new LoginRepository();
        resetPwdLiveData = loginRepository.setPassword(tel, code, pwd);
        pageStateLiveData = loginRepository.getPageStateLiveData();
    }

}

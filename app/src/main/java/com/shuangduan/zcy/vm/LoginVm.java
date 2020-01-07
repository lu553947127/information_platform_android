package com.shuangduan.zcy.vm;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.LoginRepository;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.ReSetPwdBean;
import com.shuangduan.zcy.model.bean.RegisterBean;
import com.shuangduan.zcy.model.bean.WXLoginBindingBean;
import com.shuangduan.zcy.model.bean.WXLoginVerificationBean;
import com.shuangduan.zcy.model.bean.WXUserInfoBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.vm
 * @class describe
 * @time 2019/7/4 15:23
 * @change
 * @chang time
 * @class describe
 */
public class LoginVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<Long> timeLiveDataLiveData;
    public MutableLiveData<Long> timeLiveDataLiveDataRegister;
    public MutableLiveData smsDataLiveData;
    public MutableLiveData<LoginBean> accountLoginLiveData;
    public MutableLiveData<RegisterBean> registerLiveData;
    public MutableLiveData<ReSetPwdBean> resetPwdLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<WXLoginVerificationBean> wxLoginVerificationBeanMutableLiveData;
    public MutableLiveData<WXLoginBindingBean> wxLoginBindingBeanMutableLiveData;
    public MutableLiveData<WXUserInfoBean> wxLoginLiveData;
    public MutableLiveData wxLoginBindingLiveData;
    public MutableLiveData wxLoginCloseLiveData;

    public LoginVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        accountLoginLiveData = new MutableLiveData<>();
        smsDataLiveData = new MutableLiveData<>();
        resetPwdLiveData = new MutableLiveData<>();
        registerLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        timeLiveDataLiveData = new MutableLiveData<>();
        timeLiveDataLiveDataRegister = new MutableLiveData<>();
        wxLoginVerificationBeanMutableLiveData = new MutableLiveData<>();
        wxLoginBindingBeanMutableLiveData = new MutableLiveData<>();
        wxLoginLiveData = new MutableLiveData<>();
        wxLoginBindingLiveData = new MutableLiveData();
        wxLoginCloseLiveData = new MutableLiveData();
    }

    /**
     * 获取验证码倒计时
     */
    @SuppressLint("CheckResult")
    public void sendVerificationCode() {
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

    @SuppressLint("CheckResult")
    public void sendVerificationCodeRegister() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(60)
                .map(aLong -> 59 - aLong)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(aLong -> timeLiveDataLiveDataRegister.postValue(aLong), throwable -> {

                }, () -> {
                    //倒计时结束，重置按钮，并停止获取请求
                    timeLiveDataLiveDataRegister.postValue((long) -1);
                });
    }

    public void smsCode(String tel, int type) {
        new LoginRepository().smsCode(smsDataLiveData, pageStateLiveData, tel, type);
    }

    public void codeLogin(String tel, String code, String client_id) {
        new LoginRepository().codeLogin(accountLoginLiveData, pageStateLiveData, tel, code, client_id);
    }

    public void accountLogin(String tel, String pwd, String client_id) {
       
        new LoginRepository().accountLogin(accountLoginLiveData, pageStateLiveData, tel, pwd, client_id);
    }

    public void register(String tel, String code, String pwd, String invite_tel) {
        new LoginRepository().register(registerLiveData, pageStateLiveData, tel, code, pwd, invite_tel);
    }

    public void resetPwd(String tel, String code, String pwd) {
        new LoginRepository().setPassword(resetPwdLiveData, pageStateLiveData, tel, code, pwd);
    }

    //微信登录绑定
    public void getWeChatBinding(String unionid, String openid,String headimgurl,String nickname, String tel, String code, String invite_tel) {
        new LoginRepository().getWeChatBinding(wxLoginBindingBeanMutableLiveData, pageStateLiveData, unionid, openid,headimgurl,nickname, tel, code, invite_tel);
    }

    //微信登录验证(获取unionid/openid)
    public void wxLogin(String code) {
        new LoginRepository().wxLogin(wxLoginVerificationBeanMutableLiveData,pageStateLiveData, code);
    }

    //微信绑定信息查询
    public void getWxStatus() {
        new LoginRepository().getWxStatus(wxLoginLiveData,pageStateLiveData, userId);
    }

    //微信内部绑定
    public void userWechatBind(String code) {
        new LoginRepository().userWechatBind(wxLoginBindingLiveData,pageStateLiveData,userId,code);
    }

    //微信解绑
    public void userWechatClose() {
        new LoginRepository().userWechatClose(wxLoginCloseLiveData,pageStateLiveData, userId);
    }
}

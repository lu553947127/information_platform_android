package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.RegisterBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/18 10:32
 * @change
 * @chang time
 * @class describe
 */
public class LoginRepository extends BaseRepository {

    public MutableLiveData smsCode(String tel, int type){
        BaseSubscriber subscriber = request(apiService.smsCode(tel, type)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    public MutableLiveData<LoginBean> codeLogin(String tel, String code, String client_id){
        BaseSubscriber subscriber = request(apiService.codeLogin(tel, code, client_id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    public MutableLiveData<LoginBean> accountLogin(String tel, String pwd, String client_id){
        BaseSubscriber subscriber = request(apiService.accountLogin(tel, pwd, client_id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    public MutableLiveData<RegisterBean> register(String tel, String code, String pwd, String invite_tel){
        BaseSubscriber subscriber = request(apiService.register(tel, code, pwd, invite_tel)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

}

package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.ReSetPwdBean;
import com.shuangduan.zcy.model.bean.RegisterBean;
import com.shuangduan.zcy.model.bean.WXLoginBindingBean;
import com.shuangduan.zcy.model.bean.WXLoginVerificationBean;

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

    public void smsCode(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, String tel, int type){
        request(apiService.smsCode(tel, type)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void codeLogin(MutableLiveData<LoginBean> liveData, MutableLiveData<String> pageStateLiveData, String tel, String code, String client_id){
        request(apiService.codeLogin(tel, code, client_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void accountLogin(MutableLiveData<LoginBean>liveData, MutableLiveData<String> pageStateLiveData, String tel, String pwd, String client_id){
        request(apiService.accountLogin(tel, pwd, client_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void register(MutableLiveData<RegisterBean> liveData, MutableLiveData<String> pageStateLiveData, String tel, String code, String pwd, String invite_tel){
        request(apiService.register(tel, code, pwd, invite_tel)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 重置密码
     */
    public void setPassword(MutableLiveData<ReSetPwdBean> liveData, MutableLiveData<String> pageStateLiveData, String tel, String code, String pwd){
        request(apiService.setPassword(tel, code, pwd)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void outLogin(MutableLiveData<String> liveData, int userId){
        request(apiService.outLogin(userId)).setData(liveData).send();
    }

    //微信登录验证
    public void getWeChatVerification(MutableLiveData<WXLoginVerificationBean> liveData, String unionid, String openid){
        request(apiService.getWeChatVerification(unionid,openid)).setData(liveData).send();
    }

    //微信登录验证
    public void getWeChatBinding(MutableLiveData<WXLoginBindingBean> liveData, String unionid, String openid, String tel, String code, String invite_tel){
        request(apiService.getWeChatBinding(unionid,openid,tel,code,invite_tel)).setData(liveData).send();
    }
}

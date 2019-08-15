package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.PwdPayStateBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/13 18:18
 * @change
 * @chang time
 * @class describe
 */
public class UpdatePwdPayVm extends BaseViewModel {
    private int userId;
    public MutableLiveData updatePwdLiveData;
    public MutableLiveData setPwdLiveData;
    public MutableLiveData forgetPwdLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<PwdPayStateBean> stateLiveData;
    public int status;

    public UpdatePwdPayVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        updatePwdLiveData = new MutableLiveData();
        forgetPwdLiveData = new MutableLiveData();
        setPwdLiveData = new MutableLiveData();
        pageStateLiveData = new MutableLiveData<>();
        stateLiveData = new MutableLiveData<>();
    }

    public void updatePwd(String oldPwd, String newPwd){
        new UserRepository().updatePwdPay(updatePwdLiveData, pageStateLiveData, userId, oldPwd, newPwd);
    }

    public void setPwdPay(String pwd, String tel, String code){
        new UserRepository().setPwdPay(setPwdLiveData, pageStateLiveData, userId, pwd, tel, code);
    }

    public void forgetPwdPay( String tel, String code, String pwd){
        new UserRepository().forgetPwdPay(forgetPwdLiveData, pageStateLiveData, userId, tel, code, pwd);
    }

    public void payPwdState(){
        new UserRepository().pwdPayState(stateLiveData, pageStateLiveData, userId);
    }

}

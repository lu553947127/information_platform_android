package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.AuthenBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/6 14:48
 * @change
 * @chang time
 * @class describe
 */
public class AuthenticationVm extends BaseViewModel {
    private int userId;
    public MutableLiveData authenticationLiveData;
    public MutableLiveData<AuthenBean> authenticationStatusLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public String image_front = null;
    public String image_reverse_site = null;

    public AuthenticationVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        authenticationLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        authenticationStatusLiveData = new MutableLiveData<>();
    }

    public void idCard(){
        if (image_front == null){
            ToastUtils.showShort("请上传身份证正面");
            return;
        }
        if (image_reverse_site == null){
            ToastUtils.showShort("请上传身份证反面");
            return;
        }
        new UserRepository().idcard(authenticationLiveData, pageStateLiveData, userId, image_front, image_reverse_site);
    }

    /**
     * 实名认证检测
     */
    public void authentication(){
        new UserRepository().authentication(authenticationStatusLiveData, pageStateLiveData, userId);
    }
}

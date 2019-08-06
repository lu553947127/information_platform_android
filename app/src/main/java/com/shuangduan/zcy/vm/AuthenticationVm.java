package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;

/**
 * @author 宁文强 QQ:858777523
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
    public MutableLiveData<String> pageStateLiveData;
    public String image_front = null;
    public String image_reverse_site = null;

    public AuthenticationVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        authenticationLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
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
}

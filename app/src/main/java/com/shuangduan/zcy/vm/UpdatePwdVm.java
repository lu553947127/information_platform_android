package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/9/2 15:05
 * @change
 * @chang time
 * @class describe
 */
public class UpdatePwdVm extends BaseViewModel {
    private int userId;
    public MutableLiveData resultLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public UpdatePwdVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        resultLiveData = new MutableLiveData();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void update(String oldPwd, String newPwd){
        new UserRepository().resetPassword(resultLiveData, pageStateLiveData, userId, oldPwd, newPwd);
    }
}

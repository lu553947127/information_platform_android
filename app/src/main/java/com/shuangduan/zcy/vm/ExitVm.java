package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.LoginRepository;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/25 9:01
 * @change
 * @chang time
 * @class describe
 */
public class ExitVm extends BaseViewModel {

    private int userId;
    public MutableLiveData exitLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public void init(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        exitLiveData = new MutableLiveData();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void exit(){
        new LoginRepository().outLogin(exitLiveData, userId);
    }

}

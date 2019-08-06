package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.BankCardBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/6 16:39
 * @change
 * @chang time
 * @class describe
 */
public class BankCardVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<List<BankCardBean>> bankcardLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public BankCardVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        bankcardLiveData = new MutableLiveData<>();
    }

    public void bankcardList(){
        new UserRepository().bankcardList(bankcardLiveData, pageStateLiveData, userId);
    }
}

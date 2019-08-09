package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.IncomeRepository;
import com.shuangduan.zcy.model.bean.MineIncomeBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/5 18:27
 * @change
 * @chang time
 * @class describe
 */
public class MineIncomeVm extends BaseViewModel {

    public MutableLiveData<MineIncomeBean> incomeLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;

    public MineIncomeVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        incomeLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void myIncome(){
        new IncomeRepository().mineIncome(incomeLiveData, pageStateLiveData, userId);
    }
}

package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.RechargeRepository;
import com.shuangduan.zcy.model.bean.RechargeResultBean;
import com.shuangduan.zcy.model.bean.RechargeShowBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/13 14:46
 * @change
 * @chang time
 * @class describe
 */
public class RechargeVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<RechargeShowBean> showLiveData;
    public MutableLiveData<RechargeResultBean> resultLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<Integer> positionLiveData;

    public RechargeVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        showLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        resultLiveData = new MutableLiveData<>();
        positionLiveData = new MutableLiveData<>();
        positionLiveData.postValue(-1);
    }

    /**
     * 充值列表
     */
    public void getShowData(){
        new RechargeRepository().rechargeShow(showLiveData, pageStateLiveData, userId);
    }

    public void getResult(String order_sn){
        new RechargeRepository().rechargeResult(resultLiveData, pageStateLiveData, userId, order_sn);
    }
}

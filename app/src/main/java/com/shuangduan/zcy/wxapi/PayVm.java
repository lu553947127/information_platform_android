package com.shuangduan.zcy.wxapi;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.PayRepository;
import com.shuangduan.zcy.model.bean.PayInfoBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/29 11:39
 * @change
 * @chang time
 * @class describe
 */
public class PayVm extends BaseViewModel {

    private int type;
    public String amount;
    private int userId;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<PayInfoBean> payInfoLiveData;

    public PayVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        payInfoLiveData = new MutableLiveData<>();
    }

    public void getInfo(){
        new PayRepository().getPayInfo(payInfoLiveData, pageStateLiveData, userId, amount, type);
    }

    public void setType(int i){
        this.type = i;
    }

}

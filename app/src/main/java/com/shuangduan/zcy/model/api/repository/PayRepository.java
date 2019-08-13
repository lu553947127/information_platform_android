package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.PayInfoBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/29 11:44
 * @change
 * @chang time
 * @class describe
 */
public class PayRepository extends BaseRepository {

    public void getPayInfo(MutableLiveData<PayInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String amount, int type){
        request(apiService.recharge(userId, amount, type)).setData(liveData).setPageState(pageStateLiveData).send();
    }

}

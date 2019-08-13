package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.RechargeRecordBean;
import com.shuangduan.zcy.model.bean.RechargeRecordDetailBean;
import com.shuangduan.zcy.model.bean.RechargeResultBean;
import com.shuangduan.zcy.model.bean.RechargeShowBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/13 14:45
 * @change
 * @chang time
 * @class describe
 */
public class RechargeRepository extends BaseRepository {
    public void rechargeShow(MutableLiveData<RechargeShowBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.rechargeShow(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void rechargeResult(MutableLiveData<RechargeResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String order_sn){
        request(apiService.rechargeResult(userId, order_sn)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void rechargeRecord(MutableLiveData<RechargeRecordBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.rechargeRecord(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void rechargeRecordDetail(MutableLiveData<RechargeRecordDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.rechargeRecordDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}

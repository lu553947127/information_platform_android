package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.DemandReleaseBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/19 18:27
 * @change
 * @chang time
 * @class describe
 */
public class DemandRepository extends BaseRepository {
    public void demandRelease(MutableLiveData<DemandReleaseBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String title, String intro, String start_time, String end_time, String price){
        request(apiService.demandRelease(user_id, title, intro, start_time, end_time, price)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}

package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.IncomeRecordBean;
import com.shuangduan.zcy.model.bean.PeopleBean;
import com.shuangduan.zcy.model.bean.PeopleDetailBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/14 8:40
 * @change
 * @chang time
 * @class describe
 */
public class PeopleRepository extends BaseRepository {
    public void peopleShow(MutableLiveData<PeopleBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.peopleShow(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void peopleDetail(MutableLiveData<PeopleDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int path_user_id){
        request(apiService.peopleDetail(userId, path_user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void incomeRecord(MutableLiveData<IncomeRecordBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int path_user_id, int page){
        request(apiService.incomeRecord(userId, path_user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

}

package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.IncomeRecordBean;
import com.shuangduan.zcy.model.bean.PeopleDetailBean;
import com.shuangduan.zcy.model.bean.RankListBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/14 8:40
 * @change
 * @chang time
 * @class describe
 */
public class PeopleRepository extends BaseRepository {

    public void peopleDetail(MutableLiveData<PeopleDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int path_user_id){
        request(apiService.peopleDetail(userId, path_user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void incomeRecord(MutableLiveData<IncomeRecordBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int path_user_id, int page){
        request(apiService.incomeRecord(userId, path_user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //榜单列表
    public void honorList(MutableLiveData<RankListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int type){
        request(apiService.honorList(userId, type)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}

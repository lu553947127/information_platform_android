package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.model.bean.RecruitDetailBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/6 9:48
 * @change
 * @chang time
 * @class describe
 */
public class RecruitReporitory extends BaseRepository {
    public void recruitList(MutableLiveData<RecruitBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.recruitList(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void recruitDetail(MutableLiveData<RecruitDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.recruitDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void recruitCollect(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.recruitCollect(userId, id)).setData(liveData).send();
    }

    public void recruitCancelCollect(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.recruitCancelCollect(userId, id)).setData(liveData).send();
    }
}

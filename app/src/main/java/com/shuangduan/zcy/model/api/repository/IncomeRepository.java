package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.IncomeLocusBean;
import com.shuangduan.zcy.model.bean.IncomeMsgBean;
import com.shuangduan.zcy.model.bean.IncomePeopleBean;
import com.shuangduan.zcy.model.bean.IncomeReleaseBean;
import com.shuangduan.zcy.model.bean.MineIncomeBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/9 15:05
 * @change
 * @chang time
 * @class describe
 */
public class IncomeRepository extends BaseRepository {
    /**
     * 我的收益
     */
    public void mineIncome(MutableLiveData<MineIncomeBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id){
        request(apiService.myProceeds(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 发布信息收益
     */
    public void incomeRelease(MutableLiveData<IncomeReleaseBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.incomeRelease(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 亲密好友列表
     */
    public void incomePeople(MutableLiveData<IncomePeopleBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page, int type){
        request(apiService.incomePeople(user_id, page, type)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 信息认购收益
     */
    public void incomeMsg(MutableLiveData<IncomeMsgBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.incomeMsg(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 发布动态收益
     */
    public void incomeLocus(MutableLiveData<IncomeLocusBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.incomeLocus(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}

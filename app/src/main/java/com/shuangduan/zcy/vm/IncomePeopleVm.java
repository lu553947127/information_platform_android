package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.IncomeRepository;
import com.shuangduan.zcy.model.api.repository.PeopleRepository;
import com.shuangduan.zcy.model.bean.IncomePeopleBean;
import com.shuangduan.zcy.model.bean.IncomeRecordBean;
import com.shuangduan.zcy.model.bean.PeopleBean;
import com.shuangduan.zcy.model.bean.PeopleDetailBean;
import com.shuangduan.zcy.model.bean.RankListBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/9 14:41
 * @change
 * @chang time
 * @class describe
 */
public class IncomePeopleVm extends BaseViewModel {
    public MutableLiveData<IncomePeopleBean> liveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<PeopleBean> showLiveData;
    public MutableLiveData<PeopleDetailBean> detailLiveData;
    public MutableLiveData<IncomeRecordBean> recordLiveData;
    public MutableLiveData<RankListBean> rankListLiveData;
    private int userId, page, pageRecord;
    public int type, uid;

    public IncomePeopleVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        liveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        showLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
        recordLiveData = new MutableLiveData<>();
        rankListLiveData = new MutableLiveData<>();
    }

    /**
     * 邀请好友详情
     */
    public void getDetail(){
        new PeopleRepository().peopleDetail(detailLiveData, pageStateLiveData, userId, uid);
    }

    /**
     * 邀请好友列表
     */
    public void getData(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new IncomeRepository().incomePeople(liveData, pageStateLiveData, userId, page, type);
    }

    /**
     * 邀请好友列表
     */
    public void getMoreData(){
        page++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new IncomeRepository().incomePeople(liveData, pageStateLiveData, userId, page, type);
    }

    /**
     * 收益记录
     */
    public void getRecord(){
        pageRecord = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new PeopleRepository().incomeRecord(recordLiveData, pageStateLiveData, userId, uid, pageRecord);
    }

    public void getMoreRecord(){
        pageRecord ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new PeopleRepository().incomeRecord(recordLiveData, pageStateLiveData, userId, uid, pageRecord);
    }

    /**
     * 榜单列表
     */
    public void honorList(int type){
        new PeopleRepository().honorList(rankListLiveData, pageStateLiveData, userId, type);
    }
}

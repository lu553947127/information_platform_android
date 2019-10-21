package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.RechargeRepository;
import com.shuangduan.zcy.model.bean.RechargeRecordBean;
import com.shuangduan.zcy.model.bean.RechargeRecordDetailBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/13 16:36
 * @change
 * @chang time
 * @class describe
 */
public class RechargeRecordVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<RechargeRecordBean> recordLiveData;
    public MutableLiveData<RechargeRecordDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int page;

    public RechargeRecordVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        recordLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getRecord(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new RechargeRepository().rechargeRecord(recordLiveData, pageStateLiveData, userId, page);
    }

    public void getMoreRecord(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new RechargeRepository().rechargeRecord(recordLiveData, pageStateLiveData, userId, page);
    }

    public void getDetail(int id){
        new RechargeRepository().rechargeRecordDetail(detailLiveData, pageStateLiveData, userId, id);
    }
}

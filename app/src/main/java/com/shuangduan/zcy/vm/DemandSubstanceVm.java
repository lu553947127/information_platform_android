package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.DemandRepository;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;
import com.shuangduan.zcy.model.bean.SubstanceDetailBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/20 14:36
 * @change
 * @chang time
 * @class describe
 */
public class DemandSubstanceVm extends BaseViewModel {
    private int userId;
    private int page;
    public int isMy = 0;
    public MutableLiveData<DemandSubstanceBean> substanceLiveData;
    public MutableLiveData<SubstanceDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public int id;
    public int currentPay = 0;

    public DemandSubstanceVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        substanceLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
    }

    public void getSubstance(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandSubstance(substanceLiveData, pageStateLiveData, userId, page, isMy);
    }

    public void getMoreSubstance(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandSubstance(substanceLiveData, pageStateLiveData, userId, page, isMy);
    }

    public void getDetail(){
        new DemandRepository().substanceDetail(detailLiveData, pageStateLiveData, userId, id);
    }
}

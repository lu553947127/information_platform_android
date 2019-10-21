package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.DemandRepository;
import com.shuangduan.zcy.model.bean.BuyerDetailBean;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;

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
public class DemandBuyerVm extends BaseViewModel {
    private int userId;
    private int page;
    public int isMy = 0;
    public MutableLiveData<DemandBuyerBean> buyerLiveData;
    public MutableLiveData<BuyerDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public int id;
    public int currentPay = 0;

    public DemandBuyerVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        buyerLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getBuyer(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandBuyer(buyerLiveData, pageStateLiveData, userId, page, isMy);
    }

    public void getMoreBuyer(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandBuyer(buyerLiveData, pageStateLiveData, userId, page, isMy);
    }

    public void getDetail(){
        new DemandRepository().buyerDetail(detailLiveData, pageStateLiveData, userId, id);
    }
}

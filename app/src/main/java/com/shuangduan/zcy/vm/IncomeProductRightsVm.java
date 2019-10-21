package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.IncomeRepository;
import com.shuangduan.zcy.model.bean.IncomeLocusBean;
import com.shuangduan.zcy.model.bean.IncomeProductRightsBean;

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
public class IncomeProductRightsVm extends BaseViewModel {
    public MutableLiveData<IncomeProductRightsBean> liveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId, page;

    public IncomeProductRightsVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        liveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getData(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
//        new IncomeRepository().incomeLocus(liveData, pageStateLiveData, userId, page);
    }

    public void getMoreData(){
        page++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
//        new IncomeRepository().incomeLocus(liveData, pageStateLiveData, userId, page);
    }
}

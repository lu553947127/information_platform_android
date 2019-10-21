package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.HomeRepository;
import com.shuangduan.zcy.model.bean.HeadlinesBean;
import com.shuangduan.zcy.model.bean.HeadlinesDetailBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/15 15:17
 * @change
 * @chang time
 * @class describe
 */
public class HeadlinesVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<HeadlinesBean> headlinesLiveData;
    public MutableLiveData<HeadlinesDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int page;
    public int id;

    public HeadlinesVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        headlinesLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getHeadlines(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new HomeRepository().headlines(headlinesLiveData, pageStateLiveData, userId, page);
    }

    public void getMoreHeadlines(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new HomeRepository().headlines(headlinesLiveData, pageStateLiveData, userId, page);
    }

    public void getDetail(){
        new HomeRepository().headlinesDetail(detailLiveData, pageStateLiveData, userId, id);
    }
}

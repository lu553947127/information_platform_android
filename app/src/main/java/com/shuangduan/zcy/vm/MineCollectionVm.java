package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.ProjectCollectBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/5 14:48
 * @change
 * @chang time
 * @class describe
 */
public class MineCollectionVm extends BaseViewModel {
    public MutableLiveData<ProjectCollectBean> collectLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int page;

    public MineCollectionVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        collectLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void myCollection(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().projectCollection(collectLiveData, pageStateLiveData, userId, page);
    }

    public void refreshMyCollection(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().projectCollection(collectLiveData, pageStateLiveData, userId, page);
    }
}
